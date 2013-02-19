package org.brewpot


trait ServiceModule extends HtmlWrapperModule
  with DataProviderModule {

  def serve[A, B](a: A): Option[B]
}

trait StaticService extends ServiceModule {
  val serve = Some(wrap(<h1>{greetTitle}</h1><p>{greetContent}</p>))
}

trait CalcService extends ServiceModule {
  def serve(a: AbvCalc) = Some((a.og - a.fg) * 131)
}
