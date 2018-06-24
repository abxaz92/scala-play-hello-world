package da

import anorm.{RowParser, SQL}
import model.AbstractEntity
import play.api.db.Database
import services.DbExecutionContext

import scala.concurrent.Future

abstract class AbstractDao[T <: AbstractEntity](db: Database, tableName: String, ec: DbExecutionContext) {

  def getRowMapper : RowParser[T]

  def findAll(limit: Option[Int], offset: Option[Int]): Future[List[T]] = {
    Future {
      db.withConnection { implicit c =>
        SQL(
          s"""SELECT * FROM $tableName
           limit {limit} offset {offset}
         """)
          .on("limit" -> limit.getOrElse(10), "offset" -> offset.getOrElse(0))
          .as(getRowMapper *)
      }
    }(ec)
  }

  def findByID(id: Long): Future[Option[T]] = {
    Future {
      db.withConnection {
        implicit c =>
          SQL(
            s"""
              SELECT * FROM $tableName
              WHERE id = {id}
           """).on("id" -> id).as(getRowMapper singleOpt)
      }
    }(ec)
  }

  def deleteById(id: Long): Future[Boolean] = {
    Future {
      db.withConnection {
        implicit c =>
          SQL(
            s"""
            DELETE FROM $tableName
             WHERE id = {id}
          """).on("id" -> id).execute()
      }
    }(ec)
  }

}
