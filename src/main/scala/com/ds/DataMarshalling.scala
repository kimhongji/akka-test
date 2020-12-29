package com.ds

import spray.json._

case class DataDescription(data: Int){
  require(data > 0)
}

case class Error(message: String)

trait DataMarshalling extends DefaultJsonProtocol{
  import myStack._

  implicit val dataDescriptionFormat= jsonFormat1(DataDescription)
  implicit val dataFormat = jsonFormat1(Data)
  implicit val errorFormat = jsonFormat1(Error)
  //TODO { "name": dd , "data": 4 } 형식으로 출력되도록 수정
}