package part2_primer

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.{Flow, Sink, Source}



object BackpressureBasic extends App{

  implicit val system = ActorSystem("BackpressureBasic")
  implicit val materializer = ActorMaterializer()

  val fastSource = Source(1 to 1000)
  val slowSink = Sink.foreach[Int]{x=>
    Thread.sleep(1000)
    println(s"Sink: $x")
  }
  //fastSource.to(slowSink).run()
  // no backpressure

  //fastSource.async.to(slowSink).run()
  // backpressure
  val simpleFlow = Flow[Int].map{x=>
  println(s"Incoming $x")
  x+1
  }

//  fastSource.async
//    .via(simpleFlow).async
//    .to(slowSink)
//    .run() // backpressure in action

  val bufferedFlow = simpleFlow.buffer(10, overflowStrategy = OverflowStrategy.dropHead)

  fastSource.async
    .via(bufferedFlow).async
    .to(slowSink)
    .run()

  /*
  1-16 - nobody no backpressure
  17-26 - flow will buffer, flow will start dropping at the next element
  26 - 1000 - flow will always drop the oldest elements
    => 991-1000 => 992- 1001 => sink
   */
  /*
  overflow strategies
  - drop head = oldest
  - drop tail = newest
  - drop new = exact element to be added = keeps the buffer
  - drop the entire buffer
  - backpressure signal
  - fail
   */

  // throttling
  import scala.concurrent.duration._

 // fastSource.throttle(2,1 second)

//  val fastSource2 = Source.fromIterator { () =>
//    Iterator.from(1).map(1.to)}
//    .mapConcat(identity)
  List(1,2,3,4).flatMap(x=>List(x*10))
}
