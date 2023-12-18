/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtf;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Date;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Windows 11
 */
public class doktor {

    public int doktorId;
    public String isim;
    public String soyisim;
    public int registerId;
    public String bolum;
    public int hastaneId;
    public String tc;

    public Connection connection;

    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/hastane";
            String username = "root";
            String password = "Orayfena503.";
            connection = DriverManager.getConnection(url, username, password);

            String query = "INSERT INTO Doktor (isim, soyisim, register_id, bolum, hastane_id, tc) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "root");
            preparedStatement.setString(2, "root");
            preparedStatement.setInt(3, 1);
            preparedStatement.setString(4, "root");
            preparedStatement.setInt(5, 1);
            preparedStatement.setString(6, "12345678901");
            preparedStatement.executeUpdate();
            System.out.println("Doktor başarıyla eklendi.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Bağlantıyı kapat
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void DoktorDAO() {
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

    public void addDoktor(String isim, String soyisim, int registerId, String bolum, int hastaneId, String tc) {
        try {
            String query = "INSERT INTO Doktor (isim, soyisim, register_id, bolum, hastane_id, tc) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, isim);
            preparedStatement.setString(2, soyisim);
            preparedStatement.setInt(3, registerId);
            preparedStatement.setString(4, bolum);
            preparedStatement.setInt(5, hastaneId);
            preparedStatement.setString(6, tc);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<doktor> getDoktorListByHastaneId(int hastaneId) {
        List<doktor> doktorList = new ArrayList<>();
        try {
            String query = "SELECT * FROM Doktor WHERE hastane_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, hastaneId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                doktor doktor = new doktor();
                doktor.doktorId = resultSet.getInt("doktor_id");
                doktor.isim = resultSet.getString("isim");
                doktor.soyisim = resultSet.getString("soyisim");
                doktor.registerId = resultSet.getInt("register_id");
                doktor.bolum = resultSet.getString("bolum");
                doktor.hastaneId = resultSet.getInt("hastane_id");
                doktorList.add(doktor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doktorList;
    }

    public List<doktor> getAllDoktorList() {
        List<doktor> doktorList = new ArrayList<>();
        try {
            String query = "SELECT * FROM Doktor";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    doktor doktor = new doktor();
                    doktor.doktorId = resultSet.getInt("doktor_id");
                    doktor.isim = resultSet.getString("isim");
                    doktor.soyisim = resultSet.getString("soyisim");
                    doktor.registerId = resultSet.getInt("register_id");
                    doktor.bolum = resultSet.getString("bolum");
                    doktor.hastaneId = resultSet.getInt("hastane_id");
                    doktorList.add(doktor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doktorList;
    }

    public int getDoctorIdByName(String doctorName) {
        // SQL sorgusu
        String sql = "SELECT doktor_id FROM Doktor WHERE isim = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // SQL sorgusu için parametreyi set et
            preparedStatement.setString(1, doctorName);

            // Sorguyu çalıştır ve sonucu al
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Doktor bulundu, doktor_id değerini döndür
                    return resultSet.getInt("doktor_id");
                } else {
                    // Doktor bulunamadı
                    return -1; // veya istediğiniz başka bir değer
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // SQLException ile başa çıkmak için uygun bir şekilde işlem yapılmalıdır.
            return -1;
        }
    }
}
