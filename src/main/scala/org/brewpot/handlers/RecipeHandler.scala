package org.brewpot.handlers

import org.brewpot.model.Entities
import Entities.{User, RecipeData, Recipe}
import unfiltered.request.{Body, HttpRequest}
import org.brewpot.auth.TokenUser
import org.brewpot.web.Views
import javax.servlet.http.HttpServletRequest
import unfiltered.response.{Forbidden, ResponseString, BadRequest, ResponseFunction}
import net.liftweb.json._
import Entities.User
import Entities.Recipe
import scala.Some
import jsonpicklers.{Failure, Success}
import java.util.UUID

object RecipeHandler {

  lazy val recipes = collection.mutable.Buffer(
    Recipe("0", "kareblak", RecipeData("Underyul", Option("Lager"), Option(1.080), Option(7.62), Option(60), Option(40))),
    Recipe("11", "obama", RecipeData("Mjød", Option("Dobbelbock"), Option(1.080), Option(7.62), Option(60), Option(40))))

  def handleRecipes(req: HttpRequest[_]) =
    Views.recipesPage(recipes) {
      req match {
        case TokenUser(u) if u.isDefined => u
        case _ => None
      }
    }

  def handleAddRecipeForm(req: HttpRequest[HttpServletRequest]): ResponseFunction[Any] = req match {
    case TokenUser(user) => Views.newRecipePage(user)
  }

  def handleSaveRecipe(req: HttpRequest[_]): ResponseFunction[Any] = {
    def toRecipesPage(u: User) = {
      parseOpt(Body.string(req)).map(j => RecipeData.json.unpickle(j) match {
        case Success(recipe, _) => Views.recipesPage(Recipe(UUID.randomUUID().toString, u.username, recipe) +=: recipes)(Some(u))
        case f: Failure => BadRequest ~> ResponseString(f.toString())
      }).getOrElse(BadRequest)
    }
    TokenUser.unapply(req).get match {
      case Some(u) => toRecipesPage(u)
      case None => Forbidden
    }
  }
}
