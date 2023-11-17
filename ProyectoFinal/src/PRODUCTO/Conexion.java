/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PRODUCTO;

import PRODUCTO.Conexion;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    public static Connection connectDB() {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/iniciosesion", "root", "");

            return connect;

        } catch (Exception e) {e.printStackTrace();}
        return null;

    }

}