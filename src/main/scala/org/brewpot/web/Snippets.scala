package org.brewpot.web

import xml.NodeSeq
import unfiltered.response.Html5
import org.brewpot.models.{User, TwitterUser}

object snippets {

  def header(text: String) =
    <div class="page-header">
      <h1>
        {text}
      </h1>
    </div>

  def nav(user: Option[User]) = {
    <ul class="nav">
      <li>
        <a href="/recipes"><i class="icon-th-list"/> Recipes</a>
      </li>
    </ul>
    <ul class="nav pull-right">
    {
      user match {
        case Some(u) => {
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src={ u.avatar.getOrElse("") }/>  { u.name.getOrElse(u.username) } <b class="caret"></b>
            </a>
            <ul class="dropdown-menu" data-no-collapse="true">
              <li>
                <a href="/auth/twitter/logout">
                  <img src="/img/twitter-bird-16x16.png"/> Logout
                </a>
              </li>
            </ul>
          </li>
        }
        case None =>
          <li>
            <a href="/auth/twitter/login">
              <img src="/img/twitter-bird-16x16.png"/> Login
            </a>
          </li>
       }
    }
    </ul>
  }

  def recipebar(auth: Boolean) =
    <div class="container navbar">
    { if (auth)
      <a href="#addRecipeModal" class="btn pull-right" role="button" data-toggle="modal">
        <i class="icon-plus"/> Add recipe
      </a>
    }
      <form class="navbar-search pull-left">
        <input type="text" class="search-query" placeholder="Filter"/>
      </form>
    </div>

  def addRecipeModal =
    <div id="addRecipeModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="addRecipeModalLabel" aria-hidden="true">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="addRecipeModal">New recipe</h3>
      </div>
      <div class="modal-body">
        <form class="form-horizontal">
          <div class="control-group">
            <label class="control-label" for="name">Name</label>
            <div class="controls">
              <input type="text" id="inputName" placeholder="E.g. Bitches Brew"/>
            </div>
          </div>
          <div class="control-group">
            <label class="control-label" for="style">Beer style</label>
            <div class="controls">
              <select>
                <option>1A American Lager</option>
                <option>2A German Bock</option>
                <option>3D English Pale Ale</option>
                <option>4C Asian Lager</option>
                <option>5E Dry Stout</option>
              </select>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer control-group">
        <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
        <button class="btn btn-primary" data-loading-text="Saving...">Save</button>
      </div>
    </div>

  def bootstrap(title: String, body: NodeSeq)(user: Option[User]) =
    Html5(
      <html>
        <head>
          <title>
            {title}
          </title>
          <meta name="viewport" content="width=device-width; initial-scale=1.0;" />
          <link href="/css/bootstrap.min.css" rel="stylesheet"/>
          <link href="/css/bootstrap-responsive.min.css" rel="stylesheet"/>
          <link href="/css/brewpot.css" rel="stylesheet"/>
        </head>
        <body>
          <div class="navbar" data-toggle="collapse" data-target=".nav-collapse">
            <div class="navbar-inner">
              <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </a>
              <a class="brand" href="/">
                Brewpot!
              </a>
              <div class="nav-collapse collapse">{nav(user)}</div>
            </div>
          </div>
          <div class="container">
            <div class="alert">
              This page is currently being implemented. Any added data will not be stored.
              If you want to hack the shit out of it, please do, and tell me what I got wrong
              by adding an issue on <a href="http://github.com/brewpot/brewpot">github</a>. Thanks!
            </div>
            {body}
          </div>
          <footer class="footer">
            <div class="container">
              <p class="pull-right">
                <a href="#">To the top</a>
              </p>
            </div>
          </footer>
          <script src="http://code.jquery.com/jquery-latest.js"></script>
          <script src="/js/bootstrap.min.js"></script>
        </body>
      </html>
    )

}
