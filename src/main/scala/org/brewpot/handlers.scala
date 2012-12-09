package org.brewpot

import auth.TokenUser
import org.brewpot.web.views
import javax.servlet.http.HttpServletRequest
import unfiltered.response._
import unfiltered.request._
import util.control.Exception._
import scala.Some
import unfiltered.response.ResponseString
import net.liftweb.json.parseOpt
import jsonpicklers.{Failure, Success}
import java.util.UUID
import org.brewpot.entities._

object handlers {

  object RecipeHandler {

    /**
     * Temporary stuff
     */
    lazy val recipes = collection.mutable.Buffer(
      Recipe("0", "kareblak", RecipeData("Underyul", Option("Lager"), Option(1.080), Option(7.62), Option(60), Option(40))),
      Recipe("11", "obama", RecipeData("Mjød", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40))))

    def handleRecipes(req: HttpRequest[_]) = req match {
      case TokenUser(user) => views.recipesPage(user)(recipes)
      case _ => views.recipesPage(None)(recipes)
    }

    def handleAddRecipeForm(req: HttpRequest[HttpServletRequest]): ResponseFunction[Any] = req match {
      case TokenUser(user) => views.newRecipePage(user)
    }

    def handleSaveRecipe(req: HttpRequest[_]): ResponseFunction[Any] = {
      def toRecipesPage(u: User) = {
        parseOpt(Body.string(req)).map(j => RecipeData.json.unpickle(j) match {
          case Success(recipe, _) => views.recipesPage(Some(u))(Recipe(UUID.randomUUID().toString, u.username, recipe) +=: recipes)
          case f: Failure => BadRequest ~> ResponseString(f.toString())
        }).getOrElse(BadRequest)
      }
      TokenUser.unapply(req).get match {
        case Some(u) => toRecipesPage(u)
        case None => Forbidden
      }
    }
  }

  object MainPageHandler {
    def handleMain(req: HttpRequest[_]) = req match {
      case TokenUser(user) => {
        views.mainPage(user, None)
      }
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