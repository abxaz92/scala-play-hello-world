package da

import javax.inject.Inject
import models.Paypack
import play.api.Logger
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

class RestClient @Inject()(ws: WSClient) {

  val logger = Logger(this.getClass)
  def getPaypacks(): Future[List[Paypack]] = {
    ws.url("http://localhost:9000/paypacks").get()
    .map(resp => resp.json.validate[List[Paypack]].get)
  }
}
