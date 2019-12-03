package exercices_hbase;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class Ventes {
	
	public static void nbVentes() throws IOException {
		Configuration conf = HBaseConfiguration.create();
		conf.set( "hbase.zookeeper.quorum","server1.com","server2.fr" );
		Connection c = ConnectionFactory.createConnection();
		
//		TableName tableName1 = TableName.valueOf("categorie");
//		TableName tableName2 = TableName.valueOf("client");
//		TableName tableName3 = TableName.valueOf("magasin");
		TableName tableName4 = TableName.valueOf("produit");
		TableName tableName5 = TableName.valueOf("vente");
		
//		Table categorie = c.getTable(tableName1);
//		Table client = c.getTable(tableName2);
//		Table magasin = c.getTable(tableName3);
		Table produit = c.getTable(tableName4);
		Table vente = c.getTable(tableName5);
		
		
		Scan scan = new Scan();
		scan.addColumn(Bytes.toBytes("defaultcf"), Bytes.toBytes("idprod"));
		scan.addColumn(Bytes.toBytes("defaultcf"), Bytes.toBytes("categorie"));
		ResultScanner produits = produit.getScanner(scan);
		HashMap<String,String> produit_categorie = new HashMap<>();
		
		for (Result result : produits) {
			byte[] prod = result.getValue(Bytes.toBytes("defaultcf"), Bytes.toBytes("idprod"));
			byte[] cat = result.getValue(Bytes.toBytes("defaultcf"), Bytes.toBytes("categorie"));
			if (Bytes.toString(prod) ==  "idprodproduit99") {
				System.out.println(Bytes.toString(prod));
				System.out.println("true");
			}
			produit_categorie.put(Bytes.toString(prod), Bytes.toString(cat));
		}
		
		scan.addColumn(Bytes.toBytes("defaultcf"), Bytes.toBytes("produit"));
		ResultScanner ventes = vente.getScanner(scan);
		HashMap<String,Integer> categorie_ventes = new HashMap<>();
		
		for (Result result : ventes) {
			byte[] p = result.getValue(Bytes.toBytes("defaultcf"), Bytes.toBytes("produit"));
			if (Bytes.toString(p) ==  "idprodproduit99") {
				System.out.println(Bytes.toString(p));
				System.out.println("true");
			}else
			{
				System.out.println(Bytes.toString(p).charAt(0));
			}
			String cat = produit_categorie.get(Bytes.toString(p));
			
			if(!categorie_ventes.containsKey(cat)) {
				categorie_ventes.put(cat, 0);
			}
			
			categorie_ventes.put(cat, categorie_ventes.get(cat) + 1);
		}
		
		for (String k : categorie_ventes.keySet()) {
			System.out.println(k + " : " + categorie_ventes.get(k));
		}
		
	}
	
	public static void main(String[] args) {
		try { nbVentes(); } 
		catch (IOException e) { e.printStackTrace(); }
	}
}
