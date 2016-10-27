package ratul.myexperiments.cluster;

import ratul.myexperiments.cluster.ClusterMsgs.*;
import akka.actor.*;
import scala.concurrent.duration.Duration;
import java.util.concurrent.TimeUnit;


public class VideoStorageClient extends UntypedActor {




  @Override
  public void onReceive(Object message) {
    if (message instanceof Hello) {
      Hello job = (Hello) message;
      System.out.println("Hello msg received");
      getSender().tell(new HelloResult(), getSelf());
      //getContext().actorSelection("/user/video-storage-coordinator-proxy").tell(new HelloResult(), getSelf());
    }
    else if (message instanceof ReferenceToNodeActor) {
      ReferenceToNodeActor referenceToNodeActor = (ReferenceToNodeActor) message;
      System.out.println("ReferenceToNodeActor msg received");
      getSender().tell(new HelloResult(), getSelf());
      referenceToNodeActor.node.tell(new Hello(), getSelf());
      //getContext().actorSelection("/user/video-storage-coordinator-proxy").tell(new HelloResult(), getSelf());
    }
    else {
      unhandled(message);
    }
  }


}
