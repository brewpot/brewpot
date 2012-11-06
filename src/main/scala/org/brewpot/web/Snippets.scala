package org.brewpot.web

import xml.NodeSeq
import unfiltered.response.Html5

object Snippets {

  def bootstrap(title: String, body: NodeSeq) = {
    Html5(
      <html>
        <head>
          <title>
            {title}
          </title>
          <link href="/css/bootstrap.min.css" rel="stylesheet"/>
        </head>
        <body>
          <div class="navbar">
            <div class="navbar-inner">
              <a class="brand" href="/">
                Brewpot!
              </a>
            </div>
          </div>
          <div class="container">
            {body}
          </div>
          <script src="http://code.jquery.com/jquery-latest.js"></script>
          <script src="/js/bootstrap.min.js"></script>
        </body>
      </html>
    )
  }

}
