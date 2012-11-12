package org.brewpot.web

import unfiltered.filter.Plan
import unfiltered.request._
import org.brewpot.handlers._
import unfiltered.response.Ok
import org.scribe.builder.api.TwitterApi

object Resources extends Plan {

  def intent = {
    case r @ Path(Seg(Nil)) => MainPageHandler.main
    case r @ Path(Seg("recipes" :: Nil)) => r match {
      case GET(_) => RecipeHandler.recipes
      case POST(_) => RecipeHandler.addRecipe(r)
    }
    case r @ Path(Seg("auth" :: "login" :: Nil)) => AuthHandler.requestToken
    case r @ Path(Seg("auth" :: "callback" :: Nil)) => AuthHandler.callback(r)

  }

}
