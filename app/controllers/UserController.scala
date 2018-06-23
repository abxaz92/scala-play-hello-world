package controllers

import da.UserDao
import javax.inject.Inject
import model.User
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.Future
import services.Implicits._

class UserController @Inject()(userDao: UserDao, cc: ControllerComponents)
  extends AbstractController(cc) {

  import UserDao.parser
  import scala.concurrent.ExecutionContext.Implicits.global

  def getAll(limit: Option[String], offset: Option[String]) = Action.async { implicit rq =>
    val eventualUsers: Future[List[User]] = userDao.findAll(limit, offset)
    eventualUsers.map(users => Ok(users))
  }

  def getById(id: Long) = Action.async {
    val maybeUserFuture = userDao.findByID(id)
    maybeUserFuture.map(maybeUser =>
      if (maybeUser.isDefined)
        Ok(maybeUser.get)
      else
        NotFound
    )
  }

  def deleteById(id: Long) = Action.async {
    val deleteFuture = userDao.deleteById(id)
    deleteFuture.map(res => {
      if (res)
        NoContent
      else
        NotFound("user not found")

    })
  }

}
