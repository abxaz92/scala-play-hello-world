package da

import anorm.{NamedParameter, RowParser, SQL}
import javax.inject.Inject
import model.{AbstractEntity, User}
import play.api.db.Database

class AbstractDao[T <: AbstractEntity](db: Database, tableName: String) {

  def findAll(limit: Option[Int], offset: Option[Int])(implicit simple: RowParser[T]): List[T] = {
    db.withConnection { implicit c =>
      SQL(
        s"""SELECT * FROM $tableName
           limit {limit} offset {offset}
         """)
        .on("limit" -> limit.getOrElse(10), "offset" -> offset.getOrElse(0))
        .as(simple *)
    }
  }

  def findByID(id: Long)(implicit simple: RowParser[T]): Option[T] = {
    db.withConnection {
      implicit c =>
        SQL(
          s"""
              SELECT * FROM $tableName
              WHERE id = {id}
           """).on("id" -> id).as(simple singleOpt)
    }
  }

  def deleteById(id: Long): Boolean = {
    db.withConnection {
      implicit c =>
        SQL(
          s"""
            DELETE FROM $tableName
             WHERE id = {id}
          """).on("id" -> id).execute()
    }
  }

}
