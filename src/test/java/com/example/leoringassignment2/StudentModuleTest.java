package com.example.leoringassignment2;

import com.example.leoringassignment2.model.StudentModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentModuleTest {

    @Test
    void getName() {
        StudentModule studentModule1 = new StudentModule("name1", "ahj", 3);
        StudentModule studentModule2 = new StudentModule("name2", "j9", 8);
        StudentModule studentModule3 = new StudentModule("name3", "kal", 5);
        assertEquals(studentModule1.getName(), "name1");
        assertEquals(studentModule2.getName(), "name2");
        assertEquals(studentModule3.getName(), "name3");
    }

    @Test
    void getCode() {
        StudentModule studentModule1 = new StudentModule("name1", "ahj", 3);
        StudentModule studentModule2 = new StudentModule("name2", "j9", 8);
        StudentModule studentModule3 = new StudentModule("name3", "kal", 5);
        assertEquals(studentModule1.getCode(), "ahj");
        assertEquals(studentModule2.getCode(), "j9");
        assertEquals(studentModule3.getCode(), "kal");
    }

    @Test
    void getSemester() {
        StudentModule studentModule1 = new StudentModule("name1", "ahj", 3);
        StudentModule studentModule2 = new StudentModule("name2", "j9", 8);
        StudentModule studentModule3 = new StudentModule("name3", "kal", 5);
        assertEquals(studentModule1.getSemester(), 3);
        assertEquals(studentModule2.getSemester(), 8);
        assertEquals(studentModule3.getSemester(), 5);
    }

    @Test
    void setSemester() {
        StudentModule studentModule1 = new StudentModule("name1", "ahj", 3);
        StudentModule studentModule2 = new StudentModule("name2", "j9", 8);
        StudentModule studentModule3 = new StudentModule("name3", "kal", 5);
        studentModule1.setSemester(2);
        studentModule2.setSemester(6);
        studentModule3.setSemester(1);
        assertEquals(studentModule1.getSemester(), 2);
        assertEquals(studentModule2.getSemester(), 6);
        assertEquals(studentModule3.getSemester(), 1);
    }

    @Test
    void setName() {
        StudentModule studentModule1 = new StudentModule("name1", "ahj", 3);
        StudentModule studentModule2 = new StudentModule("name2", "j9", 8);
        StudentModule studentModule3 = new StudentModule("name3", "kal", 5);
        studentModule1.setName("ah");
        studentModule2.setName("tis");
        studentModule3.setName("sure");
        assertEquals(studentModule1.getName(), "ah");
        assertEquals(studentModule2.getName(), "tis");
        assertEquals(studentModule3.getName(), "sure");
    }

    @Test
    void toRecord() {
        StudentModule studentModule1 = new StudentModule("name1", "ahj", 3);
        StudentModule studentModule2 = new StudentModule("name2", "j9", 8);
        StudentModule studentModule3 = new StudentModule("name3", "kal", 5);
        assertEquals(studentModule1.toRecord(), "name1                ahj        3          \n");
        assertEquals(studentModule2.toRecord(), "name2                j9         8          \n");
        assertEquals(studentModule3.toRecord(), "name3                kal        5          \n");
    }

    @Test
    void toSQLInsert() {
        StudentModule studentModule1 = new StudentModule("name1", "ahj", 3);
        StudentModule studentModule2 = new StudentModule("name2", "j9", 8);
        StudentModule studentModule3 = new StudentModule("name3", "kal", 5);
        assertEquals(studentModule1.toSQLInsert(), "INSERT INTO Modules (moduleName, moduleId, moduleSemester) VALUES ('name1', 'ahj', 3)");
        assertEquals(studentModule2.toSQLInsert(), "INSERT INTO Modules (moduleName, moduleId, moduleSemester) VALUES ('name2', 'j9', 8)");
        assertEquals(studentModule3.toSQLInsert(), "INSERT INTO Modules (moduleName, moduleId, moduleSemester) VALUES ('name3', 'kal', 5)");
    }

    @Test
    void toSQLCreateTable() {
        assertEquals(StudentModule.toSQLCreateTable(), """
                CREATE TABLE IF NOT EXISTS Modules (
                    moduleName varchar(255) NOT NULL,
                    moduleId varchar(255) NOT NULL,
                    moduleSemester int NOT NULL,
                    PRIMARY KEY (moduleId)
                );""");
    }

    @Test
    void testToString() {
        StudentModule studentModule1 = new StudentModule("name1", "ahj", 3);
        StudentModule studentModule2 = new StudentModule("name2", "j9", 8);
        StudentModule studentModule3 = new StudentModule("name3", "kal", 5);
        assertEquals(studentModule1.toString(), "Module: name1     Code: ahj       Semester: 3\n");
        assertEquals(studentModule2.toString(), "Module: name2     Code: j9        Semester: 8\n");
        assertEquals(studentModule3.toString(), "Module: name3     Code: kal       Semester: 5\n");
    }
}