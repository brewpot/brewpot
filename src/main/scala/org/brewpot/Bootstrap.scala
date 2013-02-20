package org.brewpot

import xml.NodeSeq

object Bootstrap {
  def wrap(content: NodeSeq) = {
    <html>
      <head>
        <title>
          Brewpot!
        </title>
        <meta name="viewport" content="width=device-width; initial-scale=1.0;"/>
        <link href="/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="/css/bootstrap-responsive.min.css" rel="stylesheet"/>
        <link href="/css/brewpot.css" rel="stylesheet"/>
      </head>
      <body>
        <div class="navbar navbar-static-top" data-toggle="collapse" data-target=".nav-collapse">
          <div class="navbar-inner">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </a>
            <a class="brand" href="/">
              Brewpot!
            </a>
            <div class="nav-collapse collapse">
              <ul class="nav">
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    Calculators <b class="caret"></b>
                  </a>
                  <ul class="dropdown-menu" data-no-collapse="true">
                    <li>
                      <a href="/calc/abv"><b>ABV</b> Alcohol by volume</a>
                    </li>
                    <li>
                      <a href="/calc/og"><b>OG</b> Original gravity</a>
                    </li>
                  </ul>
                </li>
              </ul>

            </div>
          </div>
        </div>
        <div class="container">
          {content}
        </div>
        <footer class="footer">
          <div class="container">
            <hr/>
            <p class="pull-right">
              <a href="#"><i class="icon-circle-arrow-up"></i></a>
            </p>
          </div>
        </footer>
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="/js/bootstrap.min.js"></script>
      </body>
    </html>
  }
}
