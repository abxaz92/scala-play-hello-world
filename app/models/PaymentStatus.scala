package models

import play.api.libs.json._

object PaymentStatus extends Enumeration {
  val CREATED, COMPLETE, CANCEL, ROLLBACK = Value
  implicit val jsonFormat: Format[PaymentStatus.Value] = new Format[PaymentStatus.Value] {
    override def reads(json: JsValue): JsResult[PaymentStatus.Value] = JsSuccess(PaymentStatus.withName(json.as[String]))

    override def writes(o: PaymentStatus.Value): JsValue = JsString(o.toString)
  }
}
