package part1_recap

import akka.actor.{Actor, ActorSystem, Props}

object ScalaRecap extends App{

  class SimpleActor extends Actor {
    override def receive: Receive = {
      case "createChild"=> // actors can cpawn other actors
        val childActor = context.actorOf(Props[SimpleActor],"myChild")
        childActor ! "Hello from child"
      case message => println(s"I reseiver: $message")
    }
  }
  // actor incapsulation
  val system = ActorSystem("AkkaRecap")
  //1: You can only enstantiate an actor throw the actor system
  val actor = system.actorOf(Props[SimpleActor], "simpleActor")
  // 2: sending messages
  actor ! "createChild"

  /*
  - messages are sent asynchronously
  - many actors (in the millions) can share a few dozen threads
  - each message is processed - handled ATOMICALLY
  - no need for locks
   */

  // actors can cpawn other actors
  // actors have a defined lifecycle: they can be started, stoped, apended
}
