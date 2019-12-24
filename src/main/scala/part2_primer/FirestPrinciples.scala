package part2_primer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

object FirstPrinciples extends App{

  implicit val system = ActorSystem("FirstPrinciples")
  implicit val materializer = ActorMaterializer()
// create simple stream
  val source = Source(1 to 10)
  val sink = Sink.foreach[Int](println)
  val flow = Flow[Int].map(x=>x+1)

  val graph = source.via(flow)to(sink)
  // graph.run()

  //flow - transforms elements

  val sourceWithFlow = source.via(flow)
  val flowWithSink = flow.to(sink)

  //  sourceWithFlow.to(sink).run()
  //  source.to(flowWithSink).run()
  //  source.via(flow).to(sink).run() // more natures

  // NULL are not allowed
  val illegalSource = Source.single[Option[String]](None)
  illegalSource.to(Sink.foreach(println)).run()
  // use Option instead

  // various type of source
  val finiteSource = Source.single(1)
  val anotherFiniteSource = Source(List(1,2,3,4))
  val emptySource = Source.empty[Int]
  val infiniteSource = Source(Stream.from(1)) // ?
  val futureSource = Source.fromFuture(Future(42))

  // sinks
  val theMostBoringSink = Sink.ignore
  val foreachSink = Sink.foreach[String](println)
  val headSink = Sink.head[Int] // retrieves only head
  val foldSink = Sink.fold[Int,Int](0)((a,b)=>a+b)

    // flow usualy mapped to collection operator
  val mapFlow = Flow[Int].map(x=>x+1)
  val takeFlow = Flow[Int].take(5)
  // drop, filter
  // Not have flatMap

  // source -> flow -> flow -> ... -> sink
  val doubleFlowGraph = source.via(mapFlow).via(takeFlow).to(sink)
  doubleFlowGraph.run()
  // syntatic sugar
  val mapSource = Source(1 to 10).map(x=>x*2) // same Source(1 to 10).via(Flow[Int].map(x=>x*2))

  // run streams directly
  mapSource.runForeach(println) // same mapSource.to(Sink.foreach[Int](println))



}
