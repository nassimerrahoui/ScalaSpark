package TP1

import scala.collection.mutable.Map

object exo1 extends App{
  
	class Catalogue{
		var nom_prix = Map[String,Double]()
		
		def addProduit(prod : String,prix : Double){
		  nom_prix = nom_prix + (prod -> prix)
		}
		
		def removeProduit (prod : String){
		  nom_prix = nom_prix - (prod)
		  
		}
			
		override def toString : String  = {
		  var sb : StringBuffer = new StringBuffer
		  
		  for((nom,prix) <- nom_prix){
		    sb.append(nom+" : "+prix+" euros\n")
		  }
		  return sb.toString();
		}
  }
	
	val catalogue  = new Catalogue
	catalogue.addProduit("peluche",10.5)
	catalogue.addProduit("tondeuse",250.6)
	catalogue.addProduit("table",90)
	catalogue.addProduit("saladier",20)
	catalogue.addProduit("casserole",35)
	println(catalogue)
}