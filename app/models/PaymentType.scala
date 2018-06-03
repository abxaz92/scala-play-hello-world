package models

import play.api.libs.json._

object PaymentType extends Enumeration {
  val Refil, Purchase = Value
  implicit val jsonFormat: Format[PaymentType.Value] = new Format[PaymentType.Value] {
    override def reads(json: JsValue): JsResult[PaymentType.Value] = JsSuccess(PaymentType.withName(json.as[String]))

    override def writes(o: PaymentType.Value): JsValue = JsString(o.toString)
  }
}
