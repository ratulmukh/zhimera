package ratul.myexperiments.cluster;

import ratul.myexperiments.cluster.ClusterMsgs.*;
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

    } else {
      unhandled(message);
    }
 
  }



}