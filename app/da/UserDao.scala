package da

import anorm.{RowParser, SqlParser, ~}
import javax.inject.{Inject, Singleton}
import model.User
import play.api.db.Database
import services.DbExecutionContext
import anorm.SqlParser._

@Singleton
class UserDao @Inject()(db: Database, ec: DbExecutionContext) extends AbstractDao[User](db, "users", ec) {

  override def getRowMapper(): RowParser[User] = {
    long("id") ~
      str("name") ~
      str("fio") ~
      long("accountId") map {
      case id ~ name ~ fio ~ accountId => User(Option(id), name, fio, accountId)
    }
  }

}
