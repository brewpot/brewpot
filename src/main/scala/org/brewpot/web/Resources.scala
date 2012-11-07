package org.brewpot.web

import unfiltered.filter.Plan
import unfiltered.request.{Path, Seg}
import org.brewpot.service.{MainService, RecipeService}

object Resources extends Plan {

  def intent = {
    case r@Path(Seg(Nil)) => MainService.main(r)
    case r@Path(Seg("recipes" :: Nil)) => RecipeService.recipes
  }

}
