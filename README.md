# spring-boot-akka-example [![Build Status](https://travis-ci.org/daggerok/spring-boot-akka-example.svg?branch=master)](https://travis-ci.org/daggerok/spring-boot-akka-example)
Spring Boot Scala starter using Gradle / Maven build tools to build reactive apps.

## getting started

```bash
git clone --no-single-branch --depth=1 https://github.com/daggerok/spring-boot-akka-example.git -b spring-boot-all
cd spring-boot-akka-example
```

## maven

_spring-boot_

```bash
./mvnw spring-boot:run
```

_scala-maven-plugin_

```bash
mvn scala:run -DmainClass=com.github.daggerok.SpringBootScalaApplication
```

_fat jar_

```bash
./mvnw package
java -jar target/*.jar
```

_project sources archive_

find archive with all project sources in target folder too:

```bash
./mvnw
unzip -d target/sources target/*-sources.zip
unzip -d target/default target/*-src.zip
```

## gradle

_gradle spring-boot plugin_

```bash
./gradlew bootRun
```

_fat jar_

```bash
./gradlew
java -jar build/libs/*.jar
```

_project sources archive_

to create archive with all project sources use gradle _sources_ task, like so:

```bash
./gradlew sources
unzip -d build/sources build/*.zip
```

## test

```bash
http post :8080
http post :8080
http get  :8080
```

NOTE: _This project has been based on [GitHub: daggerok/spring-boot-akka-example (spring-boot-scala branch)](https://github.com/daggerok/spring-boot-akka-example/tree/spring-boot-scala)_

<!--
_update versions_

```bash
./mvnw versions:display-property-updates
./gradlew dependencyUpdates -Drevision=release
```
-->
