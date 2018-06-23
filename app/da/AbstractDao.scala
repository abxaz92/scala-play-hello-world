package da

import anorm.{RowParser, SQL}
import javax.inject.Inject
import model.{AbstractEntity, User}
import play.api.db.Database

class AbstractDao[T <: AbstractEntity](db: Database, tableName: String) {

  def findAll()(implicit simple: RowParser[T]): List[T] = {
    db.withConnection { implicit c =>
      SQL(s"""SELECT * FROM $tableName""")
        .as(simple *)
    }
  }

  def findByID(id: Long)(implicit simple: RowParser[T]): T = {
    db.withConnection {
      implicit c =>
        SQL(
          s"""
              SELECT * FROM $tableName
              WHERE id = {id}
           """).on("id" -> id).as(simple single)
    }
  }
}
