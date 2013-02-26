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

  private val where = MongoDBObject

  val MongoSetting(db) = Properties.envOrNone("MONGOLAB_URI")

  def fetchGrains: Seq[Grain] = db("grains").find.map(grainTemplate).collect {
    case Some(g) => g
  }.toSeq

  def fetchHops: Seq[Hop] = db("hops").find.map(hopTemplate).collect {
    case Some(h) => h
  }.toSeq

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

  object MongoSetting {
    def unapply(url: Option[String]): Option[MongoDB] = {
      val regex = """mongodb://(\w+):(\w+)@([\w|\.]+):(\d+)/(\w+)""".r
      url match {
        case Some(regex(u, p, host, port, dbName)) =>
          val db = MongoConnection(host, port.toInt)(dbName)
          db.authenticate(u, p)
          Some(db)
        case _ => Some(MongoConnection("127.0.0.1", 27017)("test"))
      }
    }
  }

}