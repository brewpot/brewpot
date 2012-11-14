package org.brewpot

import org.brewpot.web.views
import org.brewpot.models.{User, Recipe}
import unfiltered.request.{Params, HttpRequest}
import unfiltered.request.QParams._
import javax.servlet.http.HttpServletRequest
import unfiltered.response._
import org.brewpot.extractors.UserWithToken
import util.control.Exception._
import scala.Some
import unfiltered.response.ResponseString

object handlers {

  object RecipeHandler {

    /**
     * Temporary stuff
     */
    def fetchRecipes = Seq(
      Recipe(Some("0"), "Underyul", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
      Recipe(Some("1"), "Overyul", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
      Recipe(Some("2"), "ObamaHoneyAleXXXXxxxxXXXXxx", Option("Dobbelbock"), Option(1.280), Option(14.0), Option(60), Option(40), "obama"),
      Recipe(Some("3"), "Mjød", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
      Recipe(Some("4"), "Underyul", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
      Recipe(Some("5"), "Overyul", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
      Recipe(Some("6"), "Obama Honey Ale", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "obama"),
      Recipe(Some("7"), "Mjød", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
      Recipe(Some("8"), "Underyul", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
      Recipe(Some("9"), "Overyul", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
      Recipe(Some("10"), "Obama Honey Ale", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "obama"),
      Recipe(Some("11"), "Mjød", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"))

    def recipes(req: HttpRequest[_]) = req match {
      case UserWithToken(user) => views.recipes(user)(fetchRecipes)
      case _ => views.recipes(None)(fetchRecipes)
    }

    /**
     * Temporary stuff
     *
     * Fix this shit
     */
    def addRecipe(req: HttpRequest[HttpServletRequest]): ResponseFunction[Any] = {
      def double[E](e: String => E) = watch(Params.double, e)

      def expected(user: User) = for {

        id <- lookup("id")        is trimmed is required("id is required")
        name <- lookup("name")    is trimmed is required("name is required")
        style <- lookup("style")  is trimmed
        OG <- lookup("OG")        is trimmed is double(e => "'OG' %s should be a double".format(e))
        ABV <- lookup("ABV")      is trimmed is double(e => "'ABV' %s should be a double".format(e))
        EBC <- lookup("EBC")      is trimmed is int(e => "'EBC' %s should be an int".format(e))
        IBU <- lookup("IBU")      is trimmed is int(e => "'IBU' %s should be an int".format(e))

      } yield Recipe(None, name.get, style, OG, ABV, EBC, IBU, user.username)

      req match {
        case UserWithToken(Some(u)) => req match {
          case Params(ps) => {
            expected(u)(ps).fold(
              err => BadRequest ~> ResponseString(err.toString),
              rec => views.recipes(Some(u))(rec +: fetchRecipes)
            )
          }
          case _ => BadRequest
        }
        case _ => Unauthorized
      }
    }
  }

  object MainPageHandler {

    def main(req: HttpRequest[_]) = req match {
      case UserWithToken(user) => views.main(user, None)
      case _ => views.main
    }

  }

  case class QueryParams(qp: Map[String, Seq[String]]) {
    def get(key: String) = qp.get(key)
    def getOrElse(key: String, orElse: Seq[String]) = qp.getOrElse(key, orElse)
    def foldLeft[B](acc: B)(f: (B, (String, Seq[String])) => B) = qp.foldLeft(acc)(f)
    def apply(key: String) = qp.apply(key)
    def mapFirst[B](key: String, f: (String) => Option[B]) = qp(key).headOption.flatMap(f)
    def first(s: String) = qp(s).headOption
    def firstInt(key: String) = mapFirst(key, d => allCatch.opt(d.toInt))
    def firstLong(key: String) = mapFirst(key, d => allCatch.opt(d.toLong))
    def firstDouble(key: String) = mapFirst(key, d => allCatch.opt(d.toDouble))
    def asServletRequestParameters: Map[String, Array[String]] = qp.mapValues(_.toArray[String])
  }

}