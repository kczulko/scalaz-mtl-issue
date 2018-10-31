import Dependencies._

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.8")

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.7",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "scalaz-mtl-issue",
    libraryDependencies ++= Seq(
      "org.scalaz" %% "scalaz-core" % "7.2.26",
      scalaTest % Test
    )
  )
