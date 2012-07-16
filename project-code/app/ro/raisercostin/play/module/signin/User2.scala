package ro.raisercostin.play.module.signin

//import scala.collection.mutable.MutableList
import _root_.org.squeryl.KeyedEntity
import _root_.org.squeryl.Schema
import _root_.org.squeryl.Table
import _root_.org.squeryl.dsl._
import _root_.org.squeryl.dsl.ast._
import _root_.org.squeryl.dsl.{OneToMany, CompositeKey2}

case class User2(id: Long, username: String) extends KeyedEntity[Long]
case class Identity(id: Long, email: String) extends KeyedEntity[Long]
object Database extends Schema {
    val usersTable: Table[User2] =
        table[User2]
    val identitiesTable: Table[Identity] =
        table[Identity]
    on(usersTable) { p =>
        declare {
            p.id is(autoIncremented)
        }
    }
    on(identitiesTable) { s =>
        declare {
            s.id is(autoIncremented)
        }
    }
}
