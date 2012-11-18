package org.brewpot.model

import jsonpicklers.Picklers._

object entities {

  case class Recipe(id: Option[String], name: String, style: Option[String], OG: Option[Double], ABV: Option[Double], EBC: Option[Int], IBU: Option[Int], user: String)

  object Recipe {
    def apply(p: PickledRecipe, u: User): Recipe = Recipe(None, p.name, p.style, p.OG, p.ABV, p.EBC, p.IBU, u.username)
  }

  case class PickledRecipe(name: String, style: Option[String], OG: Option[Double], ABV: Option[Double], EBC: Option[Int], IBU: Option[Int])

  object PickledRecipe {
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