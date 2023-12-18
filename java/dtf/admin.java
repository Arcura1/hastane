/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtf;
import dtf.register;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Windows 11
 */
public class admin {
    public int adminId;
    public String isim;
    public String soyisim;
    public int registerId;
    public String email;
    public String telefon;
    
        public Connection connection;

    public void AdminDAO() {
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
    
}
