package org.brewpot.handlers

import auth.TwitterAuth
import unfiltered.response.ResponseString
import unfiltered.request.HttpRequest
import javax.servlet.http.HttpServletRequest

object AuthHandler {

    def login = new TwitterAuth("http://localhost:8888/login/oauth_callback").fetchRequestToken

    def request_token_callback(r: HttpRequest[HttpServletRequest]) = ResponseString(r.underlying.toString)


}