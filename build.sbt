import com.typesafe.startscript.StartScriptPlugin

seq(StartScriptPlugin.startScriptForClassesSettings: _*)

name := "brewpot"

scalaVersion := "2.9.2"

crossScalaVersions := Seq("2.9.2")

organization := "org.brewpot"

version := "1.0-SNAPSHOT"

homepage := Some(new URL("http://github.com/kareblak/brewpot"))

startYear := Some(2012)

licenses := Seq(("Apache 2", new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")))

libraryDependencies ++=
  Seq(
    "net.databinder" %% "unfiltered" % "0.6.3",
    "net.databinder" %% "unfiltered-filter" % "0.6.3",
    "net.databinder" %% "unfiltered-jetty" % "0.6.3",
    "net.databinder.dispatch" %% "dispatch-core" % "0.9.4",
    "org.specs2" %% "specs2" % "1.12.2" % "test"
  )
