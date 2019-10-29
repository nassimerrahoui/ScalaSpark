package TP2

object exo4 extends App {
  
  def moyenne(notes : List[(Double,Int)]) {
     var list_tuple = notes.map{case (note : Double,coeff : Int) => (note*coeff,coeff)}
     println(list_tuple)
     var tuple_reduced = list_tuple.reduce( (x,y) => (x._1 +y._1 ,x._2+y._2)) 
     println(tuple_reduced._1 / tuple_reduced._2)
     
  }
  moyenne(List((10,3),(12,1)))
}