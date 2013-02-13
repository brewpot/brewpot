import java.io.File
import org.brewpot.Routes
import util.Properties

object Server extends App with Routes { this: Routes =>

  val port = Properties.envOrElse("PORT", "8888") toInt

  unfiltered.jetty.Http(port).context("/") { c =>
    c.filter(this)
    c.resources(new File("src/main/resources").toURI.toURL)
  } run (s => println("server started"))

}