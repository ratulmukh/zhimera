package ratul.myexperiments.cluster;

import ratul.myexperiments.cluster.ClusterMsgs.*;

import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.CurrentClusterState;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.Member;
import akka.cluster.MemberStatus;


public class VideoContentStorageNode extends UntypedActor {

  Cluster cluster = Cluster.get(getContext().system());

  //subscribe to cluster changes, MemberUp
  @Override
  public void preStart() {
    cluster.subscribe(getSelf(), MemberUp.class);
  }

  //re-subscribe when restart
  @Override
  public void postStop() {
    cluster.unsubscribe(getSelf());
  }

  @Override
  public void onReceive(Object message) {
    if (message instanceof Hello) {
      Hello job = (Hello) message;
      getSender().tell(new HelloResult(), getSelf());
    }
    else {
      unhandled(message);
    }
  }

  
}