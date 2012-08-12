package ro.raisercostin.play.module.signin

import org.squeryl.adapters.H2Adapter
import org.squeryl.adapters.PostgreSqlAdapter
import org.squeryl.{Session, SessionFactory}
import play.api.db.DB
import play.api.{Application, GlobalSettings}
import play.api.Play.current
import org.squeryl.SessionFactory
object Global extends GlobalSettings {
    SessionFactory.concreteFactory = Some(() =>
        Session.create(
            DB.getConnection(), new PostgreSqlAdapter//H2Adapter
            ))
}