package com.ds

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._


class RestApi(system: ActorSystem, timeout: Timeout) extends Rest{
	implicit val requestTimeout: Timeout = timeout
	implicit def executionContext = system.dispatcher
	def createStack = system.actorOf(myStack.props)


}

trait Rest extends StackApi with DataMarshalling {
	import StatusCodes._

	def route = stackRoute ~ stackPostRout


	//get/post route
	def stackRoute =
		pathPrefix("storage"){
			pathEndOrSingleSlash{
				get{
					//Get /storage
					onSuccess(getData()) {
						case Some(value) => complete(OK, value)
						case None => complete(BadRequest, "dataset is empty")
					}
				}
			}
		}

	def stackPostRout=
		pathPrefix("storage" / Segment){ dt =>
			pathEndOrSingleSlash{
				post{
					//POST /storage/:dt
					entity(as[DataDescription]){ ed =>
						onSuccess(createData(data = ed.data)) {
							case myStack.DataCreated(data) => println(s"\nrouter post: ${data}"); complete(OK, myStack.Data(data))
							case myStack.DataExists => println(s"router post"); complete(BadRequest, "data already exists")
						}
					}
				}
			}
		}
}

//trait으로 뺀 이유는 코드를 깔끔하게 하기 위해서임
trait StackApi{
	import myStack._

	def createStack(): ActorRef

	implicit def executionContext: ExecutionContext
	implicit def requestTimeout: Timeout

	val ms: ActorRef = createStack()

	def createData(data: Int) =
		ms.ask(CreateData(data)).mapTo[DataResponse]

	def getData() =
		ms.ask(GetData).mapTo[Option[Data]]
}
