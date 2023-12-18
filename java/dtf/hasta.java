/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtf;

import java.util.Date;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dtf.register;

/**
 *
 * @author Windows 11
 */
public class hasta {

    public int hastaId;
    public String isim;
    public String soyisim;
    public int registerId;
    public String email;
    public String telefon;
    public String tc;
    public Date dogumTarihi;
    public String cinsiyet;

    public Connection connection;

    public void HastaDAO() {
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

    public void addHasta(String isim, String soyisim, int register, String email, String telefon, String tc, String cinsiyet, String dogumTarihi) {
        try {
            String query = "INSERT INTO Hasta (isim, soyisim, register_id, email, telefon, tc, cinsiyet, dogum_tarihi) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, isim);
            preparedStatement.setString(2, soyisim);
            preparedStatement.setInt(3, register);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, telefon);
            preparedStatement.setString(6, tc);
            //preparedStatement.setDate(7, new java.sql.Date(hasta.getDogumTarihi().getTime()));
            preparedStatement.setString(7, cinsiyet);
            preparedStatement.setDate(8, java.sql.Date.valueOf(dogumTarihi));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<hasta> getHastaList() {
        List<hasta> hastaList = new ArrayList<>();
        try {
            String query = "SELECT * FROM Hasta";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                hasta hasta = new hasta();
                hasta.hastaId = resultSet.getInt("hasta_id");
                hasta.isim = resultSet.getString("isim");
                hasta.soyisim = resultSet.getString("soyisim");
                hasta.registerId = resultSet.getInt("register_id");
                hasta.email = resultSet.getString("email");
                hasta.telefon = resultSet.getString("telefon");
                hasta.tc = resultSet.getString("tc");
                hasta.cinsiyet = resultSet.getString("cinsiyet");
                hastaList.add(hasta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hastaList;
    }

    public int getRegisterIdByHastaId(int hastaId) {
        int registerId = -1;
        try {
            String query = "SELECT register_id FROM Hasta WHERE hasta_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, hastaId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                registerId = resultSet.getInt("register_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registerId;
    }

    public void deleteHastaForce(int hastaId) {
        try {
            String query = "DELETE FROM Hasta WHERE hasta_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, hastaId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteHasta(int hastaId) {
        int registerId = -1;
        try {
            String query = "SELECT register_id FROM Hasta WHERE hasta_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, hastaId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                registerId = resultSet.getInt("register_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (registerId != -1) {

            register reg = new register();
            reg.RegisterDAO();
            reg.delete(registerId);
            try {
                String query = "DELETE FROM Hasta WHERE hasta_id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, hastaId);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
