package com.example.leoringassignment2;

import com.example.leoringassignment2.model.StudentClass;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class StudentClassTest {
    @Test
    void getName() {
        StudentClass student1 = new StudentClass("name1", "1", new Date(2014,10,24), 1);
        StudentClass student2 = new StudentClass("name2", "b2", new Date(2001,9,11), 7);
        StudentClass student3 = new StudentClass("name3", "3(", new Date(2024,7,2), 3);
        assertEquals(student1.getName(), "name1");
        assertEquals(student2.getName(), "name2");
        assertEquals(student3.getName(), "name3");
    }

    @Test
    void getStudentId() {
        StudentClass student1 = new StudentClass("name1", "1", new Date(2014,10,24), 1);
        StudentClass student2 = new StudentClass("name2", "b2", new Date(2001,9,11), 7);
        StudentClass student3 = new StudentClass("name3", "3(", new Date(2024,7,2), 3);
        assertEquals(student1.getStudentId(), "1");
        assertEquals(student2.getStudentId(), "b2");
        assertEquals(student3.getStudentId(), "3(");
    }

    @Test
    void getDateOfBirth() {
        StudentClass student1 = new StudentClass("name1", "1", new Date(2014,10,24), 1);
        StudentClass student2 = new StudentClass("name2", "b2", new Date(2001,9,11), 7);
        StudentClass student3 = new StudentClass("name3", "3(", new Date(2024,7,2), 3);
        assertEquals(student1.getDateOfBirth(), new Date(2014,10,24));
        assertEquals(student2.getDateOfBirth(), new Date(2001,9,11));
        assertEquals(student3.getDateOfBirth(), new Date(2024,7,2));
    }

    @Test
    void getCurrentSemester() {
        StudentClass student1 = new StudentClass("name1", "1", new Date(2014,10,24), 1);
        StudentClass student2 = new StudentClass("name2", "b2", new Date(2001,9,11), 7);
        StudentClass student3 = new StudentClass("name3", "3(", new Date(2024,7,2), 3);
        assertEquals(student1.getCurrentSemester(), 1);
        assertEquals(student2.getCurrentSemester(), 7);
        assertEquals(student3.getCurrentSemester(), 3);
    }

    @Test
    void setName() {
        StudentClass student1 = new StudentClass("name1", "1", new Date(2014,10,24), 1);
        StudentClass student2 = new StudentClass("name2", "b2", new Date(2001,9,11), 7);
        StudentClass student3 = new StudentClass("name3", "3(", new Date(2024,7,2), 3);
        student1.setName("frank");
        student2.setName("67");
        student3.setName("e^H");
        assertEquals(student1.getName(), "frank");
        assertEquals(student2.getName(), "67");
        assertEquals(student3.getName(), "e^H");
    }

    @Test
    void setDateOfBirth() {
        StudentClass student1 = new StudentClass("name1", "1", new Date(2014,10,24), 1);
        StudentClass student2 = new StudentClass("name2", "b2", new Date(2001,9,11), 7);
        StudentClass student3 = new StudentClass("name3", "3(", new Date(2024,7,2), 3);
        student1.setDateOfBirth(new Date(2002,8,22));
        student2.setDateOfBirth(new Date(1994,3,1));
        student3.setDateOfBirth(new Date(1492,2,29));
        assertEquals(student1.getDateOfBirth(), new Date(2002,8,22));
        assertEquals(student2.getDateOfBirth(), new Date(1994,3,1));
        assertEquals(student3.getDateOfBirth(), new Date(1492,2,29));
    }

    @Test
    void setCurrentSemester() {
        StudentClass student1 = new StudentClass("name1", "1", new Date(2014,10,24), 1);
        StudentClass student2 = new StudentClass("name2", "b2", new Date(2001,9,11), 7);
        StudentClass student3 = new StudentClass("name3", "3(", new Date(2024,7,2), 3);
        student1.setCurrentSemester(2);
        student2.setCurrentSemester(56);
        student3.setCurrentSemester(23);
        assertEquals(student1.getCurrentSemester(), 2);
        assertEquals(student2.getCurrentSemester(), 56);
        assertEquals(student3.getCurrentSemester(), 23);
    }

    @Test
    void toSQLInsert() {
        StudentClass student1 = new StudentClass("name1", "1", new Date(2014,10,24), 1);
        StudentClass student2 = new StudentClass("name2", "b2", new Date(2001,9,11), 7);
        StudentClass student3 = new StudentClass("name3", "3(", new Date(2024,7,2), 3);
        assertEquals(student1.toSQLInsert(), "INSERT INTO Students (studentName, studentId, dateOfBirth, currentSemester)" +
                " VALUES ('name1', '1', '3914-11-24', 1)");
        assertEquals(student2.toSQLInsert(), "INSERT INTO Students (studentName, studentId, dateOfBirth, currentSemester)" +
                " VALUES ('name2', 'b2', '3901-10-11', 7)");
        assertEquals(student3.toSQLInsert(), "INSERT INTO Students (studentName, studentId, dateOfBirth, currentSemester)" +
                " VALUES ('name3', '3(', '3924-08-02', 3)");
    }

    @Test
    void toSQLCreateTable() {
        assertEquals(StudentClass.toSQLCreateTable(), """
                CREATE TABLE IF NOT EXISTS Students (
                    studentName varchar(255) NOT NULL,
                    studentId varchar(255) NOT NULL,
                    dateOfBirth date NOT NULL,
                    currentSemester int NOT NULL,
                    PRIMARY KEY (studentId)
                );""");
    }

    @Test
    void testToString() {
        StudentClass student1 = new StudentClass("name1", "1", new Date(2014,10,24), 1);
        StudentClass student2 = new StudentClass("name2", "b2", new Date(2001,9,11), 7);
        StudentClass student3 = new StudentClass("name3", "3(", new Date(2024,7,2), 3);
        assertEquals(student1.toString(), "Name: name1     ID: 1     DOB: 3914-11-24  Semester: 1\n");
        assertEquals(student2.toString(), "Name: name2     ID: b2    DOB: 3901-10-11  Semester: 7\n");
        assertEquals(student3.toString(), "Name: name3     ID: 3(    DOB: 3924-08-02  Semester: 3\n");
    }


}