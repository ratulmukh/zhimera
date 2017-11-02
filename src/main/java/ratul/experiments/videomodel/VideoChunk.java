package ratul.experiments.videomodel;

public class VideoChunk {
    private int seqNo;
    byte[] data;
    
    public VideoChunk(int seqNo, byte[] data) {
        this.seqNo = seqNo;
        this.data = data;
    }
    
}
