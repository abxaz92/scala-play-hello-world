package da

import anorm.{NamedParameter, RowParser, SQL}
import javax.inject.Inject
import model.{AbstractEntity, User}
import play.api.db.Database
import services.DbExecutionContext

import scala.concurrent.{ExecutionContext, Future}

class AbstractDao[T <: AbstractEntity](db: Database, tableName: String, ec: DbExecutionContext) {

  def findAll(limit: Option[Int], offset: Option[Int])(implicit simple: RowParser[T]): Future[List[T]] = {
    Future {
      db.withConnection { implicit c =>
        SQL(
          s"""SELECT * FROM $tableName
           limit {limit} offset {offset}
         """)
          .on("limit" -> limit.getOrElse(10), "offset" -> offset.getOrElse(0))
          .as(simple *)
      }
    }(ec)
  }

  def findByID(id: Long)(implicit simple: RowParser[T]): Future[Option[T]] = {
    Future {
      db.withConnection {
        implicit c =>
          SQL(
            s"""
              SELECT * FROM $tableName
              WHERE id = {id}
           """).on("id" -> id).as(simple singleOpt)
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
