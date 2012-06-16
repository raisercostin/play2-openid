package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

    def index = Action {
        SignIn.log("Here's my log message - costin");
        Ok(views.html.index("Your new application is ready."))
    }

}