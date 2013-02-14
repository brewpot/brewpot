package org.brewpot

import xml.NodeSeq

trait ServiceModule extends HtmlWrapperModule
  with DataProviderModule {

  val greet: NodeSeq
}

trait StaticService extends ServiceModule {
  val greet: NodeSeq = wrap(<h1>{greetTitle}</h1><p>{greetContent}</p>)
}
