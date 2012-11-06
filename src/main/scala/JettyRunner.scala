import java.io.File
import org.brewpot.web.Resources
import util.Properties

object JettyRunner extends App {

  def port = Properties.envOrElse("PORT", "8888").toInt

  def start = {
    unfiltered.jetty.Http(port)
      .resources(new File("src/main/resources").toURI.toURL)
      .filter(Resources)
      .start
  }

  start

}
