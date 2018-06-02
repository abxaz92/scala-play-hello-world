package controllers

import javax.inject.Inject

import models.{Task, TaskDao}
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

class TaskController @Inject()(cc: ControllerComponents, taskDao: TaskDao) extends AbstractController(cc) {
  val logger = Logger(this.getClass)

  def tasks = Action {
    Ok(Json.toJson(taskDao.allDb())).as("application/json")
  }

  def tasksGen = Action {
    val task = new Task(Option.empty, "label", "who", "time", 0);
    taskDao.create(task);
    Ok("added... !")
  }

  def addTask = Action {
    request =>
      val task = request.body.asJson.get.as[Task]
      taskDao.create(task)
      Ok(Json.toJson(task))
  }

  def deleteTask(id: Long) = Action {
    val isDeleted = taskDao.delete(id)
    logger.info(s"delete for document '${id}' success is ${isDeleted}, ")
    Redirect(routes.TaskController.tasks())
  }

  def completeTask(id: Long) = TODO

}
