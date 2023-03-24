package kr.cafeIn.cafeorder.repository;

import kr.cafeIn.cafeorder.model.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor //생성자 자동 주입
public class JdbcUserRepository implements  UserRepository {
    private final DataSource dataSource;


    @Override
    public User save(User users) {
        String sql = "insert into users(email,password,nickname,grade,point,create_at,update_at) values (?,?,?,?,?,sysdate(),sysdate())";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            /* conn.setAutoCommit(true);*///트랜잭션시작@
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, users.getEmail());
            pstmt.setString(2, users.getPassword());
            pstmt.setString(3, users.getNickname());
            pstmt.setInt(4, users.getGrade());
            pstmt.setInt(5, users.getPoint());

            pstmt.executeUpdate();
            return users;
        } catch (Exception e) {
            // conn.rollback();//실패시 롤백@*//*
            log.error("caught exception", e);
            throw new IllegalStateException(e);

        } finally {
            close(conn, pstmt, null);
        }
    }

    //조회
    public Optional<User> findByEmail(String email) {

        String sql = "select * from users where email = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                User users = new User();
                users.setEmail(rs.getString("email"));
                return Optional.of(users);
            } else {//데이터가 없다
                return Optional.empty();
            }


        } catch (SQLException e) {
            throw new IllegalStateException(e);

        } finally {
            close(con, pstmt, rs);
        }

    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}