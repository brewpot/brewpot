package org.brewpot

import xml.NodeSeq

trait ViewModule {
  def greet: NodeSeq
}

trait StaticDataView extends ViewModule {
  def greet: NodeSeq = {
    <h1>Welcome to Brewpot!</h1> ++
    <p>
      This will be the home of Brewpot. An application for social brewing.
      Create, clone or get inspired by other recipes, take control over your inventory
      or just hang around to see what people are brewing. Check back soon for incremental updates!!
    </p>
  }
}
