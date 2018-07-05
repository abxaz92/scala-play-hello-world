package da

import anorm._
import model.AbstractEntity
import play.api.db.Database
import services.DbExecutionContext

import scala.concurrent.Future

abstract class AbstractDao[T <: AbstractEntity](db: Database, tableName: String, ec: DbExecutionContext) {

  def getRowMapper: RowParser[T]

  def findAll(limit: Option[Int], offset: Option[Int]): Future[List[T]] = {
    Future {
      db.withConnection { implicit c =>
        SQL"""SELECT * FROM #$tableName
             limit ${limit.getOrElse(10).toInt} offset ${offset.getOrElse(0).toInt}"""
          .as(getRowMapper *)
      }
    }(ec)
  }

  def findByID(id: Long): Future[Option[T]] = {
    Future {
      db.withConnection {
        implicit c =>
          SQL"""
              SELECT * FROM #$tableName
              WHERE id = $id
           """.as(getRowMapper singleOpt)
      }
    }(ec)
  }

  def deleteById(id: Long): Future[Boolean] = {
    Future {
      db.withConnection {
        implicit c =>
          SQL"""
            DELETE FROM #$tableName
             WHERE id = $id
          """.execute()
      }
    }(ec)
  }

}
