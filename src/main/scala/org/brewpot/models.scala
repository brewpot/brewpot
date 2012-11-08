package org.brewpot

object models {

  case class Recipe(id: String, name: String, style: Option[String], OG: Option[Double], ABV: Option[Double], EBC: Option[Int], IBU: Option[Int], user: String)

}