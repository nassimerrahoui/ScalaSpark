package exercices_hbase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class LastFM {
	
	public static void addData() throws IOException {

		Configuration conf = HBaseConfiguration.create();
		conf.set( "hbase.zookeeper.quorum","server1.com","server2.fr" );
		Connection c = ConnectionFactory.createConnection();
		Admin admin = c.getAdmin();
		if(!admin.tableExists(TableName.valueOf("ecoute"))) {
			System.out.println("table créée.");
			HTableDescriptor desc = new HTableDescriptor(TableName.valueOf("default:ecoute"));
			desc.addFamily(new HColumnDescriptor("cf1"));
			admin.createTable(desc);			
		}
		TableName tableName = TableName.valueOf("ecoute");
		Table ecoute = c.getTable(tableName);
		
		BufferedReader br = new BufferedReader(new FileReader("lastfm_1fichier_de_1ko/lastfm_fichier_1"));
		String line;
		while ((line = br.readLine()) != null) {
			
			String user_id = line.split("\\s+")[0];
			String track_id = line.split("\\s+")[1];
			Long LocalListening = Long.valueOf(line.split("\\s+")[2]);
			Long RadioListening = Long.valueOf(line.split("\\s+")[3]);
			Long Skip = Long.valueOf(line.split("\\s+")[4]);
			
			Get get = new Get(Bytes.toBytes(user_id+track_id));
			Result res = ecoute.get(get);

			if(res.isEmpty()) {
				Put put = new Put(Bytes.toBytes(user_id+track_id));
				put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("UserId"), Bytes.toBytes(user_id));
				put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("TrackId"), Bytes.toBytes(track_id));

				ecoute.put(put);
			}
			Increment increment = new Increment(Bytes.toBytes(user_id+track_id));
			increment.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("LocalListening"), LocalListening);
			increment.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("RadioListening"), RadioListening);
			increment.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("Skip"), Skip);
			ecoute.increment(increment);
		}
		
		br.close();
	}
	
	public static void main(String[] args) {
		try { LastFM.addData(); } 
		catch (IOException e) { e.printStackTrace(); }
	}
}
