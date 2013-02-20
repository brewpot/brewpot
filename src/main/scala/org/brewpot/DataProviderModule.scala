package org.brewpot

trait DataProviderModule {
  def get[A](a: A): A
  def insert[A](a: A): A
  def update[A](a: A): A
  def delete[A](a: A)
}

trait MongoDbDataProvider extends DataProviderModule with ConfigurationModule {


  def get[A](a: A): A = ???
  def insert[A](a: A): A = ???
  def update[A](a: A): A = ???
  def delete[A](a: A) {}
}