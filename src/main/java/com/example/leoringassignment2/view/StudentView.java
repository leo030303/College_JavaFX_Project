package com.example.leoringassignment2.view;

import com.example.leoringassignment2.PopUpStage;
import com.example.leoringassignment2.controller.StudentController;
import com.example.leoringassignment2.model.StudentClass;
import com.example.leoringassignment2.model.StudentModule;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.time.LocalDate;

/**
 * class to create all GUI elements
 * @author Leo Ring
 */
public class StudentView {
    private final StudentController studentController = new StudentController();

    /**
     * method to create and return a complete gridPane
     * @return a gridPane
     */
    private GridPane createGridPane(){
        // creating and defining gridPane
        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("base");
        gridPane.setAlignment(Pos.CENTER_LEFT);
        gridPane.setHgap(20);
        gridPane.setVgap(10);
        gridPane.setMinWidth(400);
        return gridPane;
    }

    /**
     * method to create the base borderPane for the app, with all tabs included
     * @return base borderPane of app
     */
    public BorderPane createRoot(){
        TabPane tabPane = new TabPane();
        Tab studentTab = createStudentTab();
        Tab moduleTab = createModuleTab();
        Tab gradeTab = createGradeTab();
        Tab recordsTab = viewRecordsTab();
        Tab memoryLeakTab = createMemoryLeakTab();
        tabPane.getTabs().addAll(studentTab, moduleTab, gradeTab, recordsTab, memoryLeakTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tabPane);
        return borderPane;
    }

    /**
     * method to create the student tab
     * @return the student Tab
     */
    private Tab createStudentTab(){
        Tab studentTab = new Tab();
        studentTab.getStyleClass().add("tab");
        studentTab.setText("Manage Students");
        GridPane studentGridPane = createGridPane();
        // creating, defining and adding to studentGridPane all the Labels, TextFields, DatePickers and TextAreas
        studentGridPane.add(new Label("Enter Name"), 0, 0, 1, 1);
        TextField nameText = new TextField();
        studentGridPane.add(nameText, 1, 0, 3, 1);

        studentGridPane.add(new Label("Enter Student ID"), 0, 1, 1, 1);
        TextField sidText = new TextField();
        studentGridPane.add(sidText, 1, 1, 3, 1);

        studentGridPane.add(new Label("Enter Date of Birth"), 0, 2, 1, 1);
        DatePicker dobPicker = new DatePicker();
        dobPicker.setValue(LocalDate.now());
        studentGridPane.add(dobPicker, 1, 2, 3, 1);

        studentGridPane.add(new Label("Enter Current Semester"), 0, 3, 1, 1);
        TextField semesterText = new TextField();
        studentGridPane.add(semesterText, 1, 3, 3, 1);

        TextArea studentListArea = new TextArea();
        studentListArea.setEditable(false);
        studentListArea.setMinWidth(500);
        studentGridPane.add(studentListArea, 0, 6, 4, 1);

        // creating Add and Remove buttons and binding their methods
        Button addButton = new Button("Add");
        addButton.setMinWidth(190);
        addButton.setOnAction(actionEvent -> {
            studentController.addStudent(nameText, sidText, dobPicker, semesterText);
            studentController.listStudents(studentListArea);
        });
        studentGridPane.add(addButton, 0, 4, 2, 1);

        Button removeButton = new Button("Remove");
        removeButton.setMinWidth(190);
        removeButton.setOnAction(actionEvent -> {
            studentController.removeStudent(nameText, sidText, dobPicker, semesterText);
            studentController.listStudents(studentListArea);
        });
        studentGridPane.add(removeButton, 2, 4, 2, 1);

        // load and list data from file, then return completed studentGridPane
        studentController.listStudents(studentListArea);
        //Setting the content
        studentTab.setContent(studentGridPane);
        return studentTab;
    }

