package TeacherDataBase;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class InsertingDB {
    static String[] subjects = new String[]{"Algebra", "Geometry", "English", "Russian", "History", "Chemistry", "Physics"};
    static String[] teachers = new String[]{"Ivanov A.A.", "Petrov B.B.", "Smith D.D.", "Browns A.B.", "Pechkin V.W.", "Potapov N.M.", "Sidorov K.L."};
    static String[] audiences = new String[]{"A205", "A208", "B304", "D321", "E506"};
    static String[] weekdays = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public static void fillDB(String propertiesFile) {
        DataSource ds = DB.getDS(propertiesFile);
        try (Connection conn = ds.getConnection()) {
            System.out.println("Connection: " + conn.isValid(1));
            String query_ = "SELECT count (*) FROM subjects;";
            ResultSet rs_ = conn.createStatement().executeQuery(query_);
            rs_.next();
            int n = rs_.getInt("count");
            if (n == 0) {
                String subjectQuery = "INSERT INTO subjects(name) VALUES (?);";
                String teacherQuery = "INSERT INTO teachers(full_name) VALUES (?);";
                String ttQuery = "INSERT INTO timetable(audience, weekday, student_amount, subject, teacher) VALUES (?, ?, ?, ?, ?);";
                String linkQuery = "INSERT INTO link_subject_teacher(lesson_amount, subject, teacher) VALUES (?, ?, ?);";
                for (String subject : subjects) {
                    PreparedStatement subjectSt = conn.prepareStatement(subjectQuery);
                    subjectSt.setString(1, subject);
                    subjectSt.executeUpdate();
                }
                for (String teacher : teachers) {
                    PreparedStatement teacherSt = conn.prepareStatement(teacherQuery);
                    teacherSt.setString(1, teacher);
                    teacherSt.executeUpdate();
                }
                int numOfLessons = (int) (0.8*subjects.length*teachers.length);
                for (int i = 0; i < numOfLessons; ++i) {
                    PreparedStatement ttSt = conn.prepareStatement(ttQuery);
                    ttSt.setString(1, audiences[new Random().nextInt(audiences.length)]);
                    ttSt.setString(2, weekdays[new Random().nextInt(weekdays.length)]);
                    ttSt.setInt(3, new Random().nextInt(10) + 10);
                    ttSt.setString(4, subjects[new Random().nextInt(subjects.length)]);
                    ttSt.setString(5, teachers[new Random().nextInt(teachers.length)]);
                    ttSt.executeUpdate();
                }
                for (String _teacher : teachers) {
                    String _query = "SELECT subject, count(subject) FROM timetable WHERE teacher='" + _teacher + "' group by subject;";
                    ResultSet _rs = conn.createStatement().executeQuery(_query);
                    while (_rs.next()) {
                        String sub = _rs.getString("subject");
                        int count = _rs.getInt("count");
                        PreparedStatement linkSt = conn.prepareStatement(linkQuery);
                        linkSt.setInt(1, count);
                        linkSt.setString(2, sub);
                        linkSt.setString(3, _teacher);
                        linkSt.executeUpdate();
                    }
                }
            } else {
                System.out.println("Some tables already filled");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
