package org.brewpot.web

import Snippets._
import org.brewpot.models._
import xml.NodeSeq
import unfiltered.response.Html5

object Views {

  def main(user: Option[User], alert: Option[String]): Html5 = {
    bootstrap(
      "Brewpot!",
      header("Brewpot!") ++
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

  def main: Html5 = main(None, None)

  def recipes(user: Option[User])(brews: Seq[Recipe]): Html5 = bootstrap(
    "Recipes",
    header("Recipes") ++
      addRecipeModal ++
      <p>
        {recipebar}
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
          </thead>{brews.sortBy(-_.id.getOrElse("0").toInt).map(x =>
          <tr onclick="input" data-toggle="modal" href="#addRecipeModal">
            <td class="hidden-phone">
              {x.id.getOrElse("-")}
            </td>
            <td>
              {if (x.name.size >= 16) x.name.substring(0, 13) + "..." else x.name}
            </td>
            <td>
              {x.style.getOrElse("-")}
            </td>
            <td>
              {x.OG.getOrElse(0)}
            </td>
            <td class="hidden-phone">
              {x.ABV.getOrElse(0)}
              %</td>
            <td class="hidden-phone">
              {x.EBC.getOrElse(0)}
            </td>
            <td class="hidden-phone">
              {x.IBU.getOrElse(0)}
            </td>
            <td>
              {x.user}
            </td>
          </tr>
        )}
        </table>
  )(user)

}
