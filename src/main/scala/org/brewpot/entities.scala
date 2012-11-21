package org.brewpot

import jsonpicklers.Picklers._

object entities {

  case class Recipe(id: String, user: String, data: RecipeData)

  case class RecipeData(name: String, style: Option[String], OG: Option[Double], ABV: Option[Double], EBC: Option[Int], IBU: Option[Int])

  object RecipeData {
    val json = wrap(apply)(unapply(_).get) {
      ("name"               :: string) ~
      ("style"              :: string).? ~
      ("og"                 :: double).? ~
      ("abv"                :: double).? ~
      ("ebc"                :: int).? ~
      ("ibu"                :: int).?
    }
  }

  case class TwitterUser(username: String, name: Option[String], avatar: Option[String]) extends User

  object TwitterUser {
    val json = wrap(apply)(unapply(_).get) {
      ("screen_name"        :: string) ~
      ("name"               :: string).? ~
      ("profile_image_url"  :: string).?
    }
  }

  trait User {
    val username: String
    val name: Option[String]
    val avatar: Option[String]
  }

}