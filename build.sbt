lazy val libDeps = {
  val jschVersion = "0.1.54"

  Seq(
    "com.jcraft" % "jsch" % jschVersion
  )
}

lazy val testDeps = {
  val dockerItScalaVersion = "0.9.0"
  val scalaTestVersion = "3.0.3"

  Seq(
    "com.whisk" %% "docker-testkit-scalatest" % dockerItScalaVersion % Test,
    "com.whisk" %% "docker-testkit-impl-spotify" % dockerItScalaVersion % Test,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  )
}

lazy val root = (project in file("."))
  .settings(
    name := "integrationtests",
    scalaVersion := "2.12.2",
    libraryDependencies ++= libDeps,
    libraryDependencies ++= testDeps,
    fork in Test := true
  )
