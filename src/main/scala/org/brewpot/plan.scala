package org.brewpot

import unfiltered.filter.Plan
import unfiltered.request._
import org.brewpot.handlers._
import org.brewpot.auth.{TwitterDetails, Auth}

object plan extends Plan {

  def intent = {
    case req @ Path(Seg(Nil)) => MainPageHandler.main(req)
    case req @ Path(Seg("recipes" :: Nil)) => req match {
      case GET(_) => RecipeHandler.recipes
      case POST(_) => RecipeHandler.addRecipe(req)
    }
    case req @ Path(Seg("auth" :: "twitter" :: "login" :: Nil)) => Auth.authToken(req, TwitterDetails)
    case req @ Path(Seg("auth" :: "twitter" :: "callback" :: Nil)) => Auth.callback(req, TwitterDetails)

  }

}
