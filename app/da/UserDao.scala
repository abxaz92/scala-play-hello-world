package da

import anorm.{RowParser, SqlParser, ~}
import javax.inject.{Inject, Singleton}
import model.User
import play.api.db.Database
import services.DbExecutionContext

@Singleton
class UserDao @Inject()(db: Database, ec: DbExecutionContext) extends AbstractDao[User](db, "users", ec) {

  override def getRowMapper(): RowParser[User] = {
    SqlParser.long("id") ~
      SqlParser.str("name") ~
      SqlParser.str("fio") ~
      SqlParser.long("accountId") map {
      case id ~ name ~ fio ~ accountId => User(Option(id), name, fio, accountId)
    }
  }

}
