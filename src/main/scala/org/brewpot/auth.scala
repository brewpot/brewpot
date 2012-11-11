package auth

import dispatch._
import oauth.SomeConsumer
import com.ning.http.client.oauth.ConsumerKey

class Auth(val callback: String) extends oauth.Exchange
with oauth.SomeCallback
with oauth.SomeHttp
with BrewpotConsumer
with TwitterEndpoints {
  val http = dispatch.Http
}

trait TwitterEndpoints extends oauth.SomeEndpoints {
  val requestToken = "https://api.twitter.com/oauth/request_token"
  val accessToken  = "https://api.twitter.com/oauth/access_token"
  val authorize    = "https://api.twitter.com/oauth/authorize"
}

trait BrewpotConsumer extends SomeConsumer {
  val consumer: ConsumerKey = new ConsumerKey("","")
}