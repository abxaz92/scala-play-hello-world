package da

import javax.inject.{Inject, Singleton}

import anorm._
import models.Paypack
import play.api.db.Database
import play.api.libs.json.{Format, Json}

@Singleton
class PaypackDao @Inject()(db: Database) {

  implicit val jsonFormat: Format[Paypack] = Json.format[Paypack]

  def findAll(): List[Paypack] = {
    db.withConnection {
      implicit c =>
        SQL(
          """
          SELECT * from paypack;
          """).as(Paypack.simple *)
    }
  }
}
