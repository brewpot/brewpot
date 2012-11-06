import org.brewpot.web.Resources
import util.Properties

object JettyRunner extends App {

  def port = Properties.envOrElse("PORT", "8888").toInt

  def start = {
    unfiltered.jetty.Http(port)
      .filter(Resources)
      .start()
  }

  start

}
