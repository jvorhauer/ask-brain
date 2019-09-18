package ask.brain

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}
import scala.xml.XML
import java.{util => ju}
import akka.actor.Props

object BrainServer extends App with BrainRoutes {
  implicit val system:       ActorSystem       = ActorSystem("brainsys")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executor:     ExecutionContext  = ExecutionContext.global

  override val validator = system.actorOf(Validator.props, "validator")

  private[this] val server: Future[Http.ServerBinding] = Http().bindAndHandle(route, "localhost", 9090)

  server.onComplete {
    case Success(bound) => println(s"${bound.localAddress.getHostString}:${bound.localAddress.getPort}")
    case Failure(e) =>
      Console.err.println(s"Server could not start: ${e.getMessage}")
      system.terminate()
  }

  Await.result(system.whenTerminated, Duration.Inf)
}



case class AanvraagRequest(aanvraag: Aanvraag)
case class Aanvraag(id: String)
case class ChangeRequest(
  id: ju.UUID,
  extId: String,
  ean: String,
  capacity: String
)
