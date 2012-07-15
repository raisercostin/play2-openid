package ro.raisercostin.play.module.signin

import scala.collection.mutable.MutableList
import org.squeryl.KeyedEntity

case class Product(
    id: Long,
    ean: Long,
    name: String,
    description: String) extends KeyedEntity[Long]
case class Warehouse(
    id: Long,
    name: String) extends KeyedEntity[Long]
case class StockItem(
    id: Long,
    product: Long,
    location: Long,
    quantity: Long) extends KeyedEntity[Long]
case class User(username: String) extends KeyedEntity[Long]
case class Identity(email: String) extends KeyedEntity[Long]

object User {
    var users = Map[String, User]()

    def authenticated(emails: java.util.List[_]): User = {
        val emails2 = convert(emails)
        for (email <- emails2) {
            if (users.contains(email)) {
                var user = users(email)
                user = User(email, emails2)
                for (email2 <- emails2) {
                    users += email2 -> user
                }
                return user
            }
        }
        val user = User(emails2(0), emails2)
        users += user.id -> user
        user
    }
    def authenticated(identifier: Option[String]): User = {
        if (!identifier.isEmpty) {
            if (users.contains(identifier.get)) {
                var user = users(identifier.get)
                if (user != null) {
                    return user
                }
            }
            val user = User(identifier.get, List.empty)
            users += user.id -> user
            user
        }
        null
    }

    def convert(list: java.util.List[_]): List[String] = {
        var result = List[String]()
        import collection.JavaConversions._
        for (obj <- list) {
            val text = obj.asInstanceOf[String]
            result ::= text
        }
        result
    }
}
