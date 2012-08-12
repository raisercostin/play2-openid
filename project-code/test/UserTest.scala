import ro.raisercostin.play.module.signin.User2
import ro.raisercostin.play.module.signin.Database
import org.junit.runner.RunWith
import org.squeryl.PrimitiveTypeMode.transaction
import org.fest.assertions.Assertions._
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import org.specs2.matcher.Matchers._
import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class HelloWorldSpec extends Specification {
    //FakeApplication(additionalConfiguration = inMemoryDatabase())
    "create users table" should {
        "create/drop table" in {
            running(FakeApplication()) {
                ro.raisercostin.play.module.signin.Global
                transaction {
                    Database.drop
                    Database.create
                    Database.printDdl
                    true
                }
            }
        }
    }

    "A User" should {
        "be creatable" in {
            running(FakeApplication(/*additionalConfiguration = inMemoryDatabase()*/)) {
                transaction {
                    val user1 = Database.usersTable insert User2("name")
                    user1.id should not be equalTo(0)
                }
            }
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