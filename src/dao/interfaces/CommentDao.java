package dao.interfaces;

import domain.Comment;

import java.util.List;

public interface CommentDao {
    List<Comment> getAll(int mid);
    
    boolean validate(int cid, int mid);
    
    void comment(Comment comment);
}
