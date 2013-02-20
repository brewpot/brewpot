package org.brewpot

import org.brewpot.model.Grain


trait DataProviderModule {
  def grains: Seq[Grain]
}

trait MongoDbDataProvider extends DataProviderModule with ConfigurationModule {

  import com.mongodb.casbah.Imports._

  val dbEnvVar: String = "MONGOLAB_URI"

  val mongoClient = dbLocation.map(MongoClient(_)).getOrElse(MongoClient)

  def grains: Seq[Grain] = Nil
}