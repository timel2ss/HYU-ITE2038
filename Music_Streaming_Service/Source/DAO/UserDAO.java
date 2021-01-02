package DAO;

import Entity.User;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Calendar;
import java.util.Properties;

public class UserDAO {
    private final Properties properties = new Properties();

    private Connection getConnection() {
        try {
            String resource = "src/config/DB.properties";
            FileInputStream fi = new FileInputStream(resource);
            properties.load(fi);
            String driver = properties.getProperty("driver");
            String url = properties.getProperty("url");
            String dbID = properties.getProperty("username");
            String dbPW = properties.getProperty("password");
            Class.forName(driver).newInstance();
            return DriverManager.getConnection(url, dbID, dbPW);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if(rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(pstmt != null) {
                pstmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getInfo(int index) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "SELECT * FROM user WHERE U_Index = ? AND deleted = false";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, index);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                int userIndex = rs.getInt(1);
                String ID = rs.getString(2);
                String PW = rs.getString(3);
                String name = rs.getString(4);
                String SSN = rs.getString(5);
                String address = rs.getString(6);
                String phoneNumber = rs.getString(7);
                String email = rs.getString(8);
                boolean subscription = rs.getBoolean(9);
                String expirationDate = rs.getString(10);
                boolean autoSubscription = rs.getBoolean(11);
                int listCount = rs.getInt(12);
                return new User(userIndex, ID, PW, name, SSN, address, phoneNumber, email, subscription, expirationDate, autoSubscription, listCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }
    
    public int login(String ID, String PW) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "SELECT U_Index, U_PW FROM user WHERE U_ID = ? AND deleted = false";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, ID);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                if(rs.getString(2).equals(PW)) {
                    return rs.getInt(1); // login success
                }
                else {
                    return 0; // wrong PW
                }
            }
            return -2; // no ID
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1; // error
    }

    public int register(String ID, String PW, String name, String SSN, String address, String phoneNumber, String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "INSERT INTO user(U_ID, U_PW, U_Name, U_SSN, U_Address, U_PhoneNumber, U_Email, Subscription, ExpirationDate, AutoSubscription, ListCount, deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, ID);
            pstmt.setString(2, PW);
            pstmt.setString(3, name);
            pstmt.setString(4, SSN);
            pstmt.setString(5, address);
            pstmt.setString(6, phoneNumber);
            pstmt.setString(7, email);
            pstmt.setBoolean(8, false);
            pstmt.setString(9, null);
            pstmt.setBoolean(10, false);
            pstmt.setInt(11, 0);
            pstmt.setBoolean(12, false);
            return pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int musicStreaming(String title) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "UPDATE music SET PlayCount = PlayCount + 1 WHERE Title = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, title);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int subscribe(int index, boolean flag) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "UPDATE user SET Subscription = true, ExpirationDate = ?, AutoSubscription = ? WHERE U_Index = ? AND deleted = false";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            Date date = new Date(new java.util.Date().getTime());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, 1);
            pstmt.setDate(1, new java.sql.Date(cal.getTime().getTime()));
            pstmt.setBoolean(2, flag);
            pstmt.setInt(3, index);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int unsubscribe(int index) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "UPDATE user SET Subscription = false, ExpirationDate = null, AutoSubscription = false WHERE U_Index = ? AND deleted = false";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, index);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }
}
