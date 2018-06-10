package controllers

import java.time.{LocalDate, LocalDateTime}
import javax.inject.Inject

import akka.actor.{ActorRef, ActorSystem, Props}
import async.PaymentActor
import da.{PaymentDao, RestClient}
import models.{Payment, PaymentStatus, PaymentType}
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

class PaymentController @Inject()(cc: ControllerComponents, paymentDao: PaymentDao, rest : RestClient) extends AbstractController(cc) {
  val logger = Logger(this.getClass)

  def getAll() = Action {
//    val payment = new Payment(0, LocalDateTime.now(), "userId", "comment", PaymentType.Refil, 300, 1, PaymentStatus.CREATED, 0)
//    paymentDao.insert(payment)
    val balance = paymentDao.checkBalance("userId")
    logger.info(s"user balance is '${balance}'")
    val system = ActorSystem("pay")

    val pa : ActorRef = system.actorOf(Props[PaymentActor], "PaymentActor")
    pa ! rest.getPaypacks()
    Ok(Json.toJson(paymentDao.findAll())).as("application/json")
  }

}
