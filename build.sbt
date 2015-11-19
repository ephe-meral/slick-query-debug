lazy val commonSettings = Seq(
  version := "0.1.0",
  scalaVersion := "2.11.7")

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "slick-query-debug",
    libraryDependencies ++= List(
      "com.typesafe.slick" %% "slick" % "3.0.0",
      "ch.qos.logback" % "logback-classic" % "1.0.9",
      "com.h2database" % "h2" % "1.3.175",
      "org.scalatest" %% "scalatest" % "2.2.4" % "test"))
