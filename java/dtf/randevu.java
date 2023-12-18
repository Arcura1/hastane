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
import java.sql.Timestamp;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
/**
 *
 * @author Windows 11
 */
public class randevu {

    public int randevuId;
    public int hastaId;
    public int doktorId;
    public Date randevuTarihi;

    public Connection connection;

    public void RandevuDAO() {
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
    public static void main(String[] args) {
        // Rastgele bir zaman damgası oluştur
        Timestamp randomTimestamp = getRandomTimestamp();

        // Zaman damgasını gün ve saat olarak ayır
        String dayAndHour = splitTimestamp(randomTimestamp);

        // Sonucu yazdır
        System.out.println("Rastgele Zaman Damgası: " + randomTimestamp);
        System.out.println("Ayırtılmış Zaman: " + dayAndHour);
    }

    private static Timestamp getRandomTimestamp() {
        long offset = Timestamp.valueOf("2020-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2023-01-01 00:00:00").getTime();
        long diff = end - offset + 1;
        return new Timestamp(offset + (long) (Math.random() * diff));
    }

    private static String splitTimestamp(Timestamp timestamp) {
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String day = dateFormat.format(date);
        String hour = timeFormat.format(date);

        return "Gün: " + day + ", Saat: " + hour;
    }

    public void addRandevu(int hastaId, int doktorId, Timestamp randevuTarihi) {
        try {
            String query = "INSERT INTO Randevu (hasta_id, doktor_id, randevu_tarihi) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, hastaId);
            preparedStatement.setInt(2, doktorId);
            preparedStatement.setTimestamp(3, randevuTarihi);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<randevu> getRandevuListByHastaId(int hastaId) {
        List<randevu> randevuList = new ArrayList<>();
        try {
            String query = "SELECT * FROM Randevu WHERE hasta_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, hastaId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                randevu randevu = new randevu();
                randevu.randevuId = resultSet.getInt("randevu_id");
                randevu.hastaId = resultSet.getInt("hasta_id");
                randevu.doktorId = resultSet.getInt("doktor_id");
                randevu.randevuTarihi = resultSet.getTimestamp("randevu_tarihi");
                randevuList.add(randevu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return randevuList;
    }



    public List<randevu> getRandevuListByDoktorAndTarih(int doktorId, Timestamp randevuTarihi) {
        List<randevu> randevuList = new ArrayList<>();
        try {
            String query = "SELECT * FROM Randevu WHERE doktor_id = ? AND randevu_tarihi = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doktorId);
            preparedStatement.setTimestamp(2, randevuTarihi);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                randevu randevu = new randevu();
                randevu.randevuId = resultSet.getInt("randevu_id");
                randevu.hastaId = resultSet.getInt("hasta_id");
                randevu.doktorId = resultSet.getInt("doktor_id");
                randevu.randevuTarihi = resultSet.getTimestamp("randevu_tarihi");
                randevuList.add(randevu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return randevuList;
    }

        public List<String> getRandevuTarihleriByDoktorId(int doktorId) {
        List<String> randevuTarihleri = new ArrayList<>();

        try {
            String query = "SELECT randevu_tarihi FROM Randevu WHERE doktor_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doktorId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String randevuTarihi = resultSet.getString("randevu_tarihi");
                randevuTarihleri.add(randevuTarihi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return randevuTarihleri;
    }
    
    

}
