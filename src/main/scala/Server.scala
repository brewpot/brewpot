import java.io.File
import org.brewpot.Application
import util.Properties

object Server extends App {

  val port = Properties.envOrElse("PORT", "8888") toInt

  unfiltered.jetty.Http(port)
    .filter(new Application)
    .resources(new File("src/main/resources").toURI.toURL)
    .start
}