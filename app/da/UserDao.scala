package da

import anorm.{NamedParameter, RowParser, SqlParser, ~}
import javax.inject.{Inject, Singleton}
import model.User
import play.api.db.Database

@Singleton
class UserDao @Inject()(db: Database) extends AbstractDao[User](db, "users") {
}

object UserDao {
  implicit val parser: RowParser[User] = {
    SqlParser.long("id") ~
      SqlParser.str("name") ~
      SqlParser.str("fio") ~
      SqlParser.long("accountId") map {
      case id ~ name ~ fio ~ accountId => User(Option(id), name, fio, accountId)
    }
  }

}
