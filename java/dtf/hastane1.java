/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Windows 11
 */
public class hastane1 {

    public int hastaneId;
    public String isim;
    public String sehir;

    public Connection connection = null;

    public static void main(String[] args) {

    }

    public void HastaneDAO() {
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

    public void create(String isim, String sehir) {
        try {
            String query = "INSERT INTO Hastane (isim, sehir) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, isim);
            preparedStatement.setString(2, sehir);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<hastane1> getHastanelerBySehir(String sehir) {
        List<hastane1> hastaneler = new ArrayList<>();
        try {
            String query = "SELECT * FROM Hastane WHERE sehir = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, sehir);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                hastane1 hast = new hastane1();
                hast.isim = resultSet.getString("isim");
                hast.sehir = resultSet.getString("sehir");
                hastaneler.add(hast);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hastaneler;
    }
        public List<hastane1> getAllHastaneler() {
        List<hastane1> hastaneler = new ArrayList<>();
        try {
            String query = "SELECT * FROM Hastane";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                hastane1 hast = new hastane1();
                hast.isim = resultSet.getString("isim");
                hast.sehir = resultSet.getString("sehir");
                hast.hastaneId = resultSet.getInt("hastane_id");
                // Diğer özellikleri de set etmeyi unutmayın
                hastaneler.add(hast);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hastaneler;
    }
        public int getHastaneId(String hastaneIsmi) {
        int hastaneId = -1; // Bulunamazsa -1 döndür

        try {
            String query = "SELECT hastane_id FROM Hastane WHERE isim = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, hastaneIsmi);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                hastaneId = resultSet.getInt("hastane_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hastaneId;
    }

}
