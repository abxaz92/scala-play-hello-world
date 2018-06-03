package models

object PaymentStatus extends Enumeration {
  val CREATED, COMPLETE, CANCEL, ROLLBACK = Value
}
