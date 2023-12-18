/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hastane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class database {

    public Connection baglanti;
    public String url = "jdbc:mysql://localhost:3306/hastane";
    public String kullaniciAdi = "root";
    public String sifre = "Orayfena503.";

    public static void main(String[] args) {
        // MySQL veritabanına bağlanmak için kullanıcı adı ve şifreyi belirtin
        String url = "jdbc:mysql://localhost:3306/hastane";
        String kullaniciAdi = "root";
        String sifre = "Orayfena503.";

        // Bağlantıyı oluştur
        try (Connection connection = DriverManager.getConnection(url, kullaniciAdi, sifre)) {
            System.out.println("Veritabanına başarıyla bağlandı!");
        } catch (SQLException e) {
            System.err.println("Veritabanına bağlantı sağlanırken hata oluştu!");
            e.printStackTrace();
        }
    }

    public void baglanti() {

        // MySQL veritabanına bağlanmak için kullanıcı adı ve şifreyi belirtin
        // Bağlantıyı oluştur
        try (Connection connection = DriverManager.getConnection(this.url, this.kullaniciAdi, this.sifre)) {
            System.out.println("Veritabanına başarıyla bağlandı!");
            this.baglanti = connection;
        } catch (SQLException e) {
            System.err.println("Veritabanına bağlantı sağlanırken hata oluştu!");
            e.printStackTrace();
            this.baglanti = null;
        }
    }

    public void insertData(Connection connection, String adSoyad, String email, String telefon) {
        String insertQuery = "INSERT INTO kisiler (ad_soyad, email, telefon) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, adSoyad);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, telefon);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Veri başarıyla eklendi!");
            } else {
                System.err.println("Veri eklenirken bir hata oluştu.");
            }
        } catch (SQLException e) {
            System.err.println("Veri eklenirken bir SQL hatası oluştu!");
            e.printStackTrace();
        }
    }
    
    public void readData(Connection connection) {
        String selectQuery = "SELECT * FROM kisiler";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String adSoyad = resultSet.getString("ad_soyad");
                String email = resultSet.getString("email");
                String telefon = resultSet.getString("telefon");

                System.out.println("Ad Soyad: " + adSoyad + ", Email: " + email + ", Telefon: " + telefon);
            }

        } catch (SQLException e) {
            System.err.println("Veri okunurken bir SQL hatası oluştu!");
            e.printStackTrace();
        }
    }
    
    public void deleteData(Connection connection, String adSoyad) {
        String deleteQuery = "DELETE FROM kisiler WHERE ad_soyad = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, adSoyad);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Veri başarıyla silindi!");
            } else {
                System.err.println("Silinecek veri bulunamadı veya silme işlemi sırasında bir hata oluştu.");
            }
        } catch (SQLException e) {
            System.err.println("Veri silinirken bir SQL hatası oluştu!");
            e.printStackTrace();
        }
    }

}
