package dao.impl;

import dao.interfaces.CommentDao;
import domain.Comment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.Date;
import java.util.List;

public class CommentDaoImpl implements CommentDao {
    private JdbcTemplate template= JDBCUtils.getTemplate();
    @Override
    public List<Comment> getAll(int mid) {
        String sql = "SELECT c.username, m.* FROM message m INNER JOIN customer c ON c.cusID=m.cid WHERE MID = ?";
        try {
            return template.query(sql, new BeanPropertyRowMapper<>(Comment.class), mid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean validate(int cid, int mid) {
        String sql="SELECT COUNT(*) FROM orders WHERE customer_cusID=? AND merchandise_mID=?;";
        try {
            Integer integer = template.queryForObject(sql, Integer.class, cid, mid);
            return integer!=null && integer!=0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void comment(Comment comment) {
        String sql="INSERT INTO message VALUES(?,?,?,?,?)";
        try {
            template.update(sql,comment.getCid(),comment.getRate(),comment.getMid(),comment.getWords(),new Date());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
