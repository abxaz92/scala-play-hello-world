package models

import java.util.concurrent.atomic.AtomicInteger
import javax.inject.{Inject, Singleton}

import anorm.{RowParser, SqlParser}
import play.api.db.Database
import play.api.libs.json.{Format, Json}

case class Task(var id: Option[Long], label: String, who: String, mytime: String, ready: Short)

object Task {
  implicit val taskJsonFormat: Format[Task] = Json.format[Task]
//  implicit val taskJsonFormat: Format[Task] = Json.format[Task]

  import anorm.~
  import anorm.SqlParser._

  val simple: RowParser[Task] = {
    SqlParser.long("id") ~
      SqlParser.str("label") ~
      SqlParser.str("who") ~
      SqlParser.str("mytime") ~
      int("ready") map {
      case id ~ label ~ who ~ mytime ~ ready => new Task(Option.apply(id), label, who, mytime, ready.toShort)
    }
  }
}

@Singleton
class TaskDao @Inject()(db: Database) {

  def apply(label: String, who: String, mytime: String, ready: Short): Task =
    Task(Option.empty, label, who, mytime, ready)

  var tasks = Map[Long, Task]()
  val count = new AtomicInteger()

  def all(): List[Task] = tasks.values.toList.sorted(Ordering.by((task: Task) => task.id).reverse)

  import anorm._

  def allDb(): List[Task] = {
    db.withConnection { implicit connection =>
      SQL("select * from task").as(Task.simple *)
    }
  }

  def create(task: Task): Unit = {
    db.withConnection { implicit c =>
      SQL(
        """ INSERT INTO Task(label, who, mytime, ready)
            VALUES ({label}, {who}, {mytime}, {ready})
          """
      ).on("label" -> task.label, "who" -> task.who, "mytime" -> task.mytime, "ready" -> 0).executeInsert()
    }
  }

  def delete(id: Long) : Boolean ={
    db.withConnection{
      implicit c =>
        SQL(
          """
             DELETE FROM Task
             where id = {id}
          """).on("id" -> id).executeUpdate() > 0
    }
  }

  def complete(id: Long) {}

}
