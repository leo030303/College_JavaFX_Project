package com.example.leoringassignment2.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;






/**
 * class to contain and manipulate data of students
 * @author Leo Ring
 */
public class StudentClass{
    private String name;
    private String studentId;
    private Date dateOfBirth;
    private int currentSemester;

    /**
     *
     * @param name name of student
     * @param studentId id of student
     * @param dateOfBirth date of birth of student
     * @param currentSemester semester the student is currently undertaking
     */
    public StudentClass(String name, String studentId, Date dateOfBirth, int currentSemester){
        this.name = name;
        this.studentId = studentId;
        this.dateOfBirth = dateOfBirth;
        this.currentSemester = currentSemester;
    }

    /**
     *
     * @param studentResultSet dataset from database containing student info
     */
    public StudentClass(ResultSet studentResultSet){
        try{
            this.name = studentResultSet.getString("studentName");
            this.studentId = studentResultSet.getString("studentId");
            this.dateOfBirth = studentResultSet.getDate("dateOfBirth");
            this.currentSemester = studentResultSet.getInt("currentSemester");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @return student name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return student id
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     *
     * @return student date of birth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     *
     * @return students current semester
     */
    public int getCurrentSemester() {
        return currentSemester;
    }

    /**
     *
     * @param name new name of student
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param dateOfBirth new date of birth of student
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     *
     * @param currentSemester new semester of student
     */
    public void setCurrentSemester(int currentSemester) {this.currentSemester = currentSemester;}

    /**
     *
     * @return string for inserting this student into the database
     */
    public  String toSQLInsert(){
        return String.format("INSERT INTO Students (studentName, studentId, dateOfBirth, currentSemester)" +
                        " VALUES ('%s', '%s', '%s', %d)", this.name, this.studentId,
                this.dateOfBirth, this.currentSemester);
    }

    /**
     *
     * @return string to create the Students table in the database
     */
    public static String toSQLCreateTable(){
        return """
                CREATE TABLE IF NOT EXISTS Students (
                    studentName varchar(255) NOT NULL,
                    studentId varchar(255) NOT NULL,
                    dateOfBirth date NOT NULL,
                    currentSemester int NOT NULL,
                    PRIMARY KEY (studentId)
                );""";
    }

    /**
     * method for formatting data for the list area
     * @return a formatted string of data of the student
     */
    @Override
    public String toString() {
        return String.format("Name: %-10sID: %-6sDOB: %-12sSemester: %s\n", this.name, this.studentId, this.dateOfBirth, this.currentSemester);
    }
}

