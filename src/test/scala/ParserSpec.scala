import org.brewpot.entities.TwitterUser
import org.specs2.mutable.Specification
import net.liftweb.json.parse
import jsonpicklers._

class ParserSpec extends Specification {

  val json =
    """
    |{
    |  "name": "Matt Harris",
    |  "profile_image_url": "http://a1.twimg.com/profile_images/554181350/matt_normal.jpg",
    |  "screen_name": "themattharris"
    |}
    """.stripMargin

  "A Json parser" should {
    "parse a sound json structure to a scala user object" in {
      val parsed = parse(json)
      val Success(p, _) = TwitterUser.json.unpickle(parsed)
      val pickled = TwitterUser.json.pickle(p)
      parsed == pickled
    }
  }

}
