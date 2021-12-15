package com.llw.run.http.res;

import com.llw.run.http.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class FriendRes extends BaseResponse implements Serializable {

    /**
     * username : fzt
     * issueTime : 2021-12-13 20:29:41
     * issue : 今天吃了没
     * commentNumber : 3
     * likeNumber : 1
     * commentWithDynamics : [{"username":"lyy","content":"nb","commentTime":"2021-12-13 20:37:32"},{"username":"fzt","content":"wsl","commentTime":"2021-12-12 20:53:41"},{"username":"fzt","content":"wsl","commentTime":"2021-12-11 21:13:48"}]
     */

    private String username;
    private String issueTime;
    private String issue;
    private String did;
    private int commentNumber;
    private int likeNumber;
    private List<CommentWithDynamicsEntity> commentWithDynamics;
    private List<String> pics;

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public List<CommentWithDynamicsEntity> getCommentWithDynamics() {
        return commentWithDynamics;
    }

    public void setCommentWithDynamics(List<CommentWithDynamicsEntity> commentWithDynamics) {
        this.commentWithDynamics = commentWithDynamics;
    }

    public static class CommentWithDynamicsEntity implements Serializable {
        /**
         * username : lyy
         * content : nb
         * commentTime : 2021-12-13 20:37:32
         */

        private String username;
        private String content;
        private String commentTime;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
        }
    }
}
