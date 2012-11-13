package org.brewpot

import unfiltered.request.QParams._
import unfiltered.request._
import unfiltered.response.BadRequest
import org.scribe.model.{Token, Verifier}
import org.scribe.oauth.OAuthService
import util.Properties
import org.scribe.builder.ServiceBuilder
import org.scribe.builder.api.TwitterApi
import unfiltered.response.Redirect
import unfiltered.response.ResponseString

object auth {

  object Auth {

    def authToken(r: HttpRequest[_], details: ServiceDetails) = {
      val Host(host) = r
      val svc = details.svc(details.callback(host))
      val rt = svc.getRequestToken
      val uri = svc.getAuthorizationUrl(rt)
      Redirect(uri)
    }

    def callback(r: HttpRequest[_], details: ServiceDetails) = r match {
      case Params(ps) => {
        val expected = for {
          oauth_token <- lookup("oauth_token") is trimmed is required("The oauth_token is missing")
          oauth_verifier <- lookup("oauth_verifier") is trimmed is required("The oauth_verifier is missing")
        } yield (oauth_token, oauth_verifier)
        expected(ps).fold(
          err => BadRequest ~> ResponseString(err.toString),
          auth => {
            details.svc.getAccessToken(details.token(auth._1.get), new Verifier(auth._2.get))
          }
        )
      }
      Redirect("/")

    }
  }

  trait ServiceDetails {
    protected def key: String

    protected def secret: String

    def svc(callback: String): OAuthService

    def svc: OAuthService

    def token(token: String): Token = new Token(token, secret)

    def callback(base: String): String
  }

  object TwitterDetails extends ServiceDetails {
    protected val key = property("TWITTER_CONSUMER_KEY")
    protected val secret = property("TWITTER_CONSUMER_SECRET")

    def svc(callback: String) = new ServiceBuilder().provider(classOf[TwitterApi])
      .apiKey(key)
      .apiSecret(secret)
      .callback(callback)
      .build()

    val svc = new ServiceBuilder().provider(classOf[TwitterApi])
      .apiKey(key)
      .apiSecret(secret)
      .build()

    def callback(host: String) = {
      "%s://%s%s".format("http", host, "/auth/twitter/callback")
    }
  }


  def property(envVar: String) = Properties.envOrNone(envVar)
    .getOrElse(throw new IllegalArgumentException("Missing env variable for %s".format(envVar)))

  implicit def auth(req: HttpRequest[_]) = false

}