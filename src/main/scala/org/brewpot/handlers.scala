package org.brewpot

import model.entities._
import org.brewpot.web.views
import unfiltered.request.QParams._
import javax.servlet.http.HttpServletRequest
import unfiltered.response._
import unfiltered.request._
import org.brewpot.extractors.TokenUser
import util.control.Exception._
import scala.Some
import unfiltered.response.ResponseString
import net.liftweb.json.parseOpt
import jsonpicklers.{Failure, Success}

object handlers {

  object RecipeHandler {

    /**
     * Temporary stuff
     */
    lazy val recipes = collection.mutable.Buffer(
      Recipe(Some("0"), "Underyul", Option("Lager"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
      Recipe(Some("1"), "Overyul", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
      Recipe(Some("2"), "ObamaRama IPA", Option("IPA"), Option(1.280), Option(14.0), Option(60), Option(40), "obama"),
      Recipe(Some("3"), "Mjød", Option("Mead"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
      Recipe(Some("4"), "Vestkyst", Option("IPA"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
      Recipe(Some("5"), "Overyul", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
      Recipe(Some("6"), "Obama Honey Ale", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "obama"),
      Recipe(Some("7"), "Mjød", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
      Recipe(Some("8"), "Underyul", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
      Recipe(Some("9"), "Overyul", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"),
      Recipe(Some("10"), "Obama Honey Ale", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "obama"),
      Recipe(Some("11"), "Mjød", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40), "kareblak"))

    def handleRecipes(req: HttpRequest[_]) = req match {
      case TokenUser(user) => views.recipesPage(user)(recipes)
      case _ => views.recipesPage(None)(recipes)
    }

    def handleAddRecipeForm(req: HttpRequest[HttpServletRequest]): ResponseFunction[Any] = {
      Ok
    }

    def handleSaveRecipe(req: HttpRequest[_]): ResponseFunction[Any] = {
      val TokenUser(user) = req
      user match {
        case Some(user) => {
          parseOpt(Body.string(req)).map(j => PickledRecipe.json.unpickle(j) match {
            case Success(recipe, _) => views.recipesPage(Some(user))(Recipe(recipe, user) +=: recipes)
            case Failure(_, _) => BadRequest
          }).getOrElse(BadRequest)
        }
        case None => Unauthorized
      }

    }
  }


  object MainPageHandler {
    def handleMain(req: HttpRequest[_]) = req match {
      case TokenUser(user) => views.mainPage(user, None)
      case _ => views.mainPage
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