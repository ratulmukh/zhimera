package ratul.experiments.videomodel;

import java.util.List;

class Video {
    private final VideoContent videoContent;
    private final List<String> tagList;
    private final List<Comment> commentList;
    
    public Video(VideoContent videoContent, List<String> tagList, List<Comment> commentList) {
        this.videoContent = videoContent;
        this.tagList = tagList;
        this.commentList = commentList;
    } 
}