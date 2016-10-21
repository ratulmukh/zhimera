package ratul.myexperiments.videomodel;

class Comment {
    private final String commentContent;
    private final User userWhoCommented;
    private final Video commentedOnVideo;
    private final Comment parentComment;
    private int upVoteCount = 0;
    private int downVoteCount = 0;
    
    public Comment(String commentContent, User userWhoCommented, Video commentedOnVideo, Comment parentComment) {
        this.commentContent = commentContent;
        this.userWhoCommented = userWhoCommented;
        this.commentedOnVideo = commentedOnVideo;
        this.parentComment = parentComment;
    }
    
    public void incrementUpVoteCount() {
        upVoteCount++;
    }
    
    public void decrementUpVoteCount() {
        upVoteCount--;
    }
    
    public void incrementDownVoteCount() {
        downVoteCount++;
    } 
    
    public void decrementDownVoteCount() {
        downVoteCount--;
    } 
}