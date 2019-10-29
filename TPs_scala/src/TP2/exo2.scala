package TP2

object exo2 extends App {
  
  def premiers(n: Int) = {
    var premiers = List.range(2, n)
    for(a <- 2 to n){
      premiers = premiers.filter(i => i%a != 0 || i==a)
    }
    premiers //on renvoie l'élément resultantt
  }
  
  def premiersRec(l: List[Int]) : List[Int] = {
    if(l(0) * l(0) > l.last) {
      l
    } else {
      l(0)::(premiersRec(l.filter(i => i%l(0) != 0)))
    }
  }

  println(premiersRec(List.range(2,20)))
}