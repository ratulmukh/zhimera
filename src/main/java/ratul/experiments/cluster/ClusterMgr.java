package ratul.myexperiments.cluster;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import akka.actor.Props;

public class ClusterMgr {

  public static void bringUPNode(String[] args) {
    // Override the configuration of the port when specified as program argument
    final String port = args.length > 0 ? args[0] : "0";
    final Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
      withFallback(ConfigFactory.parseString("akka.cluster.roles = [video-content-storage-node]")).
      withFallback(ConfigFactory.load());

    ActorSystem system = ActorSystem.create("ClusterSystem", config);

    system.actorOf(Props.create(VideoContentStorageNode.class), "video-content-storage-node");

  }
  
  public static void main(String[] args) {
    // starting 2 frontend nodes and 3 backend nodes
    bringUPNode(new String[] { "2551" });
    bringUPNode(new String[] { "2552" });
    bringUPNode(new String[] { "2553" });
    bringUPNode(new String[] { "2554" });
    
  }

}