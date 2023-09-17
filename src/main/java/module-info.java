module com.example.leoringassignment1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.sql;
    requires java.management;


    opens com.example.leoringassignment2 to javafx.fxml;
    exports com.example.leoringassignment2;
    exports com.example.leoringassignment2.controller;
    opens com.example.leoringassignment2.controller to javafx.fxml;
    exports com.example.leoringassignment2.model;
    opens com.example.leoringassignment2.model to javafx.fxml;
    exports com.example.leoringassignment2.view;
    opens com.example.leoringassignment2.view to javafx.fxml;
}