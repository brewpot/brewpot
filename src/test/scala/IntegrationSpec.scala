import com.ning.http.client.Response
import dispatch.{url, Http}
import org.specs2._
import JettyRunner._
import org.specs2.specification.Example
import unfiltered.response._

class IntegrationSpec extends HttpHarness with Specification {

  before

  def is =

  "This is a specification to check if the front end " +
    "resources are available and returning viewable data"   ^
                                                            p^
    "The server should listen to the following resources"   ^
      "/"                                                   ! _Ok("") ^
      "/recipes"                                            ! _Ok("recipes") ^
      "/login"                                              ! _Ok("login") ^
      "/about"                                              ! _Ok("about") ^
                                                            p^
    "The server should fail on not defined resources"       ^
      "/foo"                                                ! _NotFound("foo") ^
      "/login/some"                                         ! _NotFound("login/some")^
                                                            end


  val _Ok = _Status(Ok)(_)
  val _NotFound = _Status(NotFound)(_)

  def _Status(status: Status)(plan: String) = eval(plan)(_.getStatusCode == status.code)

  def eval[A](plan: String)(f: (Response) => A) = {
    f(Http(url("http://localhost:%s/%s".format(port, plan)))())
  }


}
