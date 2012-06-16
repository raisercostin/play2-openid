package controllers

import play.api._
import play.api.mvc._

object SignIn extends Controller {
    def showLogin = Action {
        Ok(views.html.login("test"))
    }
}