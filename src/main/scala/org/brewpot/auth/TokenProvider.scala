package org.brewpot.auth

import org.brewpot.model.Entities
import Entities.User

object TokenProvider {

  val tokenStore = new collection.mutable.HashMap[String, User]()

}

object UserProvider {

  val userStore = new collection.mutable.HashMap[(String, Class[_ <: Provider]), User]()

}
