package controllers

import java.time.{LocalDate, LocalDateTime}
import javax.inject.Inject

import da.PaymentDao
import models.{Payment, PaymentStatus, PaymentType}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

class PaymentController @Inject()(cc: ControllerComponents, paymentDao: PaymentDao) extends AbstractController(cc) {

  def getAll() = Action {
    val payment = new Payment(0, LocalDateTime.now(), "userId", "comment", PaymentType.Refil, 300, 1, PaymentStatus.CREATED, 0)
    paymentDao.insert(payment)
    Ok(Json.toJson(paymentDao.findAll())).as("application/json")
  }

}
