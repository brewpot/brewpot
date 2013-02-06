package org.brewpot.http

import spray.util._
import akka.actor._
import spray.routing.HttpService
import spray.http.StatusCodes._

class MainServiceActor extends Actor with MainService with SprayActorLogging {

  def actorRefFactory = context

  def receive = runRoute(route)

}

trait MainService extends HttpService {

  // using spray-routing with the explicit tree structure instead of flat pattern matching
  val route = {
    get {
      path("") {
        // example of moving blocking code outside the service actor
        detachTo(singleRequestServiceActor) {
          complete("Welcome to brewpot!")
        }
      }
    } ~
    put {
      complete(MethodNotAllowed)
    }
  }

}
