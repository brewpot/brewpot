package org.brewpot.handlers

import auth.Auth
import unfiltered.response.ResponseString
import com.ning.http.client.oauth.RequestToken

object AuthHandler {

    def login = {
      var auth = new Auth("http://brewpot.org/login/callback")
      ResponseString(auth.toString)
    }


}