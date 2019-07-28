package com.github.daggerok

import java.util

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.util.Timeout
import javax.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{Bean, Configuration, Lazy}
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.{RouterFunction, RouterFunctions, ServerRequest, ServerResponse}
import reactor.core.publisher.Mono

import scala.compat.java8.FutureConverters
import scala.concurrent.Future
import scala.jdk.javaapi.CollectionConverters
import scala.language.postfixOps

@Service
class CounterHandlers(private val counterActorRef: ActorRef) {
  import CounterActor._

  def handleIncrement(request: ServerRequest): Mono[ServerResponse] = {
    counterActorRef ! Increment
    ServerResponse.accepted().build()
  }

  def handleGet(request: server.ServerRequest): Mono[ServerResponse] = {
    import scala.concurrent.duration._
    implicit val timeout: Timeout = Timeout(3.seconds)

    import akka.pattern.ask
    val future = counterActorRef ? GetStatus

    import scala.concurrent.ExecutionContext.Implicits.global
    val error = new RuntimeException("cannot request counter status")
    val result = future.fallbackTo(Future(error))
      .map {
        case status: String => status
        case exception: Throwable => exception.getLocalizedMessage
      }
      .map(counter => Map("status" -> counter))
      .map(status => CollectionConverters.asJava(status)) // must have! scala should be converted back to java

    val completionStage = FutureConverters.toJava(result)
    val mono = Mono.fromCompletionStage(completionStage)
    val responseType = new ParameterizedTypeReference[util.Map[String, String]]() {}
    ServerResponse.ok().body(mono, responseType)
  }
}

@Configuration
class RouterFunctionBuilderConfig(val handlers: CounterHandlers) {
  @Bean
  def routerFunctionBuilder: RouterFunction[ServerResponse] =
    RouterFunctions
      .route()
      .POST("/**", handlers handleIncrement _)
      .build()
      .andRoute(path("/**"), handlers handleGet _)
}

object CounterActor {
  val props: Props = Props[CounterActor]

  sealed trait Command
  object Increment extends Command
  object GetStatus extends Command
}

class CounterActor extends Actor with ActorLogging {
  import CounterActor._

  override def receive: Receive = updateState(0)

  private def updateState(counter: BigInt): Receive = {
    case GetStatus =>
      val response = s"current counter: $counter"
      log.info(response)
      sender() ! response
    case Increment =>
      log.info("incrementing a counter {}", counter)
      context.become(updateState(counter + 1))
  }
}

@Configuration
class CounterActorRefConfig(private val actorSystem: ActorSystem) {
  @Lazy @Bean
  def counterActorRef = actorSystem.actorOf(CounterActor.props)
}

@Configuration
class AkkaConfig {
  @Lazy @Bean def actorSystem: ActorSystem = ActorSystem.create("TweetsSystem")
  //@Lazy @Bean def actorMaterializer: ActorMaterializer = ActorMaterializer.create(actorSystem)
  @PreDestroy def preDestroy(): Unit = actorSystem.terminate()
}

@SpringBootApplication
class SpringBootScalaApplication

object SpringBootScalaApplication extends App {
  SpringApplication.run(classOf[SpringBootScalaApplication], args: _*)
}
