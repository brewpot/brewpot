package org.brewpot.service

import org.brewpot.web.{QueryParams, Views}
import org.brewpot.models.Recipe
import unfiltered.request.{Params, HttpRequest}
import unfiltered.request.QParams._
import javax.servlet.http.HttpServletRequest
import unfiltered.response.BadRequest
import com.ning.http.client.consumers.AppendableBodyConsumer

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
   *
   * Fix this shit
   */
  def addRecipe(r: HttpRequest[HttpServletRequest]) = {
    r match {
      case Params(ps) => {
        val m = QueryParams(ps)
        val expected = for {
          id <- lookup("id").is(trimmed).is(required("id is required"))
          name <- lookup("name").is(trimmed).is(required("name is required"))
          style <- lookup("style").is(trimmed)
          OG <- lookup("OG").is(trimmed).is(watch(Params.double, e => "'OG' %s should be a comma separated double".format(e)))
          ABV <- lookup("ABV").is(trimmed).is(double(e => "'OG' %s should be a comma separated double".format(e)))
        } yield Recipe("","",Some(""),Some(1.1),Some(1.1),Some(1),Some(1),"")

        Views.recipes(Recipe(
          m.first("id").get, m.first("name").get, m.first("style"), m.firstDouble("OG"), m.firstDouble("ABV"), m.firstInt("EBC"), m.firstInt("IBU"), m.first("user").get
        ) +: fetchRecipes)
      }
      case _ => BadRequest
    }
  }

  def double[E](e: String => E) = watch(Params.double, e)

}
