package controllers

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import async.PaymentActor
import async.PaymentActor._
import da.{PaymentDao, RestClient}
import javax.inject.{Inject, Named}
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps

class PaymentController @Inject()(cc: ControllerComponents,
                                  paymentDao: PaymentDao,
                                  rest: RestClient,
                                  @Named("payment-service") actor: ActorRef) extends AbstractController(cc) {
  val logger = Logger(this.getClass)

  implicit val timeout: Timeout = 5 seconds

  def getAll = Action.async {
    val balance = paymentDao.checkBalance("userId")
    logger.info(s"user balance is '${balance}'")

    (actor ? PaypacksRequest)
      .map {
        case PaypacksResponse(packs) =>
          logger.error(s"Response: $packs")
          Ok(Json.toJson(paymentDao.findAll())).as("application/json")
      }
  }

}
