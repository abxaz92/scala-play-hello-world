package controllers

import javax.inject.Inject

import da.PaypackDao
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

class PaypackController @Inject()(cc: ControllerComponents, paypackDao: PaypackDao) extends AbstractController(cc) {
  def getAll = Action {
    Ok(Json.toJson(paypackDao.findAll())).as("application/json")
  }
}
