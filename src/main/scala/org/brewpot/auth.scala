package auth

import dispatch._
import oauth.SomeConsumer
import com.ning.http.client.oauth.ConsumerKey

class TwitterAuth(val callback: String) extends oauth.Exchange
with oauth.SomeCallback
with oauth.SomeHttp
with TwitterConsumer
with TwitterEndpoints {
  val http = dispatch.Http
}

trait TwitterEndpoints extends oauth.SomeEndpoints {
  val requestToken = "https://api.twitter.com/oauth/request_token"
  val accessToken  = "https://api.twitter.com/oauth/access_token"
  val authorize    = "https://api.twitter.com/oauth/authorize"
}

trait TwitterConsumer extends SomeConsumer {
  val consumer: ConsumerKey = new ConsumerKey("ZEKYzsa1kynwC7nGsJJ1ZA","yT1ACvrsvnSEu5dxcUZMPbbLJ2Ms6zph5uAfSwFZCs")
}
//
//class GoogleAuth(val callback: String) extends oauth.Exchange
//with oauth.SomeCallback
//with oauth.SomeHttp
//with GoogleConsumer
//with GoogleEndpoints {
//  val http = dispatch.Http
//}
//
//trait GoogleEndpoints extends oauth.SomeEndpoints {
//  val consumer: ConsumerKey = new ConsumerKey()
//}