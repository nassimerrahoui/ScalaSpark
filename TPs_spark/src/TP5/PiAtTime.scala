package TP5

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds

object PiAtTime extends App{
    val conf = new SparkConf().setAppName("Custom Pi").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(10))
    val lines = ssc.socketTextStream("127.0.0.1", 4242)
    
    val points = lines.map(l => l.split(" ")).map((tab => (tab(0).toDouble,tab(1).toDouble)))
    val lst = points.map(p => { if((p._1 + p._2) * (p._1 + p._2) < 0.5) (1,1) else (0,1) })
    val res = lst.reduce({case (c1,c2) => (c1._1+c2._1, c1._2+c2._2)})
    val pi = res.map(c => (c._1.toDouble/c._2.toDouble)*4.0)
    pi.print()
    
    ssc.start()
    ssc.awaitTermination()
}