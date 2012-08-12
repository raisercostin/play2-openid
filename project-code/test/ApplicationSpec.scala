package test

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {
  
  
  "Application" should {
    
    
    "go to login page without credentials" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val result  = routeAndCall( FakeRequest( GET, "/")).get
        status(result) must equalTo(303)
      }      
    }
    "list the secured product page with credentials" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val result  = routeAndCall( FakeRequest( GET, "/").withSession("email"->"guillaume@sample.com")).get
        status(result) must equalTo(200)
      }      
    }
    
  }
}
