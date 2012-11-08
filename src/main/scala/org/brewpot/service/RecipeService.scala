package org.brewpot.service

import org.brewpot.web.Views
import org.brewpot.models.Recipe
import unfiltered.request.{Params, HttpRequest}
import javax.servlet.http.HttpServletRequest
import unfiltered.response.BadRequest

object RecipeService {

  /**
   * Temporary stuff
   */
  def fetchRecipes = Seq(
    Recipe("0", "Underyul", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
    Recipe("1", "Overyul", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
    Recipe("2", "Obama Honey Ale", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "obama"),
    Recipe("3", "Mjød", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
    Recipe("4", "Underyul", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
    Recipe("5", "Overyul", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
    Recipe("6", "Obama Honey Ale", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "obama"),
    Recipe("7", "Mjød", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
    Recipe("8", "Underyul", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
    Recipe("9", "Overyul", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
    Recipe("10", "Obama Honey Ale", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "obama"),
    Recipe("11", "Mjød", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"))

  def recipes = {
    Views.recipes(fetchRecipes)
  }

  /**
   * Temporary stuff
   */
  def addRecipe(r: HttpRequest[HttpServletRequest]) = {
    r match {
      case Params(ps) => {
        val m = ps.map(p => (p._1, p._2.head))
        Views.recipes(Recipe(
          m("id"), m("name"), m("style"), m("OG"), m("ABV"), m("EBC"), m("IBU"), m("user")
        ) +: fetchRecipes)
      }
      case _ => BadRequest
    }
  }

  implicit val convDouble = convOption[Double](_.toDouble)_
  implicit val convInt = convOption[Int](_.toInt)_
  implicit val convString = convOption[String](_.toString)_
  def convOption[T](f: String => T)(s: String) = try { Some(f(s)) } catch { case _ => None }
}

