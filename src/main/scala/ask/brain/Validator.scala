package ask.brain

import akka.actor.Actor
import akka.event.Logging
import scala.xml.Elem
import akka.actor.Props

import scala.util.Try
import scala.xml.XML
import scala.util.Success
import scala.util.Failure

class Validator extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case xml: String =>
      Try(XML.loadString(xml.replaceAll("\n", ""))) match {
        case Success(e) =>
          val v = (e \ "Aanvraag" \ "AanvraagID").map(_.text).toList
          log.info(s"received Elem ${e.label} with id ${v.head}")
          sender ! Some(v.head)
        case Failure(exception) =>
          log.error(s"parse XML failed: ${exception.getMessage()}")
          sender ! None
      }

    case _ => log.warning("received a not-Elem")
  }
}

object Validator {
  val props: Props = Props[Validator]

  final case class PostPerformed(desc: String)
}