    /**
     * method for creating the module tab
     * @return the module tab
     */
    private Tab createModuleTab(){
        Tab moduleTab = new Tab();
        moduleTab.getStyleClass().add("tab");
        moduleTab.setText("Manage Modules");
        GridPane moduleGridPane = createGridPane();
        // creating, defining and adding to moduleGridPane all the Labels, TextFields, DatePickers and TextAreas
        moduleGridPane.add(new Label("Enter Module Name"), 0, 0, 1, 1);
        TextField nameText = new TextField();
        moduleGridPane.add(nameText, 1, 0, 3, 1);

        moduleGridPane.add(new Label("Enter Module Code"), 0, 1, 1, 1);
        TextField codeText = new TextField();
        moduleGridPane.add(codeText, 1, 1, 3, 1);

        moduleGridPane.add(new Label("Enter Module Semester"), 0, 2, 1, 1);
        TextField semesterText = new TextField();
        moduleGridPane.add(semesterText, 1, 2, 3, 1);

        TextArea moduleListArea = new TextArea();
        moduleListArea.setEditable(false);
        moduleListArea.setMinWidth(500);
        moduleGridPane.add(moduleListArea, 0, 5, 4, 1);

        // creating Add and Remove buttons and binding their methods
        Button addButton = new Button("Add");
        addButton.setMinWidth(190);
        addButton.setOnAction(actionEvent -> {
            studentController.addModule(nameText, codeText, semesterText);
            studentController.listModules(moduleListArea);
        });
        moduleGridPane.add(addButton, 0, 3, 2, 1);

        Button removeButton = new Button("Remove");
        removeButton.setMinWidth(190);
        removeButton.setOnAction(actionEvent -> {
            studentController.removeModule(nameText, codeText, semesterText);
            studentController.listModules(moduleListArea);
        });
        moduleGridPane.add(removeButton, 2, 3, 2, 1);

        // load and list data from file, then return completed moduleGridPane
        studentController.listModules(moduleListArea);
        //Setting the content
        moduleTab.setContent(moduleGridPane);
        return moduleTab;
    }

    /**
     * method for creating the grade tab
     * @return the grade tab
     */
    private Tab createGradeTab(){
        Tab gradeTab = new Tab();
        gradeTab.getStyleClass().add("tab");
        gradeTab.setText("Manage Grades");
        GridPane gradeGridPane = createGridPane();
        // add combo box to select student
        gradeGridPane.add(new Label("Please select the student: "), 0, 0, 1, 1);
        ComboBox<StudentClass> studentPicker = new ComboBox<>();
        studentPicker.setMaxWidth(200);
        //reload student picker values when the window is selected or deselected
        gradeGridPane.add(studentPicker, 1, 0, 3, 1);

        // add combo box to select module
        gradeGridPane.add(new Label("Please select the module: "), 0, 1, 1, 1);
        ComboBox<StudentModule> modulePicker = new ComboBox<>();
        modulePicker.setMaxWidth(200);
        //reload module picker values when the window is selected or deselected
        gradeGridPane.add(modulePicker, 1, 1, 3, 1);

        // add text field for module grade
        gradeGridPane.add(new Label("Please enter the grade for that module: "), 0, 2, 1, 1);
        TextField gradeField = new TextField();
        gradeGridPane.add(gradeField, 1, 2, 3, 1);

        TextArea gradeListArea = new TextArea();
        gradeListArea.setEditable(false);
        gradeListArea.setMinWidth(500);
        gradeGridPane.add(gradeListArea, 0, 5, 4, 1);

        gradeTab.setOnSelectionChanged(event -> {
            studentController.fillModulePicker(modulePicker);
            studentController.fillStudentPicker(studentPicker);
            studentController.listGrades(gradeListArea);
        });

        // creating Add and Remove buttons and binding their methods
        Button addButton = new Button("Add");
        addButton.setMinWidth(190);
        addButton.setOnAction(actionEvent -> {
            studentController.addGrade(studentPicker, modulePicker, gradeField);
            studentController.listGrades(gradeListArea);
        });
        gradeGridPane.add(addButton, 0, 3, 2, 1);

        Button removeButton = new Button("Remove");
        removeButton.setMinWidth(190);
        removeButton.setOnAction(actionEvent -> {
            studentController.removeGrade(studentPicker, modulePicker, gradeField);
            studentController.listGrades(gradeListArea);
        });
        gradeGridPane.add(removeButton, 2, 3, 2, 1);

        // load and list data from file, then return completed gradeGridPane
        studentController.listGrades(gradeListArea);
        //Setting the content
        gradeTab.setContent(gradeGridPane);
        return gradeTab;
    }

