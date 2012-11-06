package org.brewpot.web

import unfiltered.filter.Plan
import unfiltered.request.{Path, Seg}
import unfiltered.response.{NotFound, Ok}

object Resources extends Plan {

  def intent = {
    case r@Path(Seg(Nil)) => Views.main
    case r@Path(Seg("recipes" :: Nil)) => Views.recipes
    case r@Path(Seg("login" :: Nil)) => Views.login
    case r@Path(Seg("about" :: Nil)) => Views.about
  }

}
