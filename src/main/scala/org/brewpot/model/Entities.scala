package org.brewpot.model

import jsonpicklers.Picklers._
import org.brewpot.twitter.TwitterImage

object Entities {

  case class Recipe(id: String, user: String, data: RecipeData)

  case class User(id: String, username: String, name: Option[String] = None, avatar: Option[String] = None)

  case class GuildMember(brewer: User, admin: Boolean)

  case class BrewGuild(name: String, members: Seq[GuildMember])

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

  case class TwitterUser(id: Int, username: String, name: Option[String], avatar: Option[String]) {

    def normalize = User(id.toString, username, name, avatar.map(TwitterImage.toBigger(_)))

  }

  object TwitterUser {
    val json = wrap(apply)(unapply(_).get) {
      ("id"                       :: int) ~
      ("screen_name"              :: string) ~
      ("name"                     :: string).? ~
      ("profile_image_url_https"  :: string).?
    }
  }


}