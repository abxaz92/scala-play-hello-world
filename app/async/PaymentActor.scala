package async

import akka.actor.Actor
import play.api.Logger

class PaymentActor extends Actor {
  val logger = Logger(this.getClass)
  override def receive = {
    case msg => logger.error(s" ${msg}")
  }
}
