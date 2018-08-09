package services

import akka.actor.ActorSystem
import javax.inject.{Inject, Singleton}
import play.api.libs.concurrent.CustomExecutionContext

@Singleton
class DbExecutionContext @Inject()(actorSystem: ActorSystem)
  extends CustomExecutionContext(actorSystem, "db-dispatcher")