package org.brewpot

import util.Properties

trait ConfigurationModule {
  val dbEnvVar: String
  lazy val dbLocation: Option[String] = Properties.propOrNone(dbEnvVar)
}