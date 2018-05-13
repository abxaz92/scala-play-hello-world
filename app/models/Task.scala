package models

import java.util.concurrent.atomic.AtomicInteger

import anorm.SqlParser.get
import play.api.libs.json.Json
import anorm._
import play.api.db._
import play.api.Play.current
import anorm.SqlParser._

case class Task(var id: Long, label: String, who: String, mytime: String, ready: Short) {
}

object Task {
  implicit val taskJsonFormat = Json.format[Task]

  val task = {
    get[Long]("id") ~
      get[String]("label") ~
      get[String]("who") ~
      get[String]("mytime") ~
      get[Short]("ready") map {
      case id~label~who~mytime~ready => new Task(id, label, who, mytime, ready)
    }
  }

  def apply(label: String, who: String, mytime: String, ready: Short) : Task =
    Task(0, label, who, mytime, ready)

  var tasks = Map[Long, Task]()
  val count = new AtomicInteger()

  def all(): List[Task] = tasks.values.toList.sorted(Ordering.by((task: Task) => task.id).reverse)

  def allDb(): List[Task] = {
    DB.withConnection { implicit connection =>
      SQL("select * from bar").as(Bar.simple *)
    }
  }

  def create(label: String, who: String, time: String): Unit = {
    val task = Task(label, who, time, 1)
    create(task)
  }

  def create(task: Task): Unit = {
    val id = count.addAndGet(1).longValue()
    tasks += id -> task
    task.id = id
  }

  def delete(id: Long) {
    tasks -= id
  }

  def complete(id: Long) {}

}
