package controllers

import javax.inject.Inject
import models.Task
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

class TaskController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def tasks = Action{
    Ok(Json.toJson(Task.all())).as("application/json")
  }
  def tasksGen = Action{
    Task.create("label", "who", "time")
    Ok("added... !")
  }

  def addTask = Action {
    request =>
      val task = request.body.asJson.get.as[Task]
      Task.create(task)
      Ok(Json.toJson(task))
  }

  def deleteTask(id: Long) = TODO

  def completeTask(id: Long) = TODO

}
