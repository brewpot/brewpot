package org.brewpot.web

import unfiltered.filter.Plan
import unfiltered.request._
import org.brewpot.handlers.{RecipeHandler, MainPageHandler}

object Resources extends Plan {

  def intent = {
    case Path(Seg(Nil)) => MainPageHandler.main
    case r @ Path(Seg("recipes" :: Nil)) => r match {
      case GET(_) => RecipeHandler.recipes
      case POST(_) => RecipeHandler.addRecipe(r)
    }
//    case Path(Seg("login" :: Nil)) => AuthHandler.login

  }

}
