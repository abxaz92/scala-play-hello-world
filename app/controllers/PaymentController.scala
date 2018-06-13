package controllers

import java.time.{LocalDate, LocalDateTime}
import javax.inject.Inject

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.routing.BalancingPool
import async.PaymentActor
import da.{PaymentDao, RestClient}
import models.{Payment, PaymentStatus, PaymentType}
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import scala.concurrent._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent._
import ExecutionContext.Implicits.global

class PaymentController @Inject()(cc: ControllerComponents, paymentDao: PaymentDao, rest: RestClient) extends AbstractController(cc) {
  val logger = Logger(this.getClass)

  val system = ActorSystem("pay")

  val actor: ActorRef = system.actorOf(Props[PaymentActor], "PaymentActor" )

  def getAll() = Action {
    //    val payment = new Payment(0, LocalDateTime.now(), "userId", "comment", PaymentType.Refil, 300, 1, PaymentStatus.CREATED, 0)
    //    paymentDao.insert(payment)
    val balance = paymentDao.checkBalance("userId")
    logger.info(s"user balance is '${balance}'")

    val a = System.currentTimeMillis()
    for (i <- 0 to 10){
      implicit val timeout = Timeout(5 seconds)
      val resut = actor ? rest.getPaypacks()
      resut.onComplete(res => logger.error(s"RSPONSE ${res}"))
    }
    val b = System.currentTimeMillis() - a
    logger.error(s"All current millis ${b}")
    Ok(Json.toJson(paymentDao.findAll())).as("application/json")
  }

}
