package org.brewpot.auth

import unfiltered.request._
import unfiltered.response._
import unfiltered.request.QParams._
import scala.Some
import org.brewpot.web.views
import org.scribe.model.{Token, Verifier}
import unfiltered.Cookie
import org.brewpot.entities.User
import TokenProvider._
import UserProvider._
import util.Random

class Auth[A <: AuthProvider](authProvider: A)(implicit manifest: Manifest[A]) {

  def authToken(r: HttpRequest[_]) = {
    val Host(host) = r
    val svc = authProvider.service(Option(authProvider.callback(host)))
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
        auth => verify(accessToken(req, auth))
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
    authProvider.service().getAccessToken(authProvider.token(auth._1.get), new Verifier(auth._2.get))

  private def applyCookie(value: String) =
    SetCookies(Cookie(name = "user.token", value = value, path = Some("/")))

  /**
   * Here be dragons.
   */
  private def verify(ext: Token) = {
    authProvider.verify(ext).map { u =>
      verifyUser(u)
      cleanOldTokens(u)
      val token = generateToken(64)
      tokenStore += (token -> u)
      applyCookie(token) ~> Redirect("/")
    }.getOrElse(Forbidden)
  }

  private def verifyUser(user: User) {
    val clazz = manifest.erasure.asInstanceOf[Class[A]]
    if (!userStore.contains((user.id, clazz))) {
      userStore += ((user.id, clazz) -> user)
      println(userStore)
    }
  }

  private def generateToken(len: Int) = {
    val chars = ('a' to 'z') ++ ('A' to 'Z') ++ ('1' to '9')
    (1 to len).map(n => chars(Random.nextInt(chars.length))).mkString
  }

  private def cleanOldTokens(u: User) =
    tokenStore.find(_._2.username == u.username).foreach(tokenStore -= _._1)

}

object TokenUser {

  def unapply(req: HttpRequest[_]) =
    SessionToken.unapply(req).map(tokenStore.get(_))
}

object SessionToken {
  def unapply[T](req: HttpRequest[T]) =
    Cookies.unapply(req).get.apply("user.token").map(_.value)
}
