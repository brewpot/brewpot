package org.brewpot

import unfiltered.filter.Plan
import unfiltered.request._
import org.brewpot.handlers._
import org.brewpot.auth.{AuthHandler, TwitterDetails}

object plan extends Plan {

  lazy val twitterHandler = new AuthHandler(TwitterDetails)

  def intent = {
    case req @ Path(Seg(Nil)) => MainPageHandler.main(req)
    case req @ Path(Seg("recipes" :: Nil)) => req match {
      case GET(_) => RecipeHandler.recipes
      case POST(_) => RecipeHandler.addRecipe(req)
    }
    case req @ Path(Seg("auth" :: "twitter" :: "login" :: Nil)) => twitterHandler.authToken(req)
    case req @ Path(Seg("auth" :: "twitter" :: "callback" :: Nil)) => twitterHandler.callback(req)

  }

}
