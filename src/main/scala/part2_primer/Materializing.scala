package part2_primer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


object Materializing extends App {

  implicit val system = ActorSystem("Materializer")
  implicit val materializer = ActorMaterializer()

  val source = Source(1 to 10)
  val sink = Sink.reduce[Int]((a, b) => a + b)
  val someFuture = source.runWith(sink)
    someFuture.onComplete{
      case Success(value)=>println(s"sum complete $value")
      case Failure(exception)=>println(s"sum NOT complete: $exception")
    }

  // choosing materialized value
  val simpleSource = Source(1 to 10)
  val simpleFlow = Flow[Int].map(x=>x+1)
  val simpleSink = Sink.foreach(println)

 // simpleSource.viaMat(simpleFlow)((sourceMat, flowMat)=> flowMat)
 val graph = simpleSource.viaMat(simpleFlow)(Keep.right).toMat(simpleSink)(Keep.right) // подробрее про viaMat и toMat
  graph.run().onComplete{
    case Success(_)=> println(s"Stream processing finished ")
    case Failure(ex)=> println(s"Stream failed with $ex")
  }
  //sugar
  val sum = Source(1 to 10).runWith(Sink.reduce[Int](_+_)) // Source(1 to 10).to(Sink.reduce)(Keep.right)
  val sum2 = Source(1 to 10).reduce(_+_).to(Sink.foreach(println)).run() // same
}

