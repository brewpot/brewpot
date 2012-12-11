package org.brewpot.auth

import org.brewpot.entities.User

object TokenProvider {

  val tokenStore = new collection.mutable.HashMap[String, User]()

}

object UserProvider {

  val userStore = new collection.mutable.HashMap[(String, Class[_ <: AuthProvider]), User]()

}
