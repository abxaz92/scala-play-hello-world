package da

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.actor.FSM.Failure
import akka.actor.Status.Success
import akka.stream.ActorMaterializer
import models.Paypack
import play.api.Logger

import scala.concurrent._
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.duration.Duration

class RestClient @Inject()(ws: WSClient) {
  val logger = Logger(this.getClass)

  def getPaypacks(): String = {
    val a = System.currentTimeMillis()

    val response: Future[WSResponse] = ws.url("http://localhost:9000/paypacks").get()
    val res = Await.result(response, Duration.Inf)
    val b = System.currentTimeMillis() - a
    logger.warn(s" each current millis ${b}")

    res.body
  }
}
