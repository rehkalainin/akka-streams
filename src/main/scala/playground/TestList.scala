package playground

object TestList extends App  {

  abstract class MyList[+A]{
    def head:A
    def tail:MyList[A]
    def add[B>:A](element:B):MyList[B]
    def map[B](f: A=>B):MyList[B]
    def flatMap[B](f: A=>MyList[B]):MyList[B]
    def filter(p: A=>Boolean):MyList[A]
    def ++[B>:A](list:MyList[B]):MyList[B]
    def prepend[B>:A](element:B):MyList[B]
    def reverse: MyList[A]
    def last: A
  }
//  object MyList{
//    def apply[A](elements:A*): MyList[A]={
//      if(elements.isEmpty)Empty
//      else nList(elements.head, apply(elements.tail:_*))
//    }
//  }

    object MyList{
      def apply[A](elements:A*): MyList[A] ={
        def helper (paramList:Seq[A], myList:MyList[A]):MyList[A]={
            if(paramList.isEmpty)myList
            else helper(paramList.tail, myList.add(paramList.head))
        }
        helper(elements, Empty)
      }
    }
  case object Empty extends MyList[Nothing] {
    override def head: Nothing = throw new NoSuchElementException
    override def tail: MyList[Nothing] = throw new NoSuchElementException
    override def add[B >: Nothing](element: B): MyList[B] = nList(element,Empty)
    override def map[B](f: Nothing => B): MyList[B] = Empty
    override def flatMap[B](f: Nothing => MyList[B]): MyList[B] = Empty
    override def filter(p: Nothing => Boolean): MyList[Nothing] = Empty
    override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
    override def prepend[B >: Nothing](element: B): MyList[B] = nList(element,Empty)
    override def reverse: MyList[Nothing] = Empty

    override def last: Nothing = throw new NoSuchElementException
  }
  case class nList[A] (h: A, t:MyList[A]) extends MyList[A] {
    override def head: A = h
    override def tail: MyList[A] = t
    override def add[B >: A](element: B): MyList[B] = nList(element,this)
    override def map[B](f: A => B): MyList[B] = nList(f(h), t.map(f))
    override def flatMap[B](f: A => MyList[B]): MyList[B] = f(h) ++ t.flatMap(f)
    override def filter(p: A => Boolean): MyList[A] = {
      if(p(h)) nList(h, t.filter(p))
      else t.filter(p)
    }
    override def ++[B >: A](list: MyList[B]): MyList[B] = nList(h, t++list)
    override def prepend[B >: A](element: B): MyList[B] = nList(h, t.prepend(element))
    override def reverse: MyList[A] = t.reverse.prepend(h)

    override def last: A = {
      if(t==Empty) h
      else t.last
    }
  }



  println(nList(1,nList(2,nList(3,nList(4,Empty)))))
  val list = MyList(1,2,3,4).reverse
  println(list.flatMap(x=>MyList(x*10))) // потому что есть apply

  List(1,2,3,4).last


}


