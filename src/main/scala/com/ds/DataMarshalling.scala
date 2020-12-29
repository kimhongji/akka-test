package com.ds

import spray.json._

case class DataDescription(data: Int){
  require(data > 0)
}

case class Error(message: String)

trait DataMarshalling extends DefaultJsonProtocol{
  import myStack._

  implicit val dataDescriptionFormat: RootJsonFormat[DataDescription] = jsonFormat1(DataDescription)
  implicit val dataFormat: RootJsonFormat[Data] = jsonFormat1(Data)
  implicit val errorFormat: RootJsonFormat[Error] = jsonFormat1(Error)

}