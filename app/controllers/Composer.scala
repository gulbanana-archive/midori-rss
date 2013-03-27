package controllers

import dal._

//technically this is more of a service locator than inversion of control
//scope of the application doesn't justify real IOC, only DI, and we'd lose static route-checking
object Composer {
  val resolveDAO : AsyncDAO = new MongoAsyncDAO()
}