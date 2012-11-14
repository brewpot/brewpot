package org.brewpot

import jsonpicklers.Picklers._

object models {

  case class Recipe(id: Option[String], name: String, style: Option[String], OG: Option[Double], ABV: Option[Double], EBC: Option[Int], IBU: Option[Int], user: String)

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