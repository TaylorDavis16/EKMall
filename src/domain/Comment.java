package domain;

import java.io.Serializable;

public class Comment implements Serializable {
    private int cid;
    private String username;
    private int rate;
    private int mid;
    private String words;
    private String leaveTime;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "cid=" + cid +
                ", username='" + username + '\'' +
                ", rate=" + rate +
                ", mid=" + mid +
                ", words='" + words + '\'' +
                ", leaveTime='" + leaveTime + '\'' +
                '}';
    }
}
