package services

import java.io.{File, FileInputStream}
import javax.inject.Singleton

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import play.api.Logger
import services.ExcelParser.RichCell

import scala.util.{Failure, Success, Try}

@Singleton
class ExcelParser {
  val logger = Logger(this.getClass)

  parse()

  def parse() = {
    var is: FileInputStream = null
    import scala.collection.JavaConverters._
    def read: List[Tuple3[String, String, String]] = {
      is = new FileInputStream(new File("C:\\Users\\Давид\\Desktop\\Address.xlsx"))
      val workbook = new XSSFWorkbook(is)
      val sheet = workbook.getSheetAt(0)

      val rowIterator = sheet.iterator.asScala
      for(row <- rowIterator) yield {
        val city = row.getCell(0).asString
        val street = row.getCell(1).asString
        val house = row.getCell(2).asString
//        logger.info(s"$city : $street : $house")
        Tuple3(city, street, house)
      }
    }.toList

    val res = Try({
      read
    }) match {
      case Success(result) => result
      case Failure(e) => is.close()
    }
    logger.info(res.toString)

  }


}

object ExcelParser {

  implicit class RichCell(val cell: Cell) extends AnyVal {
    def asString: String = {
      cell.getCellType match {
        case Cell.CELL_TYPE_STRING => cell.getStringCellValue
        case Cell.CELL_TYPE_BOOLEAN => Some(cell.getBooleanCellValue).getOrElse("").toString
        case Cell.CELL_TYPE_NUMERIC =>
          val value = Some[Double](cell.getNumericCellValue).getOrElse("")
          value.asInstanceOf[Double].toInt.toString
      }
    }
  }

}
