/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Windows 11
 */
public class owner {

    public int sahipId;
    public String isim;
    public String soyisim;
    public int registerId;
    public String bolum;
    public int hastaneId;

    public Connection connection;

    public void OwnerDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/hastane";
            String username = "root";
            String password = "Orayfena503.";
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addowner(String isim, String soyisim, int registerId, String bolum, int hastaneId) {
        String sql = "INSERT INTO Sahip (isim, soyisim, register_id, bolum, hastane_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, isim);
            pstmt.setString(2, soyisim);
            pstmt.setInt(3, registerId);
            pstmt.setString(4, bolum);
            pstmt.setInt(5, hastaneId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ (Select)
    public List<owner> getAllowner() {
        String sql = "SELECT * FROM Sahip";
        List<owner> sahipList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                owner sahip = new owner();
                sahip.sahipId = rs.getInt("sahip_id");
                sahip.isim = rs.getString("isim");
                sahip.soyisim = rs.getString("soyisim");
                sahip.registerId = rs.getInt("register_id");
                sahip.bolum = rs.getString("bolum");
                sahip.hastaneId = rs.getInt("hastane_id");
                sahipList.add(sahip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sahipList;
    }

    public void deleteowner(int sahipId) {
        String sql = "DELETE FROM Sahip WHERE sahip_id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, sahipId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<owner> getownerByHastaneId(int hastaneId) {
        String sql = "SELECT * FROM Sahip WHERE hastane_id = ?";
        List<owner> sahipList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, hastaneId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    owner sahip = new owner();
                    sahip.sahipId = rs.getInt("sahip_id");
                    sahip.isim = rs.getString("isim");
                    sahip.soyisim = rs.getString("soyisim");
                    sahip.registerId = rs.getInt("register_id");
                    sahip.bolum = rs.getString("bolum");
                    sahip.hastaneId = rs.getInt("hastane_id");
                    sahipList.add(sahip);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sahipList;
    }
}
