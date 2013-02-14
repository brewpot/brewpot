import java.io.File
import org.brewpot.Routes
import util.Properties

object Server extends App {

  val port = Properties.envOrElse("PORT", "8888") toInt

  unfiltered.jetty.Http(port)
    .filter(Routes)
    .resources(new File("src/main/resources").toURI.toURL)
    .start
}