package com.example.grant.employeeapp;

/**
 * Created by grant on 12/2/2015.
 */
public class Config {
    //Address of our scripts of the CRUD
//    public static final String URL_CONNECT="http://192.168.0.44:8080/EmployeeDatabase/connect.php";
//    public static final String URL_GET_ALL = "http://192.168.0.44:8080/EmployeeDatabase/getAllEmp.php";
//    public static final String URL_GET_EMP = "http://192.168.0.44:8080/EmployeeDatabase/getEmp.php?id=";
//    public static final String URL_ADD_EMP = "http://192.168.0.44:8080/EmployeeDatabase/addEmp.php";
//    public static final String URL_UPDATE_EMP = "http://192.168.0.44:8080/EmployeeDatabase/updateEmp.php";
//    public static final String URL_DELETE_EMP = "http://192.168.0.44:8080/EmployeeDatabase/deleteEmp.php?id=";

    public static final String URL_CONNECT="http://www.grantgotwald.me/employee-database/android/connect.php";
    public static final String URL_GET_ALL = "http://www.grantgotwald.me/employee-database/android/getAllEmp.php";
    public static final String URL_GET_EMP = "http://www.grantgotwald.me/employee-database/android/getEmp.php?id=";
    public static final String URL_ADD_EMP = "http://www.grantgotwald.me/employee-database/android/addEmp.php";
    public static final String URL_UPDATE_EMP = "http://www.grantgotwald.me/employee-database/android/updateEmp.php";
    public static final String URL_DELETE_EMP = "http://www.grantgotwald.me/employee-database/android/deleteEmp.php?id=";

    //Keys that will be used to send the request to php scripts
    public static final String KEY_EMP_ID = "EmployeeID";
    public static final String KEY_EMP_NAME = "FirstName";
    public static final String KEY_EMP_LAST_NAME="LastName";
    public static final String KEY_EMP_JOB_TITLE = "Title";
    public static final String KEY_EMP_HIRE_DATE = "FromDate";
    public static final String KEY_EMP_ACCESS_LEVEL="AccessLevel";
    public static final String KEY_EMP_EMAIL = "Email";
    public static final String KEY_USERNAME = "Username";
    public static final String KEY_PASSWORD = "Password";


    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_LAST_NAME="surname";
    public static final String TAG_USERNAME="username";
    public static final String TAG_SUCCESS="success";
    public static final String TAG_EMAIL="email";
    public static final String TAG_TITLE="title";
    public static final String TAG_START_DATE="start";
    public static final String TAG_END_DATE="end";

    //employee id to pass with intent
    public static final String EMP_ID = "emp_id";
}
