import org.specs2.mutable._
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.specs2.runner.JUnitRunner
import ro.raisercostin.play.module.signin.User2
import ro.raisercostin.play.module.signin.Database
import org.squeryl.PrimitiveTypeMode.transaction


@RunWith(classOf[JUnitRunner])
class HelloWorldSpec extends Specification {
    "create users table" should {
        "create/drop table" in {
            running(fakeApplication(), new Runnable() {
    public void run() {
        	ro.raisercostin.play.module.signin.Global
            transaction{
                Database.create
                Database.printDdl
                true
            }
    }}
        }
    }
    "The 'Hello world' string" should {
        "contain 11 characters" in {
            "Hello world" must have size (11)
        }
        "start with 'Hello'" in {
            "Hello world" must startWith("Hello")
        }
        "end with 'world'" in {
            "Hello world" must endWith("world")
        }
    }
}