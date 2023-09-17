package com.example.leoringassignment2.controller;


import com.example.leoringassignment2.model.StudentClass;
import com.example.leoringassignment2.model.StudentModule;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.lang.management.ManagementFactory;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.leoringassignment2.StudentApplication.createPopUp;

/**
 * class to hold all the functionality for the GUI elements
 * @author Leo Ring
 */
public class StudentController {

    /**
     * method to add a student to database, clears textFields on success
     * @param nameText text field for the name
     * @param sidText text field for the student id
     * @param dobPicker date picker for date of birth
     * @param semesterText text field for the semester
     */
    public void addStudent(TextField nameText, TextField sidText, DatePicker dobPicker, TextField semesterText){
        try {
            StudentClass student = new StudentClass(nameText.getText(), sidText.getText(),
                    Date.valueOf(dobPicker.getValue()), Integer.parseInt(semesterText.getText()));
            if (StudentDataController.getInstance().validateStudent(student)) {
                nameText.setText("");
                sidText.setText("");
                dobPicker.setValue(LocalDate.now());
                semesterText.setText("");
            }
        }catch (NumberFormatException e){
            createPopUp("Error", "Please enter an integer for the students current semester", false);
        }
    }

    /**
     * method to remove student from database, removes based off ID
     * @param nameText text field for the name
     * @param sidText text field for the student id
     * @param dobPicker date picker for date of birth
     * @param semesterText text field for the semester
     */
    public void removeStudent(TextField nameText, TextField sidText, DatePicker dobPicker, TextField semesterText){
        String deleteStatement = String.format("DELETE FROM Students WHERE studentId='%s';", sidText.getText());
        StudentDataController.getInstance().writeStatement(deleteStatement);
        nameText.setText("");
        sidText.setText("");
        dobPicker.setValue(LocalDate.now());
        semesterText.setText("");
    }

