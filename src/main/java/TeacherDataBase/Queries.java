package TeacherDataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Queries {

    public static void infoAboutTeachers(Connection conn, String t) {
        try {
            System.out.println("Full name: " + t);
            String query2 = "SELECT subject FROM link_subject_teacher WHERE teacher='" + t + "';";
            ResultSet rs2 = MakingQuery.getResultSet(conn, query2);
            System.out.print("Subjects: ");
            if (rs2 != null) {
                while (rs2.next()) {
                    System.out.print(rs2.getString("subject") + " ");
                }
            }
            System.out.println("\n");
            String query3 = "SELECT subject, count(subject) FROM timetable WHERE teacher='" + t + "' group by subject;";
            ResultSet rs3 = MakingQuery.getResultSet(conn, query3);
            if (rs3 != null) {
                while (rs3.next()) {
                    System.out.println(rs3.getString("subject") + ": " + rs3.getInt("count") + " in a week");
                }
            }
            System.out.println();
            String query4 = "SELECT subject, audience, weekday, student_amount FROM timetable WHERE teacher='" + t + "';";
            ResultSet rs4 = MakingQuery.getResultSet(conn, query4);
            if (rs4 != null) {
                while (rs4.next()) {
                    System.out.println("Lesson: " + rs4.getString("subject") + " " + rs4.getString("audience") +
                            " " + rs4.getString("weekday") + "; student amount: " + rs4.getInt("student_amount"));
                }
            }
            System.out.println("\n\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void task1(String weekday, String audience) {
        try (Connection conn = DB.getDS("dbProperties.txt").getConnection()) {
            String query1 = "SELECT teacher, student_amount FROM timetable WHERE weekday='" + weekday + "' AND audience='" + audience + "';";
            Set<String> teachers = new HashSet<>();
            ResultSet rs1 = MakingQuery.getResultSet(conn, query1);
            if (rs1 != null) {
                while (rs1.next()) {
                    teachers.add(rs1.getString("teacher"));
                }
            }
            for (String t : teachers) {
                infoAboutTeachers(conn, t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void task2(String weekday) {
        try (Connection conn = DB.getDS("dbProperties.txt").getConnection()) {
            Set<String> ts = new HashSet<>();
            Set<String> teachers = new HashSet<>();
            String query1 = "SELECT DISTINCT teacher FROM timetable WHERE weekday='" + weekday + "';";
            ResultSet rs1 = MakingQuery.getResultSet(conn, query1);
            if (rs1 != null) {
                while (rs1.next()) {
                    ts.add(rs1.getString("teacher"));
                }
            }
            String query2 = "SELECT * from teachers;";
            ResultSet rs2 = MakingQuery.getResultSet(conn, query2);
            if (rs2 != null) {
                while (rs2.next()) {
                    teachers.add(rs2.getString("full_name"));
                }
            }
            teachers.removeAll(ts);
            for (String t: teachers) {
                infoAboutTeachers(conn ,t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void task3(int amount) {
        try (Connection conn = DB.getDS("dbProperties.txt").getConnection()) {
            String query1 = "SELECT weekday, count(id_tt) from timetable group by (weekday) having count(id_tt)=" + amount + ";";
            ResultSet rs1 = MakingQuery.getResultSet(conn, query1);
            if (rs1 != null) {
                while (rs1.next()) {
                    System.out.println(rs1.getString("weekday"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
