package TeacherDataBase;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.util.Properties;

public class DB {
    public static DataSource getDS(String propertiesFile) {
        Properties props = new Properties();
        BasicDataSource ds = new BasicDataSource();
        try (FileInputStream fis = new FileInputStream(propertiesFile)) {
            props.load(fis);
            ds.setUrl(props.getProperty("url"));
            ds.setUsername(props.getProperty("username"));
            ds.setPassword(props.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}
