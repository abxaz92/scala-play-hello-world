package controllers

import javax.inject.Inject
import models.{Task, TaskDao}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

class TaskController @Inject()(cc: ControllerComponents, taskDao: TaskDao) extends AbstractController(cc) {

  def tasks = Action {
    Ok(Json.toJson(taskDao.allDb())).as("application/json")
  }

  def tasksGen = Action {
    taskDao.create("label", "who", "time")
    Ok("added... !")
  }

  def addTask = Action {
    request =>
      val task = request.body.asJson.get.as[Task]
      taskDao.create(task)
      Ok(Json.toJson(task))
  }

  def deleteTask(id: Long) = Action {
    taskDao.delete(id)
    Redirect(routes.TaskController.tasks())
  }

  def completeTask(id: Long) = TODO

}
