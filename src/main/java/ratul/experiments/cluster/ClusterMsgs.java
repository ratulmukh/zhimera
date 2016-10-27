package ratul.myexperiments.cluster;

import java.io.Serializable;
import ratul.myexperiments.videomodel.*;
import java.util.UUID;
import java.util.List;
import akka.actor.ActorRef;


public interface ClusterMsgs {
    
    public static class Hello implements Serializable {
        
    };
    
    public static class ReferenceToNodeActor implements Serializable {
        public final ActorRef node;
        public ReferenceToNodeActor(ActorRef node) {
            this.node = node;
        }
    };
    
    
    public static class HelloResult implements Serializable {
        
    };
    
    public static class VideoStorageRequest implements Serializable {
        
    };
    
    public static class VideoStorageReserved implements Serializable {
        public final UUID videoId;
        public final List<ActorRef> videoStorageNodes;
        
        public VideoStorageReserved(UUID videoId, List<ActorRef> videoStorageNodes) {
            this.videoId = videoId;
            this.videoStorageNodes = videoStorageNodes;
        }
    };
    
    public static class StoreVideoChunk implements Serializable {
        public final UUID videoId;
        public final VideoChunk videoChunk;
        
        public StoreVideoChunk(UUID videoId, VideoChunk videoChunk) {
            this.videoId = videoId;
            this.videoChunk = videoChunk;
        }
    };
    
    
    
    public static String StringMsg = "StringMsg";
}