package org.brewpot.web

import xml.NodeSeq
import unfiltered.response.Html5
import org.brewpot.entities.User

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
      <a href="/recipesinput" class="btn pull-right" role="button">
        <i class="icon-plus"/> <span class="hidden-phone">Add recipe</span>
      </a>
    }
      <form class="navbar-search pull-left">
        <input type="text" class="search-query input-xlarge" placeholder="Filter"/>
      </form>
    </div>

  val recipeForm =
    <container class="container-fluid">
      <form class="form-horizontal">
        <div class="control-group">
          <label class="control-label" for="inputName">Name</label>
          <div class="controls">
            <input class="span4" type="text" id="inputName" placeholder="Bitches brew"/>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="selectStyle">Style</label>
          <div class="controls controls-row">
            <select class="span4" id="selectStyle">
              <option>6A English IPA</option>
              <option>6B American IPA</option>
              <option>6C Imperial IPA</option>
            </select>
            <select class="span2" id="selectStyleGuide">
              <option>Norbrygg 2012</option>
            </select>
          </div>
        </div>
        <div class="control-group">
          <div class="controls">
            <button type="submit" class="btn"><i class="icon-plus"/> Add recipe</button>
          </div>
        </div>
      </form>
    </container>

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
            {header(title)}
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
