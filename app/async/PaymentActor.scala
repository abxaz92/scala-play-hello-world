package async

import java.lang
import javax.inject.{Inject, Singleton}

import akka.actor.Actor
import da.RestClient
import play.api.Logger

@Singleton
class PaymentActor @Inject()(restClient: RestClient) extends Actor {
  val logger = Logger(this.getClass)
  override def receive = {
    case msg => sender() ! restClient.getPaypacks()
  }
}
