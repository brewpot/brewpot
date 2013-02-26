package org.brewpot

import org.brewpot.model._
import util.Properties


trait DataProviderModule {
  def fetchGrains: Seq[Grain]
  def fetchHops: Seq[Hop]
}

trait MongoDbDataProvider extends DataProviderModule {

  import com.mongodb.casbah.Imports._
  import Templates._
  val where = MongoDBObject

  val mongoClient = Properties.envOrNone("MONGOLAB_URI").map(MongoClient(_)).getOrElse(MongoClient())
  val mongoDb = mongoClient("brewpot")

  def fetchGrains: Seq[Grain] = mongoDb("grains").find.map(grainTemplate).collect{case Some(g) => g}.toSeq

  def fetchHops: Seq[Hop] = mongoDb("hops").find.map(hopTemplate).collect{case Some(h) => h}.toSeq

  object Templates {
    val grainTemplate = (g: DBObject) =>
      for {
        n <- g.getAs[String]("name")
        p <- g.getAs[Double]("potential")
      } yield Grain(n, p)

    val hopTemplate = (h: DBObject) =>
      for {
        n <- h.getAs[String]("name")
        aa <- h.getAs[Double]("alpha_acid")
      } yield Hop(n, aa)
  }
}