package org.brewpot

import unfiltered.request.QParams._
import unfiltered.request._
import unfiltered.response._
import org.scribe.model.{Verb, OAuthRequest, Token, Verifier}
import org.scribe.oauth.OAuthService
import util.Properties
import org.scribe.builder.ServiceBuilder
import org.scribe.builder.api.TwitterApi
import collection.mutable
import net.liftweb.json.parse
import jsonpicklers._
import org.brewpot.models.User
import web.Views
import unfiltered.Cookie
import org.brewpot.extractors.{SessionToken, UserWithToken}
import unfiltered.response.Redirect
import scala.Some
import jsonpicklers.Success
import unfiltered.Cookie

object auth {

  val tokenStore = new mutable.HashMap[String, User]()

  class AuthFlow(details: ServiceDetails) {

    def authToken(r: HttpRequest[_]) = {
      val Host(host) = r
      val svc = details.svc(details.callback(host))
      val rt = svc.getRequestToken
      val uri = svc.getAuthorizationUrl(rt)
      Redirect(uri)
    }

    def callback(req: HttpRequest[_]) = req match {
      case Params(ps) => {
        val expected = for {
          oauth_token <- lookup("oauth_token") is trimmed is required("The oauth_token is missing")
          oauth_verifier <- lookup("oauth_verifier") is trimmed is required("The oauth_verifier is missing")
        } yield (oauth_token, oauth_verifier)
        expected(ps).fold(
          err => accessTokenFailed("Failed to login"),
          auth => {
            val at = accessToken(req, auth)
            verifyToken(at)
          }
        )
      }
      case _ => Views.main(None, Some("Missing OAuth params"))
    }

    def logout(req: HttpRequest[_]) = req match {
      case SessionToken(token) => {
        setTokenCookie("")
        tokenStore -= token
        Redirect("/")
      }
      case _ => Unauthorized
    }

    private def accessTokenFailed(err: String) = Views.main(None, Some(err))

    private def accessToken(req: HttpRequest[_], auth: (Option[String], Option[String])) =
      details.svc.getAccessToken(details.token(auth._1.get), new Verifier(auth._2.get))

    private def setTokenCookie(value: String) =
      SetCookies(Cookie(name = "user.token", value = value, path = Some("/"))) ~> Redirect("/")

    private def verifyToken(token: Token) = {
      val request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1/account/verify_credentials.json")
      details.svc.signRequest(token, request)
      val response = request.send()
      val parsed = parse(response.getBody)
      val Success(p, _) = User.json.unpickle(parsed)
      tokenStore += (token.getToken -> p)
      setTokenCookie(token.getToken)
    }

  }

  trait ServiceDetails extends EnvProperty {
    protected def key: String

    protected def secret: String

    def svc(callback: String): OAuthService

    def svc: OAuthService

    def token(token: String): Token = new Token(token, secret)

    def callback(base: String): String
  }

  trait EnvProperty {
    def property(property: String) = Properties.envOrNone(property)
      .getOrElse(throw new IllegalArgumentException("Missing env variable for %s".format(property)))
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

}