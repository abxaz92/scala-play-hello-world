package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

class HelloController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def hello = Action {
    Ok(" world")
  }
}
