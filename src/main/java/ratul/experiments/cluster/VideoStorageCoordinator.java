package ratul.myexperiments.cluster;

import ratul.myexperiments.cluster.ClusterMsgs.*;
import akka.actor.UntypedActor;


public class VideoStorageCoordinator extends UntypedActor {


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