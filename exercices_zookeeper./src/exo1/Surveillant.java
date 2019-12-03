package exo1;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;

import util.Client;
import util.Constantes;

public class Surveillant extends Client {

	public Surveillant() throws IOException, KeeperException, InterruptedException {
		super();
		
	}

	@Override
	public void run() {

//		try {System.out.println(super.zk().getChildren(Constantes.nodeids, true));}
//		catch (KeeperException e) {e.printStackTrace();} 
//		catch (InterruptedException e) {e.printStackTrace();}
//		
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
            synchronized (this) {
                while (true) {
                	try {System.out.println(super.zk().getChildren(Constantes.nodeids, true));}
            		catch (KeeperException e) {e.printStackTrace();} 
            		catch (InterruptedException e) {e.printStackTrace();}
                    wait();
                }
            }
        } catch (InterruptedException e) {
        }
		
	}

	@Override
	public void process(WatchedEvent arg0) {
		System.out.println("oui");
		System.out.println(arg0.getType());
		
			if(arg0.getType() == EventType.NodeChildrenChanged) {
				System.out.println("hello");
				try { System.out.println(super.zk().getChildren(Constantes.nodeids, true));} 
				catch (KeeperException | InterruptedException e) { e.printStackTrace(); }
			}
		}
	
	
	public static void main(String[] args) {
		
		int n = 5;//nombre de clients;
		for(int i=0;i<n;i++)
			try {
				new Thread(new Surveillant()).start();
			} catch (IOException | KeeperException | InterruptedException e) {
				e.printStackTrace();
			}
	}
}
