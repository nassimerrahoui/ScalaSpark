package util;

import static util.Constantes.host;
import static util.Constantes.nodeids;
import static util.Constantes.timeout;
import static util.Constantes.vide;
import java.io.IOException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

// permet d’écrire directement le nom des constantes
// sans les prefixer par le nom de la classe

public abstract class Client implements Runnable, Watcher {

	private final String id;
	private final ZooKeeper zk;

	public Client() throws IOException, KeeperException, InterruptedException {
		zk = new ZooKeeper(host, timeout, this);

		if (zk.exists(nodeids, false) == null) {
			zk.create(nodeids, vide, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}

		String[] tmp = zk.create(nodeids + "/id", vide, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL)
				.split("/");
		id = tmp[tmp.length - 1];
		System.err.println("Starting node " + id);
	}

	public String id() { return id; }

	public ZooKeeper zk() { return zk; }

}
