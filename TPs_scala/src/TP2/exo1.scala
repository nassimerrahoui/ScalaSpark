package TP2

//import scala.collection.Map

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
  
		def soldefor(percent:Int){
		  for((nom,prix) <- nom_prix){
		   //nom_prix.put(nom, prix - prix * percent/100)
		  }
		}
		
		def soldeFNomme(percent:Int) =  {
		  nom_prix.mapValues(diminution(_,percent))
		}
		
		def soldeFAno(percent:Int) =  {
		  nom_prix.mapValues(a => a * ((100.0 - percent) / 100.0))
		}
		
		def diminution(a:Double, percent:Int): Double = a * ((100.0 - percent) / 100.0)
	}
	
	
	
	val catalogue  = new Catalogue
	catalogue.addProduit("peluche",10.5)
	catalogue.addProduit("tondeuse",250.6)
	catalogue.addProduit("table",90)
	catalogue.addProduit("saladier",20)
	catalogue.addProduit("casserole",35)
	println(catalogue)
	//catalogue.soldefor(50)
	//catalogue.nom_prix=catalogue.soldeFNomme(50)
	catalogue.nom_prix=catalogue.soldeFAno(50)
	println(catalogue)
}