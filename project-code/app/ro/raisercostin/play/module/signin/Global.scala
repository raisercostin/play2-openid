package ro.raisercostin.play.module.signin

import org.squeryl.adapters.H2Adapter
import org.squeryl.{Session, SessionFactory}
import play.api.db.DB
import play.api.{Application, GlobalSettings}
import org.squeryl.SessionFactory
object Global extends GlobalSettings {
    SessionFactory.concreteFactory = Some(() =>
        Session.create(
            DB.getConnection()(app), new H2Adapter))
}