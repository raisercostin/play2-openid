package controllers

import play.api._
import play.api.mvc._

object SignIn {
  
  def log(message: String) = {
        System.out.println("MyLogger: " + message)
		//Ok(views.html.index("Your new application is ready."))
  }
  
}