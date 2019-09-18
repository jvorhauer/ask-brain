package ask.brain

import org.scalatest.WordSpec
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.Matchers
import akka.http.scaladsl.server._
import Directives._
import akka.actor.ActorRef
import scala.concurrent.ExecutionContext
import akka.http.scaladsl.model.StatusCodes

class BrainServerTest extends WordSpec with ScalatestRouteTest with Matchers with BrainRoutes {

  override val validator: ActorRef = system.actorOf(Validator.props)

  "The BrainServer" should {
    "return Hi on GET" in {
      Get() ~> route ~> check {
        responseAs[String] shouldEqual "Hi"
      }
    }

    "return aanvraagId on POST" in {
      val xml = "<ns1:AanvraagRequest><ns1:Aanvraag><ns1:AanvraagID>123</ns1:AanvraagID></ns1:Aanvraag></ns1:AanvraagRequest>"
      Post().withEntity(xml) ~> route ~> check {
        responseAs[String] shouldEqual "123"
      }
    }

    "return 400 on POST of invalid XML" in {
      val notxml = "<ns1:AanvraagRequest><ns1:Aanvraag><ns1:AanvraagID>123</ns1:AanvraagID></ns1:Aanvraag></ns1:AanvraagRequest"
      Post().withEntity(notxml) ~> route ~> check {
        status shouldEqual StatusCodes.BadRequest
      }
    }
  }
}
