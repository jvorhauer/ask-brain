package ask.brain

import akka.actor.ActorSystem
import akka.actor.ActorRef
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import akka.http.javadsl.model.StatusCode
import akka.http.scaladsl.model.StatusCodes

trait BrainRoutes {
  implicit lazy val timeout: Timeout = Timeout(5.seconds)

  implicit def system: ActorSystem
  def validator: ActorRef

  lazy val route: Route = pathEndOrSingleSlash {
    concat(
      get {
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Hi"))
      },
      post {
        entity(as[String]) { body =>
          val r: Future[Option[String]] = (validator ? body).mapTo[Option[String]]
          onSuccess(r) { result =>
            result match {
              case Some(ok) => complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, ok))
              case None => complete(StatusCodes.BadRequest)
            }
          }
        }
      }
    )
  }
}
