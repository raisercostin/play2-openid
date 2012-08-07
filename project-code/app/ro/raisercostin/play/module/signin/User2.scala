package ro.raisercostin.play.module.signin

//import scala.collection.mutable.MutableList
//import _root_.org.squeryl.Table
//import _root_.org.squeryl.dsl._
//import _root_.org.squeryl.dsl.ast._
//import _root_.org.squeryl.dsl.{OneToMany, CompositeKey2}
import org.squeryl._//{ Schema, SessionFactory, Session, KeyedEntity }
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.annotations.Column
import org.squeryl.PrimitiveTypeMode._
import java.sql.{ DriverManager }


case class User2(id: Long, username: String) extends KeyedEntity[Long]
case class Identity(id: Long, email: String) extends KeyedEntity[Long]
object Database extends Schema {
    val usersTable: Table[User2] =
        table[User2]
    val identitiesTable: Table[Identity] =
        table[Identity]
    on(usersTable) { p =>
        declare {
            p.id. is(autoIncremented)
        }
    }
    on(identitiesTable) { s =>
        declare {
            s.id is(autoIncremented)
        }
    }
}
