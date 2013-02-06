import com.typesafe.startscript.StartScriptPlugin

seq(StartScriptPlugin.startScriptForClassesSettings: _*)

name := "brewpot"

scalaVersion := "2.10.0"

crossScalaVersions := Seq("2.10.0")

organization := "org.brewpot"

version := "1.0-SNAPSHOT"

homepage := Some(new URL("http://github.com/brewpot/brewpot"))

startYear := Some(2012)

licenses := Seq(("Apache 2", new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")))

resolvers ++= Seq(
    "spray repo" at "http://repo.spray.io",
    "typesafe repo" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
    "io.spray" % "spray-can" % "1.1-M7",
    "io.spray" % "spray-routing" % "1.1-M7",
    "com.typesafe.akka" %% "akka-actor" % "2.1.0",
    "org.specs2" %% "specs2" % "1.13" % "test",
    "io.spray" % "spray-testkit" % "1.1-M7" % "test"
)

pomExtra := (
  <scm>
    <url>git@github.com:brewpot/brewpot.git</url>
    <connection>scm:git:git@github.com:brewpot/brewpot.git</connection>
  </scm>
  <developers>
    <developer>
      <id>kareblak</id>
      <name>Kåre Blakstad</name>
      <url>http://github.com/kareblak</url>
    </developer>
  </developers>)
