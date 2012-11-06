import com.ning.http.client.Response
import dispatch.{url, Http}
import org.specs2.mutable._
import JettyRunner._
import org.specs2.specification.Example
import unfiltered.response._

class IntegrationSpec extends HttpHarness with Specification {

  before

  "The server" should {
    "listen to the following resources" in {
      "/main"         in eval("main")(_.getStatusCode must_== Ok.code)
      "/recipe/{id}"  in eval("recipe/0")(_.getStatusCode must_== Ok.code)
      "/login"        in eval("login")(_.getStatusCode must_== Ok.code)
    }
  }

  def eval[A](plan: String)(f: (Response) => A) = {
    f(Http(url("http://localhost:%s/%s".format(port, plan)))())
  }


}
