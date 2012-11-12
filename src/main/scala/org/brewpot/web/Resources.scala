package org.brewpot.web

import unfiltered.filter.Plan
import unfiltered.request._
import org.brewpot.handlers._
import unfiltered.response.{ResponseString, Ok}
import scala.Either
import dispatch.Promise

object Resources extends Plan {

  def intent = {
    case Path(Seg(Nil)) => MainPageHandler.main
    case r @ Path(Seg("recipes" :: Nil)) => r match {
      case GET(_) => RecipeHandler.recipes
      case POST(_) => RecipeHandler.addRecipe(r)
    }
    case Path(Seg("login" :: Nil)) => {
      AuthHandler.login
      Ok
    }
    case r @ Path(Seg("login" :: "oauth_callback" :: Nil)) => AuthHandler.request_token_callback(r)

  }

}
