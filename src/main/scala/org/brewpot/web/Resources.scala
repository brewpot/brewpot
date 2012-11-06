package org.brewpot.web

import unfiltered.filter.Plan
import unfiltered.request.{Path, Seg}
import unfiltered.response.{NotFound, Ok}

object Resources extends Plan {

  def intent = {
    case r@Path(Seg("main" :: Nil)) => Ok
    case r@Path(Seg("recipe" :: id :: Nil)) => Ok
    case r@Path(Seg("login" :: Nil)) => Ok
  }

}
