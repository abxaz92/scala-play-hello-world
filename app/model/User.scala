package model

import play.api.http.Writeable
import play.api.libs.json.{Format, JsValue, Json, Writes}

case class User(var id: Option[Long], name: String, fio: String, accountId: Long) extends AbstractEntity

object User {
  def apply(name: String, fio: String, accountId: Long): User = User(None, name, fio, accountId)

  implicit val jsonFormat: Format[User] = Json.format[User]

  implicit def writeableUser(implicit jsonWriteable: Writeable[JsValue], writes: Writes[User])
  : Writeable[User] =
    new Writeable[User](u => jsonWriteable.transform(Json.toJson(u)), jsonWriteable.contentType)

  implicit def WriteableListUser(implicit jsonWriteable: Writeable[JsValue], writes: Writes[User])
  : Writeable[List[User]] =
    new Writeable[List[User]](list => jsonWriteable.transform(Json.toJson(list)), jsonWriteable.contentType)
}