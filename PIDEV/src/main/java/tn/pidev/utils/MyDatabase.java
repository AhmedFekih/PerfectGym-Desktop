package tn.pidev.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {

    private final String USER = System.getenv().getOrDefault("DB_USER", "root");
    private final String PWD = System.getenv().getOrDefault("DB_PASSWORD", "");
    private final String URL = System.getenv().getOrDefault("DB_URL", "jdbc:mysql://127.0.0.1:3306/pidev");

    //1st STEP
    public static MyDatabase instance;

    private Connection cnx;

    //2ND STEP
    private MyDatabase(){
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("Connection Etablie !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //3RD STEP
    public static MyDatabase getInstance(){
        if (instance == null) instance = new MyDatabase();
        return instance;
    }

    public Connection getCnx(){
        return cnx;
    }
}

