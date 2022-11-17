package TeacherDataBase;

import java.sql.Connection;
import java.sql.ResultSet;

public class MakingQuery {
    public static ResultSet getResultSet(Connection conn, String query) {
        try {
            return conn.createStatement().executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
