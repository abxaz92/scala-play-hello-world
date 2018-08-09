package services

import scala.util.Try

object Implicits {
  implicit def optionOfString2OptionOfInt(o: Option[String]): Option[Int] = {
    o.flatMap(s => Try(s.toInt).toOption)
  }
}
