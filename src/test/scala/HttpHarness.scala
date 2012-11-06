import dispatch.Http
import org.specs2.specification.Before

trait HttpHarness extends Before {

  def before = JettyRunner start

}
