package services

import javax.inject.Singleton
import org.apache.poi.openxml4j.opc.OPCPackage
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import play.api.Logger

import scala.collection.JavaConverters._
import scala.util.{Success, Try}

@Singleton
class ExcelParser {
  import ExcelParser._

  val logger = Logger(classOf[ExcelParser])

  parse

  private def workbook[T](path: String)(fn: XSSFWorkbook => T): Try[T] =
    Try {
      val pkg = OPCPackage.open(path)
      val workbook = new XSSFWorkbook(pkg)

      fn(workbook)
    }

  def parse: Try[List[Address]] = workbook(WorkbookPath) { wb =>
    wb.getSheetAt(DefaultSheetIdx)
      .iterator()
      .asScala
      .map(Address.fromRow)
      .collect { case Success(address) => address }
      .toList
  }
}

object ExcelParser {

  val DefaultSheetIdx = 0
  val WorkbookPath = "C:\\Users\\Давид\\Desktop\\Address.xlsx"

  val CityIdx = 0
  val StreetIdx = 1
  val HouseIdx = 2

  case class Address(city: String, street: String, house: String)
  object Address {
    def fromRow(row: Row): Try[Address] =
      Try {
        val city = row.getCell(CityIdx).getStringCellValue
        val street = row.getCell(StreetIdx).getStringCellValue
        val house = row.getCell(HouseIdx).getStringCellValue

        Address(city, street, house)
      }
  }
}
