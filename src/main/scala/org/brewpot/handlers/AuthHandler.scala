package org.brewpot.handlers

import auth.{GoogleAuth}
import unfiltered.response.ResponseString
import unfiltered.request.HttpRequest
import javax.servlet.http.HttpServletRequest

object AuthHandler {

    def login = new GoogleAuth("http://brewpot.herokuapp.com/oauth2callback").fetchRequestToken

    def oauth2callback(r: HttpRequest[HttpServletRequest]) = ResponseString(r.underlying.toString)


}