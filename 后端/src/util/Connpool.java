package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connpool {
    private static String user;
    private static String passwd;
    private static String url;
    private static String driver;
    private static Properties prop = null;

    static{
            prop = new Properties();
            InputStream in = Connpool.class.getClassLoader().getResourceAsStream("conf/db.properties");
            try {
                prop.load(in);
            }catch (IOException e) {
                e.printStackTrace();
            }
            url = prop.getProperty("url");
            user = prop.getProperty("user");
            passwd = prop.getProperty("passwd");
            driver = prop.getProperty("driver");
            try{
                Class.forName(driver);
                System.out.println("Driver loaded.");
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
    }

    public Connection get() {
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, passwd);
            System.out.println("Database connected.");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
