package org.brewpot

import jsonpicklers.Picklers._

object models {

  case class Recipe(id: Option[String], name: String, style: Option[String], OG: Option[Double], ABV: Option[Double], EBC: Option[Int], IBU: Option[Int], user: String)

  case class User(username: String, name: Option[String], avatar: Option[String])

  object User {
    val json = wrap(apply)(unapply(_).get) {
      ("screen_name" :: string) ~
        ("name" :: string).? ~
        ("profile_image_url" :: string).?
    }
  }
}