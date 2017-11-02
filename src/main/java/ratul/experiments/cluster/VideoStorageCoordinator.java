package ratul.experiments.cluster;

import ratul.experiments.cluster.ClusterMsgs.*;
import akka.actor.*;
import java.util.*;
import akka.cluster.*;
import akka.cluster.ClusterEvent.*;
import akka.event.Logging;
import akka.event.Logging.*;
import akka.event.LoggingAdapter;

public class VideoStorageCoordinator extends UntypedActor {

  Cluster cluster = Cluster.get(getContext().system());
  Map<String, Member> backends = new HashMap<String, Member>();
  
  LoggingAdapter log = Logging.getLogger(getContext().system(), this);



  //subscribe to cluster changes, MemberUp
  @Override
  public void preStart() {
    cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(), MemberEvent.class, UnreachableMember.class);
        
  }

  //re-subscribe when restart
  @Override
  public void postStop() {
    cluster.unsubscribe(getSelf());
  }

  @Override
  public void onReceive(Object message) {
    
    if (message instanceof MemberUp) {
      MemberUp mUp = (MemberUp) message;
      backends.put(mUp.member().address().toString(), mUp.member());
      log.info("Member is added: {}", mUp.member());
  
    } else if (message instanceof UnreachableMember) {
      UnreachableMember mUnreachable = (UnreachableMember) message;
      backends.remove(mUnreachable.member().address().toString());
      log.info("Member detected as unreachable: {}", mUnreachable.member());
 
    } else if (message instanceof MemberRemoved) {
      MemberRemoved mRemoved = (MemberRemoved) message;
      backends.remove(mRemoved.member().address().toString());
      log.info("Member is removed: {}", mRemoved.member());
 
    } else if (message instanceof MemberEvent) {
      // ignore
 
    } else if (message instanceof Terminated) {
      Terminated terminated = (Terminated) message;
      backends.remove(terminated.getActor());
 
    } else if (message instanceof CurrentClusterState) {
      CurrentClusterState state = (CurrentClusterState) message;
      for (Member member : state.getMembers()) {
        if (member.status().equals(MemberStatus.up())) {
          backends.put(member.address().toString(), member);
        }
      }
        
    } else if(message instanceof VideoStorageRequest) {
      System.out.println("VideoStorageRequest msg received by coordinator");
      String nodePath1 = (backends.keySet().toArray())[0] + "/user/video-content-storage-node";
      String nodePath2 = (backends.keySet().toArray())[1] + "/user/video-content-storage-node";
      ActorSelection node1 = getContext().actorSelection(nodePath1);
      ActorSelection node2 = getContext().actorSelection(nodePath2);
      System.out.println("Selected storage nodes are: " + nodePath1 + " and " + nodePath2);
      sender().tell(new VideoStorageDestination(new ArrayList<ActorSelection>(Arrays.asList(node1, node2))), getSelf());

      
    } else {
      unhandled(message);
    }
 
  }



}