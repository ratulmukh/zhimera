package ratul.myexperiments.cluster;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import akka.actor.Props;

import ratul.myexperiments.cluster.ClusterMsgs.*;
import akka.actor.*;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import akka.dispatch.OnSuccess;
import akka.util.Timeout;
import static akka.pattern.Patterns.ask;
import java.util.concurrent.TimeUnit;


public class ClusterMgr {

  public static void bringUpNode(String[] args) {
    // Override the configuration of the port when specified as program argument
    final String port = args.length > 0 ? args[0] : "0";
    final Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
      withFallback(ConfigFactory.parseString("akka.cluster.roles = [video-content-storage-node]")).
      withFallback(ConfigFactory.load());

    ActorSystem system = ActorSystem.create("ClusterSystem", config);

    final ActorRef node = system.actorOf(Props.create(VideoContentStorageNode.class), "video-content-storage-node");
    final ActorRef client = system.actorOf(Props.create(VideoStorageClient.class), "video-storage-client");
    
    
  final FiniteDuration interval = Duration.create(2, TimeUnit.SECONDS);
    final Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
    final ExecutionContext ec = system.dispatcher();
    
    ask(client, new ReferenceToNodeActor(node), timeout).onSuccess(new OnSuccess<Object>() {
          public void onSuccess(Object result) {
            System.out.println("Response received");
          }
        }, ec);

  }
  
  public static void main(String[] args) {
    // starting 2 frontend nodes and 3 backend nodes
    bringUpNode(new String[] { "2551" });
    bringUpNode(new String[] { "2552" });
    bringUpNode(new String[] { "2553" });
    bringUpNode(new String[] { "2554" });
    
  }

}