    /**
     * method to list student info in the list area
     * @param studentListArea text area that student info is printed to
     */
    public void listStudents(TextArea studentListArea){
        StringBuilder text = new StringBuilder();
        String studentReadString = "SELECT * FROM Students;";
        Statement studentReadStatement = StudentDataController.getInstance().createStatement();
        try{
            ResultSet studentResultSet = studentReadStatement.executeQuery(studentReadString);
            while (studentResultSet.next()){
                StudentClass currentStudent = new StudentClass(studentResultSet);
                text.append(currentStudent);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                studentReadStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        studentListArea.setText(text.toString());
    }

    /**
     * method to add students to studentPicker
     * @param picker combo box that holds list of students
     */
    public void fillStudentPicker(ComboBox<StudentClass> picker){
        picker.getItems().clear();
        String studentReadString = "SELECT * FROM Students;";
        Statement studentReadStatement = StudentDataController.getInstance().createStatement();
        try{
            ResultSet studentResultSet = studentReadStatement.executeQuery(studentReadString);
            while (studentResultSet.next()){
                picker.getItems().add(new StudentClass(studentResultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                studentReadStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * method to add a module to the database
     * Clears fields on success
     * @param moduleName text field for name of module
     * @param moduleCode text field for module code
     * @param moduleSemester text field for module semester
     */
    public void addModule(TextField moduleName, TextField moduleCode, TextField moduleSemester){
        try {
            StudentModule module = new StudentModule(moduleName.getText(), moduleCode.getText(), Integer.parseInt(moduleSemester.getText()));
            if (StudentDataController.getInstance().validateModule(module)) {
                moduleName.setText("");
                moduleCode.setText("");
                moduleSemester.setText("");
            }
        }catch (NumberFormatException e){
            createPopUp("Error", "Please enter an integer for the module semester", false);
        }
    }

    /**
     * method to remove module from database, based off module code
     * @param moduleName text field for name of module
     * @param moduleCode text field for module code
     * @param moduleSemester text field for module semester
     */
    public void removeModule(TextField moduleName, TextField moduleCode, TextField moduleSemester){
        String deleteStatement = String.format("DELETE FROM Modules WHERE moduleId='%s';", moduleCode.getText());
        StudentDataController.getInstance().writeStatement(deleteStatement);
        moduleName.setText("");
        moduleCode.setText("");
        moduleSemester.setText("");
    }

    /**
     * method to display module info
     * @param moduleTextArea text area to display modules
     */
    public void listModules(TextArea moduleTextArea){
        StringBuilder text = new StringBuilder();
        String moduleReadString = "SELECT * FROM Modules;";
        Statement moduleReadStatement = StudentDataController.getInstance().createStatement();
        try{
            ResultSet moduleResultSet =  moduleReadStatement.executeQuery(moduleReadString);
            while (moduleResultSet.next()){
                StudentModule currentModule = new StudentModule(moduleResultSet);
                text.append(currentModule);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                moduleReadStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        moduleTextArea.setText(text.toString());
    }

    /**
     * method to fill combo box with modules
     * @param picker combo box with list of modules
     */
    public void fillModulePicker(ComboBox<StudentModule> picker){
        picker.getItems().clear();
        String moduleReadString = "SELECT * FROM Modules;";
        Statement moduleReadStatement = StudentDataController.getInstance().createStatement();
        try{
            ResultSet moduleResultSet =  moduleReadStatement.executeQuery(moduleReadString);
            while (moduleResultSet.next()){
                picker.getItems().add(new StudentModule(moduleResultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                moduleReadStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * method to add grade to database, clears fields on success
     * @param studentBox combo box with list of students
     * @param moduleBox combo box with list of modules
     * @param moduleGrade text field holding the grade for the student in the module
     */
    public void addGrade(ComboBox<StudentClass> studentBox, ComboBox<StudentModule> moduleBox, TextField moduleGrade){
        if (StudentDataController.getInstance().validateGrade(studentBox.getValue(), moduleBox.getValue(), moduleGrade.getText())){
            studentBox.getSelectionModel().select(-1);
            moduleBox.getSelectionModel().select(-1);
            moduleGrade.setText("");
        }
    }

    /**
     * method to remove grade from database, based off student ID and module code
     * @param studentBox combo box with list of students
     * @param moduleBox combo box with list of modules
     * @param moduleGrade text field holding the grade for the student in the module
     */
    public void removeGrade(ComboBox<StudentClass> studentBox,
                            ComboBox<StudentModule> moduleBox, TextField moduleGrade){
        try {
            if (studentBox.getValue() == null || moduleBox.getValue() == null) {
                throw new Exception("Please select a student and a module to delete a grade for.");
            }
            String deleteStatement = String.format("DELETE FROM Grades WHERE moduleId='%s' AND studentId='%s';",
                    moduleBox.getValue().getCode(), studentBox.getValue().getStudentId());
            StudentDataController.getInstance().writeStatement(deleteStatement);
            studentBox.getSelectionModel().select(-1);
            moduleBox.getSelectionModel().select(-1);
            moduleGrade.setText("");
        }catch (Exception error){
            createPopUp("Error", error.getMessage(), false);
        }
    }

    /**
     * method to display grades
     * @param gradeListArea text area to display grades
     */
    public void listGrades(TextArea gradeListArea){
        StringBuilder text = new StringBuilder();
        String gradeReadString = "SELECT * FROM Grades;";
        Statement gradeReadStatement = StudentDataController.getInstance().createStatement();
        try{
            ResultSet gradeResultSet =  gradeReadStatement.executeQuery(gradeReadString);
            while (gradeResultSet.next()){
                text.append(String.format("Module Code: %-10sStudent ID: %-10sGrade: ", gradeResultSet.getString("moduleId"),
                        gradeResultSet.getString("studentId")));
                float grade = gradeResultSet.getFloat("gradeVal");
                if (gradeResultSet.wasNull()){
                    text.append("NP\n");
                }else {
                    text.append(String.format("%.2f%%\n", grade));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                gradeReadStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        gradeListArea.setText(text.toString());
    }

    /**
     * method to display modules and grades for a specific student and sort them
     * @param student the student to display records for
     * @param recordsArea text area to display records in
     * @param alphabetCheck checkbox for alphabetic ordering of records
     * @param numericCheck checkbox for numerical ordering of records
     */
    public void displayRecords(StudentClass student, TextArea recordsArea, CheckBox alphabetCheck, CheckBox numericCheck){
        recordsArea.setText("");
        if (student != null){
            String queryString = String.format("SELECT Modules.moduleName AS moduleName, Modules.moduleId AS moduleId, Modules.moduleSemester AS moduleSemester, Grades.gradeVal AS gradeVal " +
                    "FROM Grades INNER JOIN Modules ON Grades.moduleId=Modules.moduleId " +
                    "WHERE Grades.studentId='%s' AND Grades.gradeVal >= 40", student.getStudentId());
            if (alphabetCheck.isSelected()){
                queryString += " ORDER BY Modules.moduleName;";
            }
            else if (numericCheck.isSelected()){
                queryString += " ORDER BY Grades.gradeVal DESC;";
            }
            else{
                queryString += ";";
            }
            StringBuilder recordsText = new StringBuilder();
            recordsText.append(student).append(String.format("%-10s %-10s %-10s Grade\n", "Name", "Code", "Semester"))
                    .append("--------------------------------------\n");
            Statement recordReadStatement = StudentDataController.getInstance().createStatement();
            try{
                ResultSet recordResultSet =  recordReadStatement.executeQuery(queryString);
                while (recordResultSet.next()){
                    recordsText.append(String.format("%-10s %-10s %-10s %.2f\n", recordResultSet.getString("moduleName"),
                            recordResultSet.getString("moduleId"),recordResultSet.getString("moduleSemester"),
                            recordResultSet.getFloat("gradeVal")));
                }
            } catch (SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    recordReadStatement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            recordsArea.setText(recordsText.toString());
        }
    }

    /**
     * method to ensure only one checkbox is checked at a time
     * @param check1 checkbox to manage
     * @param check2 checkbox to manage
     */
    public void manageCheckBox(CheckBox check1, CheckBox check2){
        if (check1.isSelected()){
            check2.setSelected(false);
        }
        if (check2.isSelected()){
            check1.setSelected(false);
        }
    }

    /**
     * method that simulates a heap overflow
     * @param statTextArea text area to display usage statistics in
     */
    public void simulateLeak(TextArea statTextArea){
        String totalHeapSize = Long.toString(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax()/(1024 *1024));
        String startHeapUse = Long.toString(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed()/(1024 *1024));
        ArrayList<StudentClass> leakList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i=0;i<i+1;i++){
            leakList.add(new StudentClass("test", "1", new Date(System.currentTimeMillis()), 2));
            if (i%1000==0){
                String statText = String.format("Total Heap Size (mB): %s\nStarting Heap Used (mB): %s\nEnding Heap used (mB): %s\nElapsed Time (ms): %s",
                        totalHeapSize, startHeapUse,
                        ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed()/(1024 *1024),
                        (System.currentTimeMillis()-startTime));
                System.out.println(statText);
                statTextArea.setText(statText);
            }
        }
    }
    /**
     * method that just closes the window
     * @param contextGetter a button used to get the context of the scene to close
     */
    public static void exitApp(Button contextGetter){
        Stage stage = (Stage) contextGetter.getScene().getWindow();
        stage.close();
    }
}

