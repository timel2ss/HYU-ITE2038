package DAO;

import Entity.Admin;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class AdminDAO {
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

    public Admin getInfo(int index) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "SELECT * FROM admin WHERE A_Index = ?";
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
                return new Admin(userIndex, ID, PW, name, SSN, address, phoneNumber, email);
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

        String SQL = "SELECT A_Index, A_PW FROM admin WHERE A_ID = ?";
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

        String SQL = "INSERT INTO admin(A_ID, A_PW, A_Name, A_SSN, A_Address, A_PhoneNumber, A_Email) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int musicInsert(String title, String album, String lyrics, int playTime, String composer, String lyricist, String arranger) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "INSERT INTO music(Title, Album, ReleaseDate, Lyrics, PlayTime, PlayCount, Composer, Lyricist, Arranger) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, title);
            pstmt.setString(2, album);
            pstmt.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
            pstmt.setString(4, lyrics);
            pstmt.setInt(5, playTime);
            pstmt.setInt(6, 0);
            pstmt.setString(7, composer);
            pstmt.setString(8, lyricist);
            pstmt.setString(9, arranger);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int insertManagingMusic(Admin admin, int musicIdx) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "INSERT INTO manages_music VALUES (?, ?)";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, admin.getIndex());
            pstmt.setInt(2, musicIdx);
            return pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int singerInsert(int index, String singerName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "INSERT INTO singer(Music_Idx, Name) VALUES (?, ?)";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, index);
            pstmt.setString(2, singerName);
            int result = pstmt.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int genreInsert(int index, String genreName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "INSERT INTO genre(Music_Idx, MusicGenre) VALUES (?, ?)";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, index);
            pstmt.setString(2, genreName);
            int result = pstmt.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int getMusicLastIdx() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "SELECT MAX(M_Index) FROM music";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int findIndexByTitle(String title) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "SELECT M_Index FROM music WHERE Title = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, title);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int deleteMusic(int deleteIdx) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "DELETE FROM music where M_Index = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, deleteIdx);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int showUserList() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "SELECT U_Index, U_ID, U_Name, U_PhoneNumber, U_Email, Subscription, ExpirationDate, AutoSubscription, deleted FROM user";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%10s %10s %10s %20s %20s %20s %20s %20s %20s\n", "Index", "ID", "Name", "PhoneNumber", "Email", "Subscription", "ExpirationDate", "AutoSubscription", "Deleted");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            while(rs.next()) {
                int index = rs.getInt(1);
                String ID = rs.getString(2);
                String name = rs.getString(3);
                String phoneNumber = rs.getString(4);
                String email = rs.getString(5);
                boolean subscription = rs.getBoolean(6);
                String expirationDate = rs.getString(7);
                boolean autoSubscription = rs.getBoolean(8);
                boolean deleted = rs.getBoolean(9);
                System.out.printf("%10d %10s %10s %20s %20s %20b %20s %20b %20b\n", index, ID, name, phoneNumber, email, subscription, expirationDate, autoSubscription, deleted);
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int showDeletedUserList() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "SELECT U_Index, U_ID, U_Name, U_PhoneNumber, U_Email, Subscription, ExpirationDate, AutoSubscription, deleted FROM user WHERE deleted = true";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%10s %10s %10s %20s %20s %20s %20s %20s %20s\n", "Index", "ID", "Name", "PhoneNumber", "Email", "Subscription", "ExpirationDate", "AutoSubscription", "Deleted");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            while(rs.next()) {
                int index = rs.getInt(1);
                String ID = rs.getString(2);
                String name = rs.getString(3);
                String phoneNumber = rs.getString(4);
                String email = rs.getString(5);
                boolean subscription = rs.getBoolean(6);
                String expirationDate = rs.getString(7);
                boolean autoSubscription = rs.getBoolean(8);
                boolean deleted = rs.getBoolean(9);
                System.out.printf("%10d %10s %10s %20s %20s %20b %20s %20b %20b\n", index, ID, name, phoneNumber, email, subscription, expirationDate, autoSubscription, deleted);
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int restore(int index) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "UPDATE user SET deleted = false WHERE U_Index = ?";
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

    public int deleteUser(int deleteIdx) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "UPDATE user SET deleted = true WHERE U_Index = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, deleteIdx);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int insertManagingUser(Admin admin, int userIdx) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "INSERT INTO manages_user VALUES (?, ?)";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, admin.getIndex());
            pstmt.setInt(2, userIdx);
            return pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int deleteManagingUser(Admin admin, int userIdx) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "DELETE FROM manages_user WHERE Admin_Idx = ? and User_Idx = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, admin.getIndex());
            pstmt.setInt(2, userIdx);
            return pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }
}
