package TP5

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds

object TowardsPi extends App{
    val conf = new SparkConf().setAppName("TowardsPi").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(1))
    val lines = ssc.socketTextStream("127.0.0.1", 4242)
    
    val points = lines.map(l => l.split(" ")).map((tab => (tab(0).toDouble,tab(1).toDouble)))
    val lst = points.map(p => { if((p._1 * p._1) + (p._2 * p._2) < 1) (1,1) else (0,1) })
    val res = lst.reduce({case (c1,c2) => (c1._1+c2._1, c1._2+c2._2)})
    val pi = res.map(c => (c._1.toDouble/c._2.toDouble)*4.0).map(c => (0,c))
    val cumulPi = pi.updateStateByKey((currPi, state : Option[Double]) => state match {
      case None => if(currPi.length == 0) Some(0.0) else Some(currPi.reduce(_+_).toDouble) 
      case Some(n) => if(currPi.length == 0) Some(n.toDouble) else Some(((n + currPi.reduce(_ +_))/2.0).toDouble)
    })
    cumulPi.print()
    ssc.checkpoint("src/TP5");
    ssc.start()
    ssc.awaitTermination()
}