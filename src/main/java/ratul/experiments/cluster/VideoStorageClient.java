package ratul.myexperiments.cluster;

import ratul.myexperiments.cluster.ClusterMsgs.*;
import akka.actor.*;
import scala.concurrent.duration.Duration;
import java.util.concurrent.TimeUnit;


public class VideoStorageClient extends UntypedActor {

  ActorRef referenceToNodeActor;


  @Override
  public void onReceive(Object message) {
    if (message instanceof ReferenceToNodeActor) {
      referenceToNodeActor = ((ReferenceToNodeActor) message).node;
      System.out.println("ReferenceToNodeActor msg received");
      getContext().system().scheduler().scheduleOnce(Duration.create(15, TimeUnit.SECONDS),
        getSelf(), new VideoStorageRequest(), getContext().system().dispatcher(), null);
    } else if (message instanceof VideoStorageRequest) {
      referenceToNodeActor.tell(message, getSelf());

    } else {
      unhandled(message);
    }
  }


}
