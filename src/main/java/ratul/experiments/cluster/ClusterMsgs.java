package ratul.experiments.cluster;

import java.io.Serializable;
import ratul.experiments.videomodel.*;
import java.util.UUID;
import java.util.List;
import akka.actor.*;


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
    
    public static class VideoStorageDestination implements Serializable {
        public final UUID videoId;
        public final List<ActorSelection> videoStorageNodes;
        
        public VideoStorageDestination(List<ActorSelection> videoStorageNodes) {
            this.videoId = UUID.randomUUID();
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