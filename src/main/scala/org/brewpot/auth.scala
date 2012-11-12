package auth

import dispatch._
import oauth._
import com.ning.http.client.oauth.{RequestToken, ConsumerKey}
import util.Properties

class TwitterAuth(val callback: String) extends oauth.Exchange
with oauth.SomeCallback
with oauth.SomeHttp
with TwitterConsumer
with TwitterEndpoints {
  val http = dispatch.Http

  override def fetchRequestToken: Promise[Either[String,RequestToken]] = {
    val promised = http(
      url(requestToken)
        << Map("oauth_callback" -> callback)
        <@ (consumer)
        > as.oauth.Token
    )
    for (eth <- message(promised, "request token")) yield eth.joinRight
  }

}

trait TwitterEndpoints extends oauth.SomeEndpoints {
  val requestToken = "https://api.twitter.com/oauth/request_token"
  val accessToken = "https://api.twitter.com/oauth/access_token"
  val authorize = "https://api.twitter.com/oauth/authorize"
}

trait TwitterConsumer extends SomeConsumer {
  val consumer: ConsumerKey = new ConsumerKey(
    Properties.envOrNone("TWITTER_CONSUMER_KEY")
      .getOrElse(throw new IllegalArgumentException("Missing env variable for TWITTER_CONSUMER_KEY")),
    Properties.envOrNone("TWITTER_CONSUMER_SECRET")
      .getOrElse(throw new IllegalArgumentException("Missing env variable for TWITTER_CONSUMER_SECRET"))
  )
}