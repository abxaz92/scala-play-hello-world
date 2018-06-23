package controllers

import da.UserDao
import javax.inject.Inject
import model.User
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.Future

class UserController @Inject()(userDao: UserDao,
                               cc: ControllerComponents) extends AbstractController(cc) {

  import UserDao.parser
  import scala.concurrent.ExecutionContext.Implicits.global

  private val contentTypeJson = "application/json"

  def getAll = Action.async {
    Future {
      val users: List[User] = userDao.findAll()
      Ok(Json.toJson(users)).as(contentTypeJson)
    }
  }

  def getById(id: Long) = Action.async {
    Future {
      val maybeUser = userDao.findByID(id)
      if (maybeUser.isDefined)
        Ok(Json.toJson(maybeUser)).as(contentTypeJson)
      else
        NotFound
    }
  }

  def deleteById(id: Long) = Action.async {
    Future {
      if (userDao.deleteById(id))
        NoContent
      else
        NotFound("user not found")
    }
  }

}
