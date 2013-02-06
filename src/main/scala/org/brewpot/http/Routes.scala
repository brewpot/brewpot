package org.brewpot.http

import spray.util._
import akka.actor._
import spray.http.{HttpResponse, HttpRequest}
import spray.http.HttpMethods._

class Routes extends Actor with SprayActorLogging {

  def receive = {
    case HttpRequest(GET, "/", _, _, _) =>
      sender ! HttpResponse(404)

  }

}
