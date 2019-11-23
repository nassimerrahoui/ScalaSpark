//package TP5
//import org.apache.spark.SparkConf
//import org.apache.spark.SparkContext
//import org.apache.spark.streaming.StreamingContext
//import org.apache.spark.streaming.Seconds
//import org.apache.log4j.Level
//import scala.concurrent.duration.Duration
//
//object TwitGenerator extends App {
//    org.apache.log4j.Logger.getLogger("org.apache.spark").setLevel(Level.FATAL)
//    val conf = new SparkConf().setAppName("Custom Touitte").setMaster("local[*]")
//    val sc = new SparkContext(conf)
//    val ssc = new StreamingContext(sc, Seconds(1))
//    val lines = ssc.socketTextStream("127.0.0.1", 4242)
//    
//    val toptentwits = lines.flatMap(_.split(" "))
//                          .filter(s => s.contains("#"))
//                          .map(t => (t,1))
//                          .reduceByKeyAndWindow((_+_) , Seconds(20), Seconds(5))
//                          .transform(rdd =>{rdd.sortBy(_._2, false)})
//
//    
//    //val tcs = pair_twits.reduceByKey(_ + _)
//    /*val topCounts10 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(10), Seconds(5))
//                     .map{case (topic, count) => (count, topic)}
//                     .transform(_.sortByKey(false))
//    */
//    /*val cumul_tcs = tcs.updateStateByKey((tcs, state : Option[Int]) => state match {
//      case None => if(tcs.length == 0) Some(0) else Some(tcs.reduce(_+_)) 
//      case Some(n) => if(tcs.length == 0) Some(n) else Some(n + tcs.reduce(_ +_))
//    })*/
//    
//    
//    ssc.checkpoint("src/TP5/Twit");
//    toptentwits.print()
//    
//    ssc.start()
//    ssc.awaitTermination()
//}