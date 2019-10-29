package TP2

object exo3 extends App {
  def nbLetters(s:List[String])={
    var list_mot =s.flatMap(a=>a.split(" "))
    var list_int = list_mot.map(mot => mot.length())
    list_int.reduce((x,y) => x + y)
    
  }
  println(nbLetters(List( "Hadoop est une plateforme distribuee" , 
      "Spark en est une autre" ,"scala est un langage", "Java aussi" )))
}