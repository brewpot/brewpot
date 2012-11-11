package org.brewpot.handlers

import dispatch.{:/, Http}

object AuthHandler {

//  val svc = Twitter.host / "oauth"
//
//    def request_token(consumer: Consumer, oauth_callback: String) =
//      svc.secure / "request_token" <<
//        Map("oauth_callback" -> oauth_callback) <@ consumer as_token


  object Twitter {
    val host = :/("twitter.com")
    val search = :/("search.twitter.com")
  }


}
