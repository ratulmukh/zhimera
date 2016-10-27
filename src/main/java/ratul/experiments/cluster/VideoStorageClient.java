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
      getSender().tell(new HelloResult(), getSelf());
      //getContext().actorSelection("/user/video-storage-coordinator-proxy").tell(new HelloResult(), getSelf());
    }
    else {
      unhandled(message);
    }
  }


}
