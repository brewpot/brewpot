package org.brewpot.http

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import spray.http.StatusCodes._

class MainServiceSpec extends Specification with Specs2RouteTest with MainService {

  def actorRefFactory = system // connect the DSL to the test ActorSystem

  "The main service" should {

    "return a greeting for GET requests to the root path" in {
      Get() ~> route ~> check {
        entityAs[String] must contain("Welcome")
      }
    }

    "return a MethodNotAllowed error for PUT requests to the root path" in {
      Put() ~> route ~> check {
        status === MethodNotAllowed
      }
    }

  }
}
