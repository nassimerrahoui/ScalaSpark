package TP4

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object StereoPrix2 extends App {
  val conf = new SparkConf().setAppName("Stereo Prix").setMaster("local[*]")
  val spark = new SparkContext(conf)
  
  val rdd1 = spark.textFile("stereoprix_1fichier_de_1ko/stereoprix_fichier_1", 1)
  val rdd2 = rdd1.map(line => line.split(" "))
  val rdd3 = rdd2.map(line => (line(4),line(2).toInt)).reduceByKey(_+_) 
  rdd3.saveAsTextFile("stereoprix_1fichier_de_1ko/out2.txt")
}