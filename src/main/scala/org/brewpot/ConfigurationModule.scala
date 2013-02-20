package org.brewpot

trait ConfigurationModule {

  trait Db {
    val host: String
    val port: Int
  }

}

trait HerokuConfiguration extends ConfigurationModule {

  trait Db {

  }

}