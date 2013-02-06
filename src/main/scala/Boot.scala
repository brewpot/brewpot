import akka.actor.Props
import org.brewpot.http.{MainServiceActor, MainService}
import spray.can.server.SprayCanHttpServerApp

object Boot extends App with SprayCanHttpServerApp {

  val handler = system.actorOf(Props[MainServiceActor], "main-service")

  newHttpServer(handler) ! Bind(interface = "localhost", port = 8080)

}
