package org.brewpot.handlers

import unfiltered.request._
import unfiltered.response._
import org.brewpot.web.Views
import org.brewpot.auth.TokenUser
import org.brewpot.model.Entities.User

object UserHandler {

  def handleProfile(req: HttpRequest[_]) = req match {
    case TokenUser(user) if user.isDefined => Views.profilePage(user.get)
    case _ => Views.profilePage(User("1337", "kareblak", Some("Juban Jubanar"), Some("https://twimg0-a.akamaihd.net/profile_images/1680566367/6bdba1daac8c4e62a9b759b5523b533c_7_bigger.jpg")))
  }

}
