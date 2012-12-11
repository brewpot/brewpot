package org.brewpot.web

import Snippets._
import unfiltered.response.Html5
import org.brewpot.model.Entities
import Entities.{Recipe, User}

object Views {

  def mainPage(user: Option[User], alert: Option[String]): Html5 =
    bootstrap(
      "Brewpot!",
        <h1>Will contain magic</h1>
    )(user)

  def mainPage: Html5 = mainPage(None, None)

  def recipesPage(brews: Seq[Recipe])(implicit user: Option[User]): Html5 =
    bootstrap(
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
            </thead>
            {brews.sortBy(-_.id.toInt).map(x =>
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
  )

  def profilePage(user: User) = {
    bootstrap(
      "Profile",
      <div class="container-fluid">
        <div class="row-fluid">
          <div class="span1">
            {user.avatar.map(img => <img src={img}/>).getOrElse(Nil)}
          </div>
          <div class="span11">
            <h2>
              {user.name.getOrElse(Nil)}
            </h2>
          </div>
        </div>
        <br/>
        <div class="row">
          <div class="pull-left">
            <form class="form-horizontal">
              <div class="control-group">
                <label class="control-label" for="inputEmail">Email</label>
                <div class="controls">
                  <input type="text" id="inputEmail" placeholder="Email"/>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    )(Some(user))
  }

  def newRecipePage(implicit user: Option[User]) = bootstrap(
    "Add recipe",
    recipeForm
  )


}
