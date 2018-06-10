package da

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.actor.FSM.Failure
import akka.actor.Status.Success
import akka.stream.ActorMaterializer
import models.Paypack

import scala.concurrent._
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.duration.Duration

class RestClient @Inject()(ws: WSClient) {

  def getPaypacks(): String = {
    val response: Future[WSResponse] = ws.url("http://localhost:9000/paypacks").get()
    val res = Await.result(response, Duration.Inf)
    res.body
  }
}
