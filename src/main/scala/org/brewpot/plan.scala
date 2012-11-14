package org.brewpot

import unfiltered.filter.Plan
import unfiltered.request._
import org.brewpot.handlers._
import org.brewpot.auth.{AuthFlow, TwitterDetails}

object plan extends Plan {

  lazy val twitterFlow = new AuthFlow(TwitterDetails)

  def intent = {
    case req @ Path(Seg(Nil)) => MainPageHandler.main(req)
    case req @ Path(Seg("recipes" :: Nil)) => req match {
      case GET(_) => RecipeHandler.recipes(req)
      case POST(_) => RecipeHandler.addRecipe(req)
    }
    case req @ Path(Seg("auth" :: "twitter" :: "login" :: Nil)) => twitterFlow.authToken(req)
    case req @ Path(Seg("auth" :: "twitter" :: "callback" :: Nil)) => twitterFlow.callback(req)

  }

}
