package org.brewpot

import org.brewpot.model.Grain


trait DataProviderModule {
  def fetchGrains: Seq[Grain]
}

trait MongoDbDataProvider extends DataProviderModule with ConfigurationModule {

  import com.mongodb.casbah.Imports._
  import Templates._
  val where = MongoDBObject

  val dbEnvVar: String = "MONGOLAB_URI"

  val mongoClient = dbLocation.map(MongoClient(_)).getOrElse(MongoClient())
  val mongoDb = mongoClient("brewpot")


  def fetchGrains: Seq[Grain] = mongoDb("grains").find.map(grainTemplate).collect{case Some(g) => g}.toSeq

  object Templates {
    val grainTemplate = (g: DBObject) =>
      for {
        n <- g.getAs[String]("name")
        p <- g.getAs[Double]("potential")
      } yield Grain(n, p)
  }
}