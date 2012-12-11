package org.brewpot.handlers

import unfiltered.request.HttpRequest
import org.brewpot.auth.TokenUser
import org.brewpot.web.Views

object FrontHandler {

  def handleMain(req: HttpRequest[_]) = req match {
    case TokenUser(user) => {
      Views.mainPage(user, None)
    }
    case _ => Views.mainPage
  }

}
