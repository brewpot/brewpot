package org.brewpot

trait DataProviderModule {
  def greetTitle: String
  def greetContent: String
}

trait StaticDataProvider extends DataProviderModule {
  def greetTitle: String =
    "Welcome to Brewpot!"

  def greetContent: String =
    "This will be the home of Brewpot. An application for social brewing. " +
    "Create, clone or get inspired by other recipes, take control over your inventory " +
    "or just hang around to see what people are brewing. Check back soon for incremental updates!"
}
