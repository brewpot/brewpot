package org.brewpot

import unfiltered.request.{HttpRequest, Cookies}

object extractors {

  object LoggedOnUser {
    def unapply(req: HttpRequest[_]) =
      SessionToken.unapply(req).map(auth.tokenStore.get(_))
  }

  object SessionToken {
    def unapply[T](req: HttpRequest[T]) = {
      val cookies = Cookies.unapply(req).get
      cookies("user.token").map(_.value)
    }
  }

}
