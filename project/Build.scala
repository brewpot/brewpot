import sbt._
import sbt.Keys._
import scala.Some
import com.typesafe.startscript.StartScriptPlugin

object Build extends Build {

  lazy val root = Project(
    id = "brewpot",
    base = file("."),
    settings = Defaults.defaultSettings ++
      Seq(
        name                := Settings.name,
        description         := Settings.description,
        scalaVersion        := Settings.scalaVersion,
        organization        := Settings.organization,
        version             := Settings.version,
        homepage            := Settings.homepage,
        licenses            := Settings.licenses,
        pomExtra            := Settings.pomExtra,
        libraryDependencies ++= External.libraryDependencies,
        resolvers           ++= External.resolvers
    )
    ++ seq(StartScriptPlugin.startScriptForClassesSettings: _*)
  )

  object Settings {
    val name = "brewpot"
    val description = "Brewpot"
    val scalaVersion = "2.10.0"
    val organization = "org.brewpot"
    val version = "1.0-SNAPSHOT"
    val homepage = Some(new URL("http://github.com/brewpot/brewpot"))
    val startYear = Some(2012)
    val licenses = Seq(("Apache 2", new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")))
    val pomExtra = {
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
        </developers>
    }
  }

  object External {
    val resolvers = Seq(
      "sonatype repo" at "https://oss.sonatype.org/content/repositories/releases/",
      "typesafe repo" at "http://repo.typesafe.com/typesafe/releases/"
    )
    val libraryDependencies = Seq(
      "net.databinder" %% "unfiltered" % "0.6.7",
      "net.databinder" %% "unfiltered-filter" % "0.6.7",
      "net.databinder" %% "unfiltered-jetty" % "0.6.7",
      "org.json4s" %% "json4s-native" % "3.1.0",
      "org.mongodb" %% "casbah" % "2.5.0",
      "org.clapper" %% "grizzled-slf4j" % "1.0.1",
      "com.jteigen" %% "unfiltered-directives" % "0.1",
      "com.jteigen" %% "jsonpicklers" % "0.2-SNAPSHOT"
    )
  }

}
