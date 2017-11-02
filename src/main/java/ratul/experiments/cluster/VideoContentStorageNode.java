package ratul.experiments.cluster;

import ratul.experiments.cluster.ClusterMsgs.*;

import akka.actor.*;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.CurrentClusterState;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.Member;
import akka.cluster.MemberStatus;
import akka.cluster.singleton.ClusterSingletonManager;
import akka.cluster.singleton.ClusterSingletonManagerSettings;
import akka.cluster.singleton.ClusterSingletonProxy;
import akka.cluster.singleton.ClusterSingletonProxySettings;
import akka.actor.Props;
import akka.actor.PoisonPill;

public class VideoContentStorageNode extends UntypedActor {

  Cluster cluster = Cluster.get(getContext().system());
  
  final ClusterSingletonManagerSettings settings =
  ClusterSingletonManagerSettings.create(getContext().system()).withRole("video-content-storage-node");
  
  ClusterSingletonProxySettings proxySettings =
    ClusterSingletonProxySettings.create(getContext().system()).withRole("video-content-storage-node");

  ActorRef videoStorageCoordinator;


  //subscribe to cluster changes, MemberUp
  @Override
  public void preStart() {
    cluster.subscribe(getSelf(), MemberUp.class);
    
    context().system().actorOf(ClusterSingletonManager.props(
      Props.create(VideoStorageCoordinator.class), new End(), settings), "video-storage-coordinator");
      
      videoStorageCoordinator = context().system().actorOf(ClusterSingletonProxy.props("/user/video-storage-coordinator", proxySettings), "video-storage-coordinator-proxy");
  }
  
  public static class End {
  }

  //re-subscribe when restart
  @Override
  public void postStop() {
    cluster.unsubscribe(getSelf());
  }

  @Override
  public void onReceive(Object message) {
    if(message instanceof VideoStorageRequest) {
      System.out.println("VideoStorageRequest received and forwarded to coordinator");
      videoStorageCoordinator.forward(message, getContext());
    }else {
      unhandled(message);
    }
  }

  
}