ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.8"
ThisBuild / crossScalaVersions := Seq(scalaVersion.value, "3.1.1")
ThisBuild / organization := "io.typecraft"
ThisBuild / isSnapshot := true

val circeVersion = "0.14.1"

lazy val core = (project in file("core"))
  .settings(
    name := "ender-core",
    resolvers += Resolver.mavenLocal,
    libraryDependencies ++= Seq(
      "org.tpolecat" %% "doobie-core" % "1.0.0-RC2",
      "org.scalatest" %% "scalatest" % "3.2.11" % "test",
      "org.scalatest" %% "scalatest-flatspec" % "3.2.11" % "test"
    ) ++ Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser",
      "io.circe" %% "circe-yaml"
    ).map(_ % circeVersion % Provided),
    publishM2Configuration := publishM2Configuration.value.withOverwrite(true),
    publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(
      true
    )
  )

lazy val bukkit = (project in file("bukkit"))
  .dependsOn(core)
  .settings(
    name := "ender-bukkit",
    resolvers ++= Seq(
      "SpigotMC" at "https://hub.spigotmc.org/nexus/content/repositories/snapshots/",
      "Sonatype" at "https://oss.sonatype.org/content/repositories/snapshots/",
      "EngineHub" at "https://maven.enginehub.org/repo/",
      Resolver.mavenLocal
    ),
    libraryDependencies ++= Seq(
      "org.spigotmc" % "spigot" % "1.17.1-R0.1-SNAPSHOT" % Provided,
      "org.typelevel" %% "cats-core" % "2.7.0"
    ) ++ Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser",
      "io.circe" %% "circe-yaml"
    ).map(_ % circeVersion % Provided),
    publishM2Configuration := publishM2Configuration.value.withOverwrite(true),
    publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(
      true
    )
  )
