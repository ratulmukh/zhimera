package ratul.myexperiments.videomodel;

public class Chunk {
    private int seqNo;
    byte[] data;
    
    public Chunk(int seqNo, byte[] data) {
        this.seqNo = seqNo;
        this.data = data;
    }
    
}
