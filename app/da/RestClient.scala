package da

import java.util.concurrent.Executors
import javax.inject.Inject

import models.Paypack
import play.api.Logger
import play.api.libs.ws.WSClient

import scala.concurrent._
import ExecutionContext.Implicits.global

class RestClient @Inject()(ws: WSClient) {

  val logger = Logger(this.getClass)
  def getPaypacks(): Future[List[Paypack]] = {
    ws.url("http://localhost:9000/paypacks").get()
    .map(resp => resp.json.validate[List[Paypack]].get)
  }
}
