package com.example.leoringassignment2.controller;


import com.example.leoringassignment2.model.StudentClass;
import com.example.leoringassignment2.model.StudentModule;

import java.sql.*;

import static com.example.leoringassignment2.StudentApplication.createPopUp;

/**
 * singleton class that manages the database and validates data for it
 * @author Leo Ring
 */
public class StudentDataController {
    private Connection dbConn; // connection to database
    private final static StudentDataController instance = new StudentDataController();

    /**
     * private constructor for the singleton class
     */

    private StudentDataController(){
        createConnection();
    }

    /**
     * gets the instance of the singleton
     * @return the instance of the singleton
     */
    public static StudentDataController getInstance(){return instance;}

    /**
     * method to create a new connection to the database, if database doesn't exist it creates it, same for tables
     */
    private void createConnection(){
        try{
            String driverName = "com.mysql.cj.jdbc.Driver";
            String databaseURL = "jdbc:mysql://localhost:3306/";
            String databaseName = "oopAssignment2";
            Class.forName(driverName);
            this.dbConn = DriverManager.getConnection(databaseURL);
            Statement sqlStmt = null;
            String databaseInitialiseString = String.format("CREATE DATABASE IF NOT EXISTS %s;", databaseName);
            String switchDBString = String.format("USE %s;", databaseName);
            String createStudentsTableString = StudentClass.toSQLCreateTable();
            String createModulesTableString = StudentModule.toSQLCreateTable();
            String createGradesTableString = """
                    CREATE TABLE IF NOT EXISTS Grades (
                        moduleId varchar(255) NOT NULL,
                        studentId varchar(255) NOT NULL,
                        gradeVal FLOAT(0),
                        PRIMARY KEY (moduleId, studentId),
                        FOREIGN KEY (moduleId) REFERENCES Modules(moduleId) ON DELETE CASCADE ON UPDATE CASCADE,
                        FOREIGN KEY (studentId) REFERENCES Students(studentId) ON DELETE CASCADE ON UPDATE CASCADE
                    );""";
            try{
                sqlStmt = this.dbConn.createStatement();
                sqlStmt.executeUpdate(databaseInitialiseString);
                sqlStmt.executeUpdate(switchDBString);
                sqlStmt.executeUpdate(createStudentsTableString);
                sqlStmt.executeUpdate(createModulesTableString);
                sqlStmt.executeUpdate(createGradesTableString);
            }  catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (sqlStmt != null) {
                        sqlStmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Connection Opened");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to close connection to database
     */
    public void closeConnection(){
        try {
            if (this.dbConn != null) {
                this.dbConn.close();
                System.out.println("Connection Closed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * method to create a new sql statement object
     * @return statement object
     */
    public Statement createStatement(){
        Statement sqlStmt = null;
        try{
            sqlStmt = this.dbConn.createStatement();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return sqlStmt;
    }

    /**
     * method to write to database
     * @param statementString sql statement to execute
     */
    public void writeStatement(String statementString){
        Statement sqlStmt = null;
        try{
            sqlStmt = this.dbConn.createStatement();
            sqlStmt.executeUpdate(statementString);
        }  catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sqlStmt != null) {
                    sqlStmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * method to validate a student and add it to the database
     * @param student the student to validate
     * @return whether adding to the database was successful
     */
    public boolean validateStudent(StudentClass student){
        try{
            String readString = String.format("SELECT COUNT(studentId) AS IdCount FROM Students WHERE studentId LIKE '%s'", student.getStudentId());
            Statement readStatement = createStatement();
            try {
                ResultSet resultSet = readStatement.executeQuery(readString);
                resultSet.next();
                if (resultSet.getInt("IdCount")>0){throw new Exception("That student ID has already been used.");}
            }catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    readStatement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (student.getStudentId().equals("")){throw new Exception("Student ID field cannot be empty.");}
            if (student.getName().equals("")){throw new Exception("Student name field cannot be empty.");}
            String writeString = student.toSQLInsert();
            writeStatement(writeString);
            return true;
        }
        catch (Exception error){
            createPopUp("Error", error.getMessage(), false);
            return false;
        }
    }

    /**
     * method to validate a module and add it to the database
     * @param module the module to validate
     * @return whether adding to the database was successful
     */
    public boolean validateModule(StudentModule module){
        try {
            String readString = String.format("SELECT COUNT(moduleId) AS IdCount FROM Modules WHERE moduleId LIKE '%s'", module.getCode());
            Statement readStatement = createStatement();
            try {
                ResultSet resultSet = readStatement.executeQuery(readString);
                resultSet.next();
                if (resultSet.getInt("IdCount")>0){throw new Exception("That module code has already been used.");}
            }catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    readStatement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (module.getCode().equals("")){throw new Exception("Module code field cannot be empty.");}
            if (module.getName().equals("")){throw new Exception("Module name field cannot be empty.");}
            String writeString= module.toSQLInsert();
            writeStatement(writeString);
            return true;
        }
        catch (Exception error){
            createPopUp("Error", error.getMessage(), false);
            return false;
        }
    }

    /**
     * method to validate a grade and add it to the database
     * @param student the student who has the grade
     * @param module the module the grade is for
     * @param moduleGrade the grade
     * @return whether adding to the database was successful
     */
    public boolean validateGrade(StudentClass student, StudentModule module, String moduleGrade){
    try {
        if (student==null){throw new Exception("Please select a student from the list.");}
        if (module==null){throw new Exception("Please select a module from the list.");}
        if (module.getSemester()>student.getCurrentSemester()){
            throw new Exception("Please select a module in a semester the student has completed");}
        String grade;
        if (moduleGrade.equals("")){
            grade = "NULL";
        }else{
            float gradeFloat = Float.parseFloat(moduleGrade);
            if (gradeFloat < 0 || gradeFloat > 100){throw new Exception("Grade must be between 0 and 100");}
            grade = Float.toString(gradeFloat);
        }

        String moduleReadString = String.format("SELECT COUNT(moduleId) AS IdCount FROM Modules WHERE moduleId LIKE '%s'", module.getCode());
        String studentReadString = String.format("SELECT COUNT(studentId) AS IdCount FROM Students WHERE studentId LIKE '%s'", student.getStudentId());
        String updateReadString = String.format("SELECT COUNT(studentId) AS IdCount FROM Grades" +
                " WHERE studentId LIKE '%s' AND moduleId LIKE '%s';", student.getStudentId(), module.getCode());
        String writeString = String.format("INSERT INTO Grades (moduleId, studentId, gradeVal) VALUES ('%s', '%s', %s);",
                module.getCode(), student.getStudentId(), grade);
        String updateString = String.format("UPDATE Grades SET gradeVal=%s WHERE moduleId='%s' AND studentId='%s';",
                grade, module.getCode(), student.getStudentId());
        Statement readStatement = createStatement();
        try {
            ResultSet moduleResultSet = readStatement.executeQuery(moduleReadString);
            moduleResultSet.next();
            if (moduleResultSet.getInt("IdCount")<1){throw new Exception("That module code is not associated with any module.");}

            ResultSet studentResultSet = readStatement.executeQuery(studentReadString);
            studentResultSet.next();
            if (studentResultSet.getInt("IdCount")<1){throw new Exception("That student id is not associated with any student.");}

            ResultSet updateResultSet = readStatement.executeQuery(updateReadString);
            updateResultSet.next();
            if (updateResultSet.getInt("IdCount")>0){writeStatement(updateString);}
            else {writeStatement(writeString);}
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                readStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    catch (Exception error){
        if (error instanceof NumberFormatException){
            createPopUp("Error", "Please enter a number for the grade.", false);
        }
        else{
            createPopUp("Error", error.getMessage(), false);
        }
        return false;
    }
    }
}
