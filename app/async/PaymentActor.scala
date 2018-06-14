package async

import akka.actor.Actor
import akka.pattern.pipe
import da.RestClient
import javax.inject.{Inject, Singleton}
import models.Paypack
import play.api.Logger

@Singleton
class PaymentActor @Inject()(restClient: RestClient) extends Actor {
  import PaymentActor._
  import context.dispatcher

  val logger = Logger(this.getClass)
  override def receive: Receive = {
    case PaypacksRequest =>
      restClient.getPaypacks()
        .map(PaypacksResponse.apply)
        .pipeTo(sender())
  }
}

object PaymentActor {
  case object PaypacksRequest
  case class PaypacksResponse(paypacks: List[Paypack])
}
