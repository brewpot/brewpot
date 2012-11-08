package org.brewpot.web

import unfiltered.filter.Plan
import unfiltered.request._
import org.brewpot.service.{MainService, RecipeService}
import unfiltered.response.Ok

object Resources extends Plan {

  def intent = {
    case Path(Seg(Nil)) => MainService.main
    case r @ Path(Seg("recipes" :: Nil)) => r match {
      case GET(_) => RecipeService.recipes
      case POST(_) => RecipeService.addRecipe(r)
    }

  }

}
