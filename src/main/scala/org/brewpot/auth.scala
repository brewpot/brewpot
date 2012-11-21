package org.brewpot

import unfiltered.request.QParams._
import unfiltered.request._
import unfiltered.response._
import unfiltered.Cookie

import org.scribe.model._
import org.scribe.oauth.OAuthService
import org.scribe.builder.ServiceBuilder
import org.scribe.builder.api.TwitterApi
import collection.mutable
import jsonpicklers.Success
import net.liftweb.json.parse

import web.views
import org.brewpot.common.EnvProperty
import org.brewpot.extractors.SessionToken
import org.brewpot.entities.{TwitterUser, User}

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
      case _ => views.mainPage(None, Some("Missing OAuth params"))
    }

    def logout(req: HttpRequest[_]) = req match {
      case SessionToken(token) => {
        applyCookie("")
        tokenStore -= token
        Redirect("/")
      }
      case _ => Unauthorized ~> views.mainPage
    }

    private def accessTokenFailed(err: String) = views.mainPage(None, Some(err))

    private def accessToken(req: HttpRequest[_], auth: (Option[String], Option[String])) =
      details.svc.getAccessToken(details.token(auth._1.get), new Verifier(auth._2.get))

    private def applyCookie(value: String) =
      SetCookies(Cookie(name = "user.token", value = value, path = Some("/"))) ~> Redirect("/")

    private def verifyToken(token: Token) = {
      // TODO: Address needs to be generic
      val request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1/account/verify_credentials.json")
      details.svc.signRequest(token, request)
      val response = request.send()
      val parsed = parse(response.getBody)
      // TODO: Needs to be generic, type User
      val Success(p, _) = TwitterUser.json.unpickle(parsed)
      cleanOldTokens(p)
      tokenStore += (token.getToken -> p)
      applyCookie(token.getToken)
    }

    private def cleanOldTokens(u: User) =
      tokenStore.find(_._2.username == u.username).foreach(tokenStore -= _._1)

  }

  trait ServiceDetails extends EnvProperty {
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

}