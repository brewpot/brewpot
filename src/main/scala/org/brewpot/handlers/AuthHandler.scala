package org.brewpot.handlers

import auth.{TwitterConsumer, TwitterAuth}
import unfiltered.response.{Redirect, ServiceUnavailable, ResponseString}
import unfiltered.request.HttpRequest
import javax.servlet.http.HttpServletRequest
import org.scribe.oauth.OAuthService
import org.scribe.builder.ServiceBuilder
import org.scribe.builder.api.{Api, TwitterApi}
import util.Properties

object AuthHandler extends TwitterConsumer {

  val twitter = new ServiceBuilder().provider(classOf[TwitterApi])
    .apiKey(consumer.getKey)
    .apiSecret(consumer.getSecret)
    .callback("http://localhost:8888/auth/callback")
    .build()

  def requestToken = {
    ResponseString(twitter.getRequestToken.getToken)
  }

  def callback(r: HttpRequest[HttpServletRequest]) = ResponseString(r.underlying.toString)


}