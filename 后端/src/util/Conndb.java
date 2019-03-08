package util;

import java.sql.Connection;

public class Conndb {
    private static Connpool pool = new Connpool();

    public static Connection getConn() {
        return pool.get();
    }
}
