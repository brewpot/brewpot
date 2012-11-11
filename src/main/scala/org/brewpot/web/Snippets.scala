package org.brewpot.web

import xml.NodeSeq
import unfiltered.response.Html5

object Snippets {

  def header(text: String) =
    <div class="page-header">
      <h1>
        {text}
      </h1>
    </div>

  def nav = {
    val loggedIn = false
    <ul class="nav">
      <li>
        <a href="/recipes">Recipes</a>
      </li>
    </ul>
    <ul class="nav pull-right">
      <li class="dropdown">
        { loggedIn match {
          case false =>
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Login <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="/login"><img src="/img/twitter-bird-16x16.png" class="img-rounded"/> Sign in with Twitter</a></li>
              </ul>
          case true =>
            <a href="logout">Logout</a>
          }
        }
      </li>
    </ul>
  }

  def recipebar =
    <div class="container navbar">
        <a href="#addRecipeModal" class="btn" role="button" data-toggle="modal">
          <i class="icon-plus"/> Add recipe
        </a>
        <form class="navbar-search pull-right">
          <input type="text" class="search-query" placeholder="Filter &lt;On any attribute&gt;"/>
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

  def bootstrap(title: String, body: NodeSeq) =
    Html5(
      <html>
        <head>
          <title>
            {title}
          </title>
          <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
          <link href="/css/bootstrap.min.css" rel="stylesheet"/>
          <link href="/css/bootstrap-responsive.min.css" rel="stylesheet"/>
          <link href="/css/brewpot.css" rel="stylesheet"/>
        </head>
        <body>
          <div class="navbar">
            <div class="navbar-inner">
              <a class="brand" href="/">
                Brewpot!
              </a>{nav}
            </div>
          </div>
          <div class="container">
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
