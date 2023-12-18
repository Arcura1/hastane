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

/**
 *
 * @author Windows 11
 */
public class register {

    public int registerId;
    public String tc;
    public String password;
    public String yetki;

    private Connection connection;

    public static void main(String[] args) {

    }

    // Veritabanı bağlantısı kuruluyor
    public void RegisterDAO() {
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

    public void create(String tc, String sifre, String yetki) {
        try {
            String query = "INSERT INTO Register (tc, sifre,yetki) VALUES (?, ?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tc);
            preparedStatement.setString(2, sifre);
            preparedStatement.setString(3, yetki);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    public void update() {
        try {
            String query = "UPDATE Register SET tc = ?, sifre = ? WHERE register_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, register.getTc());
            preparedStatement.setString(2, register.getSifre());
            preparedStatement.setInt(3, register.getRegisterId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     */
    public void delete(int registerId) {
        try {
            String query = "DELETE FROM Register WHERE register_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, registerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getYetkiByTcAndSifre(String tc, String sifre) {
        String yetki = null;
        try {
            String query = "SELECT yetki FROM Register WHERE tc = ? AND sifre = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, tc);
                preparedStatement.setString(2, sifre);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        yetki = resultSet.getString("yetki");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return yetki;

    }

    public int getRegisterIdByTc(String tc) {
    int registerId = -1; // -1, geçersiz bir değer olarak kullanılabilir

    try {
        String query = "SELECT register_id FROM Register WHERE tc = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, tc);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    registerId = resultSet.getInt("register_id");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return registerId;
}

    
    
    public int getRegisterIdByTcAndSifre(String tc, String sifre) {
        int registerId = -1; // -1, geçersiz bir değer olarak kullanılabilir

        try {
            String query = "SELECT register_id FROM Register WHERE tc = ? AND sifre = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, tc);
                preparedStatement.setString(2, sifre);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        registerId = resultSet.getInt("register_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return registerId;
    }

    public String getTcByTcAndSifre(String tc, String sifre) {
        String tcFromDb = null;
        try {
            String query = "SELECT tc FROM Register WHERE tc = ? AND sifre = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, tc);
                preparedStatement.setString(2, sifre);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        tcFromDb = resultSet.getString("tc");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tcFromDb;
    }
}
