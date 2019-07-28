import org.gradle.api.JavaVersion

object Globals {
  object Project {
    const val artifactId = "spring-boot-akka-example"
    const val groupId = "com.github.daggerok"
    const val version = "1.0.0-SNAPSHOT"
  }

  const val scalaVersion = "2.13.0" // "2.12.8"
  const val scalaMajorVersion = "2.13" // "2.12"
  const val reactiveStreamsVersion = "1.0.2"
  const val akkaVersion = "2.6.0-M5"
  const val scalatestVersion = "3.0.8"

  val javaVersion = JavaVersion.VERSION_1_8
  const val vavrVersion = "0.10.0"
  const val lombokVersion = "1.18.8"
  const val junitVersion = "4.13-beta-3"
  const val junitJupiterVersion = "5.5.1"

  object Gradle {
    const val wrapperVersion = "5.5.1"

    object Plugin {
      const val lombokVersion = "3.1.0"
      const val versionsVersion = "0.21.0"
      const val springBootVersion = "2.2.0.M4"
      const val dependencyManagementVersion = "1.0.8.RELEASE"
    }
  }
}
