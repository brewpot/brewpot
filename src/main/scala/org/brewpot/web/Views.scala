package org.brewpot.web

import snippets._
import unfiltered.response.Html5
import org.brewpot.entities.{Recipe, User}

object views {

  def mainPage(user: Option[User], alert: Option[String]): Html5 = {
    bootstrap(
      "Brewpot!",
        (alert match {
          case Some(s) =>
            <div class="alert alert-error">
              <button type="button" class="close" data-dismiss="alert">×</button>
              <strong>Authentication error!</strong>
              I'm sorry, we couldn't log you in. Why not try again?
            </div>
          case None => Nil
        }) ++
        <p>
          Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore
          magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
          consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
          Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
        </p>
    )(user)
  }

  def mainPage: Html5 = mainPage(None, None)

  def recipesPage(user: Option[User])(brews: Seq[Recipe]): Html5 = bootstrap(
    "Recipes",
      <p>
        {recipebar(user.isDefined)}
      </p>
        <table class="table table-striped table-bordered table-hover">
          <thead>
            <th class="hidden-phone">Id</th>
            <th>Name</th>
            <th>Style</th>
            <th>OG</th>
            <th class="hidden-phone">ABV</th>
            <th class="hidden-phone">EBC</th>
            <th class="hidden-phone">IBU</th>
            <th>Brewer</th>
          </thead>{brews.sortBy(-_.id.toInt).map(x =>
          <tr>
            <td class="hidden-phone">{x.id}</td>
            <td>{if (x.data.name.size >= 16) x.data.name.substring(0, 12) + "..." else x.data.name}</td>
            <td>{x.data.style.getOrElse("-")}</td>
            <td>{x.data.OG.getOrElse(0)}</td>
            <td class="hidden-phone">{x.data.ABV.getOrElse(0)} %</td>
            <td class="hidden-phone">{x.data.EBC.getOrElse(0)}</td>
            <td class="hidden-phone">{x.data.IBU.getOrElse(0)}</td>
            <td>{x.user}</td>
          </tr>
        )}
        </table>
  )(user)

  def newRecipePage(user: Option[User]) = bootstrap(
    "Add recipe",
    recipeForm
  )(user)


}
