package com.example.leoringassignment2.model;


import java.sql.ResultSet;
import java.sql.SQLException;




/**
 * class to hold student module data
 * @author Leo Ring
 */
public class StudentModule {
    private String name;
    private String code;
    private int semester;

    /**
     *
     * @param _name name of module
     * @param _code code of module
     * @param _semester semester module is in
     */
    public StudentModule(String _name, String _code, int _semester){
        this.name = _name;
        this.code = _code;
        this.semester = _semester;
    }

    /**
     *
     * @param moduleResultSet dataset from database containing module data
     */
    public StudentModule(ResultSet moduleResultSet){
        try {
            this.name = moduleResultSet.getString("moduleName");
            this.code = moduleResultSet.getString("moduleId");
            this.semester = moduleResultSet.getInt("moduleSemester");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return module name
     */
    public String getName(){return this.name;}

    /**
     *
     * @return module code
     */

    public String getCode() {return code;}

    /**
     *
     * @return module semester
     */
    public int getSemester() {return semester;}

    /**
     *
     * @param semester new semester for module
     */
    public void setSemester(int semester) {this.semester = semester;}

    /**
     *
     * @param name new name for module
     */
    public void setName(String name) {this.name = name;}

    /**
     * method for displaying module data in records text area
     * @return formatted string of module data for records area
     */
    public String toRecord() {
        return String.format("%-20s %-10s %-10s \n",this.name, this.code, this.semester);
    }

    /**
     *
     * @return string to insert module into database
     */
    public String toSQLInsert(){
        return String.format("INSERT INTO Modules (moduleName, moduleId, moduleSemester)" +
                " VALUES ('%s', '%s', %d)", this.name, this.code, this.semester);
    }

    /**
     *
     * @return string to create Modules table
     */
    public static String toSQLCreateTable(){
        return """
                CREATE TABLE IF NOT EXISTS Modules (
                    moduleName varchar(255) NOT NULL,
                    moduleId varchar(255) NOT NULL,
                    moduleSemester int NOT NULL,
                    PRIMARY KEY (moduleId)
                );""";
    }

    /**
     *
     * @return formatted string of module data
     */
    @Override
    public String toString() {
        return String.format("Module: %-10sCode: %-10sSemester: %s\n",this.name, this.code, this.semester);
    }

}
