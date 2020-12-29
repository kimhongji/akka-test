package com.ds

import scala.collection.immutable.Stack
import akka.actor._
import akka.util.Timeout
import sun.awt.SunToolkit.OperationTimedOut

object myStack{
	def props = Props(new myStack)

	case object GetData
	case class CreateData(data: Int)
	case class DataSet(dataset: Vector[Int])

	case class Data(num: Int)

	sealed trait DataResponse //sealed 은 외부에서 상속 안됨
	case class DataCreated(data: Int) extends DataResponse
	case object DataExists extends DataResponse //case class는 무조건 parameter를 받아야함
}

class myStack extends Actor{
	import myStack._

	var dataset = Stack.empty[Data]

	def receive: Receive = {
		case GetData =>
			if(dataset.isEmpty){
				println("failed pop")
				sender() ! None
			}else{
				val topData = dataset.top
				println("pop: ",topData.num)
				dataset = dataset.pop
				sender() ! Some(topData)
			}
		case CreateData(data) =>
			dataset = dataset.push(Data(data))
			println(s"push: ${data}")
			dataset.foreach(x => print(x.num+", "))
			sender() ! DataCreated(data)
	}	 

}

