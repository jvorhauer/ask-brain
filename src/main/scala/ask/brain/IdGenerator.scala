package ask.brain
import com.fasterxml.uuid.EthernetAddress
import com.fasterxml.uuid.impl.TimeBasedGenerator
import com.fasterxml.uuid.Generators
import java.{util => ju}

object IdGenerator {
  private val ea  = EthernetAddress.fromInterface()
  private val tbg = Generators.timeBasedGenerator(ea)

  def random(): ju.UUID = tbg.generate()
}
