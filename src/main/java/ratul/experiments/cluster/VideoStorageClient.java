package ratul.myexperiments.cluster;

import ratul.myexperiments.cluster.ClusterMsgs.*;
import akka.actor.*;
import scala.concurrent.duration.Duration;
import java.util.concurrent.TimeUnit;


public class VideoStorageClient extends UntypedActor {

  ActorRef p = getContext().actorSelection("/user/video-storage-coordinator-proxy").resolveOne();
  Cancellable cancellable = getContext().system().scheduler().schedule(Duration.Zero(),
    Duration.create(1000, TimeUnit.MILLISECONDS), p, 
    new Hello(), getContext().system().dispatcher(), null);



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
