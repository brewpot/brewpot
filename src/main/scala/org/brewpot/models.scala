package org.brewpot

object models {

  case class Brew(id: String, name: String, style: String, OG: Double, ABV: Double, EBC: Int, IBU: Int, user: String)

}