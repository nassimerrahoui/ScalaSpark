package TP4

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object StereoPrix extends App {
  val conf = new SparkConf().setAppName("Stereo Prix").setMaster("local[*]")
  val spark = new SparkContext(conf)
  
  val rdd1 = spark.textFile("stereoprix_1fichier_de_1ko/stereoprix_fichier_1", 1)
  val rdd2 = rdd1.map(line => line.split(" "))
  val rdd3 = rdd2.filter(line => line(0).split("_")(2).toInt > 2013 
                        && line(0).split("_")(2).toInt < 2019)
  val rdd4 = rdd3.map(line => (1,line(2).toInt)).reduceByKey(_+_)
  rdd4.saveAsTextFile("stereoprix_1fichier_de_1ko/out1.txt")
}