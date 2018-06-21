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
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.io.Source
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
    val dockerCommannds = Future {
      Source.fromFile("""C:\Users\Давид\Desktop\docker commands.txt""").getLines().toList
    }

    val bashHist = Future {
      Source.fromFile("""C:\Users\Давид\.bash_history""").getLines().toList
    }

    val res = for {
      docker <- dockerCommannds
      bash <- bashHist
    } yield {
      logger.error(s"bash $bash")
      logger.info(s"docker $docker")
    }

    (actor ? PaypacksRequest)
      .map {
        case PaypacksResponse(packs) =>
          logger.error(s"Response: $packs")
          Ok(Json.toJson(paymentDao.findAll())).as("application/json")
      }
  }

}
