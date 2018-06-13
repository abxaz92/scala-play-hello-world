package actor

import async.PaymentActor
import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport
import play.api.{Configuration, Environment}

class AppModule(env: Environment, config: Configuration) extends AbstractModule with AkkaGuiceSupport {

  override def configure(): Unit = {
    bindActor[PaymentActor]("payment-service")
  }
}

