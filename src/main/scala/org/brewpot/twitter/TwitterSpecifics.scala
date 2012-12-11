package org.brewpot.twitter

import org.brewpot.Properties._
import scala.Some
import org.scribe.builder.ServiceBuilder
import org.scribe.builder.api.TwitterApi
import org.scribe.model.{Verb, OAuthRequest, Token}
import org.brewpot.model.Entities.{TwitterUser, User}
import net.liftweb.json._
import scala.Some
import org.brewpot.auth.Provider

object TwitterSpecifics extends Provider {
  protected val key = get("TWITTER_CONSUMER_KEY")
  protected val secret = get("TWITTER_CONSUMER_SECRET")

  def service(callback: Option[String] = None) = {
    val sb = new ServiceBuilder().provider(classOf[TwitterApi])
      .apiKey(key)
      .apiSecret(secret)

    callback.map(sb.callback(_)).getOrElse(sb).build()
  }

  def callback(host: String) = {
    "%s://%s%s".format("http", host, "/auth/twitter/callback")
  }

  def verify(token: Token): Option[User] = {
    val req = new OAuthRequest(Verb.GET, "https://api.twitter.com/1/account/verify_credentials.json")
    service().signRequest(token, req)
    val json = parse(req.send().getBody)
    TwitterUser.json.unpickle(json) match {
      case jsonpicklers.Success(u, _) => Some(u.normalize)
      case _ => None
    }
  }
}
