package org.brewpot

import org.brewpot.web.Views
import org.brewpot.models.Recipe
import unfiltered.request.{Params, HttpRequest}
import unfiltered.request.QParams._
import javax.servlet.http.HttpServletRequest
import unfiltered.response.{ResponseString, ResponseFunction, BadRequest}
import auth._
import org.brewpot.extractors.LoggedOnUser

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
      case LoggedOnUser(user) => Views.recipes(user)(fetchRecipes)
      case _ => Views.recipes(None)(fetchRecipes)
    }

    /**
     * Temporary stuff
     *
     * Fix this shit
     */
    def addRecipe(r: HttpRequest[HttpServletRequest]): ResponseFunction[Any] = {
      def double[E](e: String => E) = watch(Params.double, e)

      val expected = for {
        id <- lookup("id").is(trimmed).is(required("id is required"))
        name <- lookup("name").is(trimmed).is(required("name is required"))
        style <- lookup("style").is(trimmed)
        OG <- lookup("OG").is(trimmed).is(double(e => "'OG' %s should be a double".format(e)))
        ABV <- lookup("ABV").is(trimmed).is(double(e => "'OG' %s should be a double".format(e)))
        EBC <- lookup("EBC").is(trimmed).is(int(e => "'EBC' %s should be an int".format(e)))
        IBU <- lookup("IBU").is(trimmed).is(int(e => "'IBU' %s should be an int".format(e)))
      } yield Recipe(None, name.get, style, OG, ABV, EBC, IBU, "kareblak")

      r match {
        case Params(ps) => {
          expected(ps).fold(
            err => BadRequest ~> ResponseString(err.toString),
            rec => Views.recipes(None)(rec +: fetchRecipes)
          )
        }
        case _ => BadRequest
      }
    }
  }

  object MainPageHandler {

    def main(req: HttpRequest[_]) = req match {
      case LoggedOnUser(user) => Views.main(user, None)
      case _ => Views.main
    }

  }

}