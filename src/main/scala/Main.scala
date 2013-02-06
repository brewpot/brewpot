import akka.actor.Props
import org.brewpot.http.Routes
import spray.can.server.SprayCanHttpServerApp

object Main extends App with SprayCanHttpServerApp {

  val handler = system.actorOf(Props[Routes])

  newHttpServer(handler) ! Bind(interface = "localhost", port = 8080)

}
