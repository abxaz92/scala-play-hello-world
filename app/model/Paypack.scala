package model

import anorm.{RowParser, ~}
import anorm.SqlParser.{double, long, str}
import play.api.libs.json.{Format, Json}

case class Paypack(
                    id: Long,
                    name: String,
                    cost: Double,
                    period: Double
                  )

object Paypack {
  implicit val jsonFormat: Format[Paypack] = Json.format[Paypack]

  val simple: RowParser[Paypack] = {
    long("id") ~
      str("name") ~
      double("cost") ~
      double("payperiod") map {
      case id ~ name ~ cost ~ payperiod => new Paypack(id, name, cost, payperiod)
    }

  }

}
