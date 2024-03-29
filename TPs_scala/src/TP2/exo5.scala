package TP2

object exo5 extends App {
  def isSorted[A](as: Array[A], ordered: (A,A) => Boolean):Boolean={
    for(a <- 0 to as.length-2 ){
      if(!ordered(as(a),as(a+1))) 
          return false
    }
    return true
  }
  
  val ascending = (x: Int, y : Int) => x<=y
  val descending = (x: Int, y : Int) => x>=y
  val ar1 = Array(3,4,5,6,10)
  val ar2 = Array(13,4,6,7,49)
  
  assert(isSorted(ar1, ascending))
  assert(!isSorted(ar1, descending))
  assert(!isSorted(ar1.reverse, ascending))
  assert(isSorted(ar1.reverse, descending))
  
  assert(!isSorted(ar2, ascending))
  assert(!isSorted(ar2, descending))
  assert(!isSorted(ar2.reverse, ascending))
  assert(!isSorted(ar2.reverse, descending))
  
}