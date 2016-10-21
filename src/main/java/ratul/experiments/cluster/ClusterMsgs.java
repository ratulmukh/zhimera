package ratul.myexperiments.cluster;

import java.io.Serializable;

public interface ClusterMsgs {
    
    public static class Hello implements Serializable {
        
    };
    
    public static class HelloResult implements Serializable {
        
    };
    
    public static String StringMsg = "StringMsg";
}