    /**
     * method for creating the memory leak tab
     * @return the memory leak tab
     */
    private Tab createMemoryLeakTab(){
        // create tab
        Tab moduleTab = new Tab();
        moduleTab.getStyleClass().add("tab");
        moduleTab.setText("Simulate Leak");

        // create gridPane
        GridPane moduleGridPane = createGridPane();

        // add leak button
        moduleGridPane.add(new Label("Overload the heap to cause a crash: "), 0, 0);
        Button leakButton = new Button("Simulate Memory Leak");
        moduleGridPane.add(leakButton, 1, 0);
        TextArea statTextArea = new TextArea();
        statTextArea.setEditable(false);
        moduleGridPane.add(statTextArea, 0, 1, 2, 2);
        leakButton.setOnAction(actionEvent -> studentController.simulateLeak(statTextArea));
        // add gridPane to tab
        moduleTab.setContent(moduleGridPane);
        return moduleTab;
    }

    /**
     * method for creating the tab for viewing student records
     * @return the view records tab
     */
    private Tab viewRecordsTab(){
        // create tab
        Tab recordsTab = new Tab();
        recordsTab.getStyleClass().add("tab");
        recordsTab.setText("View Records");

        // create gridPane
        GridPane recordsGridPane = createGridPane();

        // create and add combo box for selecting the student you want the records of and the text area to display them
        ComboBox<StudentClass> studentPicker = new ComboBox<>();
        studentPicker.setMaxWidth(200);
        CheckBox alphabeticCheck = new CheckBox();
        CheckBox numericCheck = new CheckBox();
        TextArea recordsArea = new TextArea();
        recordsArea.setEditable(false);
        recordsArea.setMinWidth(600);
        alphabeticCheck.setOnAction(actionEvent -> {
            studentController.manageCheckBox(alphabeticCheck, numericCheck);
            studentController.displayRecords(
                    studentPicker.getSelectionModel().getSelectedItem(),
                    recordsArea, alphabeticCheck, numericCheck);
        });
        numericCheck.setOnAction(actionEvent -> {
            studentController.manageCheckBox(numericCheck, alphabeticCheck);
            studentController.displayRecords(
                    studentPicker.getSelectionModel().getSelectedItem(),
                    recordsArea, alphabeticCheck, numericCheck);});
        // reload student list when tab is selected or deselected
        recordsTab.setOnSelectionChanged(event -> studentController.fillStudentPicker(studentPicker));
        studentPicker.setOnAction(actionEvent -> studentController.displayRecords(
                studentPicker.getSelectionModel().getSelectedItem(),
                recordsArea, alphabeticCheck, numericCheck));
        recordsGridPane.add(new Label("Please select the student: "), 0, 0);
        recordsGridPane.add(studentPicker, 0, 1, 1,2);
        recordsGridPane.add(new Text("Select the primary sort method."), 1, 0, 2, 1);
        recordsGridPane.add(new Label("Alphabetic Sort:"), 1, 1);
        recordsGridPane.add(alphabeticCheck, 2, 1);
        recordsGridPane.add(new Label("Numeric Sort:"), 1, 2);
        recordsGridPane.add(numericCheck, 2, 2);
        recordsGridPane.add(recordsArea, 0, 3, 3, 1);

        // add gridPane to tab
        recordsTab.setContent(recordsGridPane);
        return recordsTab;
    }

    /**
     * method for creating the VBox for the popup
     * @param message the text of the popup
     * @param decision whether to include a yes and no button or just an okay button
     * @param context for returning the users decision to the popup stage
     * @return the Vbox for the popup
     */
    public static VBox createPopUpContent(String message, Boolean decision, PopUpStage context){
        // create VBox
        VBox popUpVBox = new VBox();
        popUpVBox.getStyleClass().add("base");
        popUpVBox.setSpacing(20);

        // create popup content
        Text messageText = new Text(message);
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(20);
        // create and add buttons depending on decision parameter
        if (decision){
            Button yesButton = new Button("Okay");
            yesButton.setOnAction(actionEvent -> {
                // record user choice in confirmation flag
                context.setConfirmation(true);
                StudentController.exitApp(yesButton);
            });
            Button noButton = new Button("Cancel");
            noButton.setOnAction(actionEvent -> {
                // record user choice in confirmation flag
                context.setConfirmation(false);
                StudentController.exitApp(noButton);
            });
            buttonBox.getChildren().addAll(noButton, yesButton);
        }
        else{
            Button closeButton = new Button("Okay");
            closeButton.setOnAction(actionEvent -> {
                // record user choice in confirmation flag
                context.setConfirmation(false);
                StudentController.exitApp(closeButton);
            });
            buttonBox.getChildren().addAll(closeButton);
        }

        // add content to VBox and return
        popUpVBox.getChildren().addAll(messageText, buttonBox);
        return popUpVBox;
    }
}

