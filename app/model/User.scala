package model

import play.api.libs.json.{Format, Json}

case class User(var id: Option[Long], name: String, fio: String, accountId: Long) extends AbstractEntity

object User {
  def apply(name: String, fio: String, accountId: Long): User = User(None, name, fio, accountId)

  implicit val jsonFormat: Format[User] = Json.format[User]

}