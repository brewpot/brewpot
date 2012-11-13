package org.brewpot

import unfiltered.request.QParams._
import unfiltered.request.{Params, Seg, Path, HttpRequest}
import unfiltered.response.{NotFound, ResponseString, BadRequest, Redirect}
import org.scribe.model.{Token, Verifier}
import org.scribe.oauth.OAuthService
import util.Properties
import org.scribe.builder.ServiceBuilder
import org.scribe.builder.api.TwitterApi

object auth {

  object Auth {

    def authToken(svc: ServiceDetails) = {
      val rt = svc.svc.getRequestToken
      val uri = svc.svc.getAuthorizationUrl(rt)
      Redirect(uri)
    }

    def callback(r: HttpRequest[_], svc: ServiceDetails) = r match {
      case Params(ps) => {
        val expected = for {
          oauth_token <- lookup("oauth_token") is trimmed is required("The oauth_token is missing")
          oauth_verifier <- lookup("oauth_verifier") is trimmed is required("The oauth_verifier is missing")
        } yield (oauth_token, oauth_verifier)
        expected(ps).fold(
          err => BadRequest ~> ResponseString(err.toString),
          auth => svc.svc.getAccessToken(svc.token(auth._1.get), new Verifier(auth._2.get))
        )
      }
      Redirect("/")

    }
  }

  trait ServiceDetails {
    protected def key: String
    protected def secret: String
    protected def callback: String

    def svc: OAuthService

    def token(token: String): Token = new Token(token, secret)
  }

  object TwitterDetails extends ServiceDetails {
    protected val key = property("TWITTER_CONSUMER_KEY")

    protected val secret = property("TWITTER_CONSUMER_SECRET")

    protected val callback = "http://localhost:8888/auth/twitter/callback"

    val svc = new ServiceBuilder().provider(classOf[TwitterApi])
      .apiKey(key)
      .apiSecret(secret)
      .callback(callback)
      .build()

  }

  def property(envVar: String) = Properties.envOrNone(envVar)
    .getOrElse(throw new IllegalArgumentException("Missing env variable for %s".format(envVar)))

  implicit def auth(req: HttpRequest[_]) = false

}