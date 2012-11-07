package org.brewpot.web

import xml.NodeSeq
import unfiltered.response.Html5

object Snippets {

  def header(text: String) =
    <div class="page-header">
      <h1>{text}</h1>
    </div>

  val nav =
    <ul class="nav">
      <li><a href="/recipes">Recipes</a></li>
    </ul>

  def bootstrap(title: String, body: NodeSeq) =
    Html5(
      <html>
        <head>
          <title>
            {title}
          </title>
          <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
          <link href="/css/bootstrap-responsive.css" rel="stylesheet"/>
          <link href="/css/bootstrap.min.css" rel="stylesheet"/>
          <link href="/css/brewpot.css" rel="stylesheet" />
        </head>
        <body>
          <div class="navbar">
            <div class="navbar-inner">
              <a class="brand" href="/">
                Brewpot!
              </a>
              {nav}
            </div>
          </div>
          <div class="container">
            {body}
          </div>
          <footer class="footer">
            <div class="container">
              <p class="pull-right"><a href="#">To the top</a></p>
            </div>
          </footer>
          <script src="http://code.jquery.com/jquery-latest.js"></script>
          <script src="/js/bootstrap.min.js"></script>
        </body>
      </html>
    )

}
