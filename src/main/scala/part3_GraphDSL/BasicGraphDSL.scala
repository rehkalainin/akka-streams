package part3_GraphDSL

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ClosedShape}
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, RunnableGraph, Sink, Source, Zip}

object BasicGraphDSL extends App{

  implicit val system = ActorSystem("BasicGraphDSL")
  implicit val materializer = ActorMaterializer()

  val input = Source(1 to 1000)
  val increamenter = Flow[Int].map(x=>x+1)
  val multiplier = Flow[Int].map(x=>x*10)
  val output = Sink.foreach[(Int,Int)](println)

  // step 1 - setting up the fundamental for the graph
  val graph = RunnableGraph.fromGraph(
    GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
      import GraphDSL.Implicits._

      // step 2 - declare components
      val broadcast = builder.add(Broadcast[Int](2)) // fan-out operator
      val zip = builder.add(Zip[Int,Int]) // fan-in operator

      // step 3 - typing up the components (обвязка)
      input ~> broadcast

      broadcast.out(0) ~> increamenter ~> zip.in0
      broadcast.out(1) ~> multiplier ~> zip.in1

      zip.out ~> output

      // step 4 - return closed shape
      ClosedShape // FREEZE builder's shape
      //shape
    } // graph
  ) // runnable graph
  graph.run()

// _________________________________________________________________
  /*
  exersice 1 : feed a source into 2 sinks at the same time
   */
  val input1 = Source(1 to 1000)
  val increamenter1 = Flow[Int].map(x=>x+1)
  val multiplier2 = Flow[Int].map(x=>x*10)
  val output1 = Sink.foreach[Int](x=>println(s"First Sink : $x"))
  val output2 = Sink.foreach[Int](x=>println(s"Second Sink : $x"))

  val graph2 = RunnableGraph.fromGraph(
    GraphDSL.create(){ implicit builder: GraphDSL.Builder[NotUsed] =>
      import GraphDSL.Implicits._

      val broadcast = builder.add(Broadcast[Int](2))

      input1 ~> broadcast ~> increamenter1 ~> output1 // implicit port numbering
               broadcast ~> multiplier2   ~> output2

//      broadcast.out(0) ~> increamenter1 ~> output1
//      broadcast.out(1) ~> multiplier2 ~> output2

      ClosedShape
    }
  )
  graph2.run()
}
