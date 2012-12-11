package org.brewpot.auth

import org.scribe.oauth.OAuthService
import org.scribe.model.Token
import org.brewpot.model.Entities
import Entities.User

abstract class Provider {
  protected def key: String
  protected def secret: String
  def service(callback: Option[String] = None): OAuthService
  def token(token: String): Token = new Token(token, secret)
  def verify(token: Token): Option[User]
  def callback(base: String): String
}

