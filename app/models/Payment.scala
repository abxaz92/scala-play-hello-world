package models

import java.time.{LocalDate, LocalDateTime, ZoneId}
import java.util.Date

import anorm.SqlParser._
import anorm.{RowParser, SqlParser}
import play.api.libs.json.{Format, Json}
import anorm._

case class Payment(
                    id: Long,
                    timestamp: LocalDateTime,
                    userId: String,
                    comment: String,
                    paymentType: PaymentType.Value,
                    amount: Double,
                    paypackId: Long,
                    status: PaymentStatus.Value,
                    balance: Double
                  )

object Payment {
  implicit val jsonFormat: Format[Payment] = Json.format[Payment]

  val simple: RowParser[Payment] = {
    long("id") ~
      date("payTimestamp") ~
      str("userId") ~
      str("comment") ~
      str("paymentType") ~
      double("amount") ~
      long("paypackId") ~
      str("status") ~
      double("balance") map {
      case id ~ payTimestamp ~ userId ~ comment ~ paymentType ~ amount ~ paypackId ~ status ~ balance =>
        new Payment(id,
          toLocalDate(payTimestamp),
          userId, comment,
          PaymentType.withName(paymentType),
          amount, paypackId,
          PaymentStatus.withName(status),
          balance)
    }
  }

  private def toLocalDate(payTimestamp: Date) = {
    payTimestamp.toInstant.atZone(ZoneId.systemDefault()).toLocalDateTime
  }
}
