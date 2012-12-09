package org.brewpot.auth

import org.brewpot.common.EnvProperty
import org.scribe.oauth.OAuthService
import org.scribe.model.{Verb, OAuthRequest, Token}
import org.brewpot.entities.{TwitterUser, User}
import org.scribe.builder.ServiceBuilder
import org.scribe.builder.api.TwitterApi
import net.liftweb.json._
import scala.Some

trait AuthProvider extends EnvProperty {
  protected def key: String
  protected def secret: String
  def service(callback: Option[String] = None): OAuthService
  def token(token: String): Token = new Token(token, secret)
  def verify(token: Token): Option[User]
  def callback(base: String): String
}

object TwitterAuthProvider extends AuthProvider {
  protected val key = property("TWITTER_CONSUMER_KEY")
  protected val secret = property("TWITTER_CONSUMER_SECRET")

  def service(callback: Option[String] = None) = {
    val sb = new ServiceBuilder().provider(classOf[TwitterApi])
      .apiKey(key)
      .apiSecret(secret)

    callback.map(sb.callback(_)).getOrElse(sb).build()
  }

  def callback(host: String) = {
    "%s://%s%s".format("http", host, "/auth/twitter/callback")
  }

  def verify(token: Token) = {
    val req = new OAuthRequest(Verb.GET, "https://api.twitter.com/1/account/verify_credentials.json")
    service().signRequest(token, req)
    val json = parse(req.send().getBody)
    TwitterUser.json.unpickle(json) match {
      case jsonpicklers.Success(u, _) => Some(User(username = u.username, name = u.name, avatar = u.avatar))
      case _ => None
    }
  }
}