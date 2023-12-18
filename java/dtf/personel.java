/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtf;

import dtf.register;
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
public class personel {

    public int personelId;
    public String isim;
    public String soyisim;
    public int registerId;
    public String bolum;
    public int hastaneId;

    public Connection connection;

    public void PersonelDAO() {
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

    public void addPersonel(String isim, String soyisim, int registerId, String bolum, int hastaneId) {
        String sql = "INSERT INTO Personel (isim, soyisim, register_id, bolum, hastane_id) VALUES (?, ?, ?, ?, ?)";

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

    public void deletePersonel(int personelId) {
        String sql = "DELETE FROM Personel WHERE personel_id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, personelId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
        public List<personel> getAllPersonel() {
        String sql = "SELECT * FROM Personel";
        List<personel> personelList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                personel personel = new personel();
                    personel.personelId=rs.getInt("personel_id");
                    personel.isim=rs.getString("isim");
                    personel.soyisim=rs.getString("soyisim");
                    personel.registerId=rs.getInt("register_id");
                    personel.bolum=rs.getString("bolum");
                    personel.hastaneId=rs.getInt("hastane_id");
                personelList.add(personel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personelList;
    }
    
        public List<personel> getPersonelByHastaneId(int hastaneId) {
        String sql = "SELECT * FROM Personel WHERE hastane_id = ? ORDER BY isim, soyisim";
        List<personel> personelList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, hastaneId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    personel personel = new personel();
                    personel.personelId=rs.getInt("personel_id");
                    personel.isim=rs.getString("isim");
                    personel.soyisim=rs.getString("soyisim");
                    personel.registerId=rs.getInt("register_id");
                    personel.bolum=rs.getString("bolum");
                    personel.hastaneId=rs.getInt("hastane_id");
                    personelList.add(personel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personelList;
    }
        
        
        
}
