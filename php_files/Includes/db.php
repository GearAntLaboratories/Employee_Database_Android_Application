<?php

class EmployeeDB extends mysqli {

    // single instance of self shared among all instances
    private static $instance = null;
    // db connection config vars
    private $user = "grantfpu_admin";
    private $pass = "headcore2";
    private $dbName = "grantfpu_employeeDB";
    private $dbHost = "localhost";

    //This method must be static, and must return an instance of the object if the object
    //does not already exist.
    public static function getInstance() {
        if (!self::$instance instanceof self) {
            self::$instance = new self;
        }
        return self::$instance;
    }

    // The clone and wakeup methods prevents external instantiation of copies of the Singleton class,
    // thus eliminating the possibility of duplicate objects.
    public function __clone() {
        trigger_error('Clone is not allowed.', E_USER_ERROR);
    }

    public function __wakeup() {
        trigger_error('Deserializing is not allowed.', E_USER_ERROR);
    }

    // private constructor
    private function __construct() {
        parent::__construct($this->dbHost, $this->user, $this->pass, $this->dbName);
        if (mysqli_connect_error()) {
            exit('Connect Error (' . mysqli_connect_errno() . ') '
                    . mysqli_connect_error());
        }
        parent::set_charset('utf-8');
    }

    //verifies employees username and password exist and match
    public function verify_employee_credentials($name, $password) {
        $name = $this->real_escape_string($name);
        $password = $this->real_escape_string($password);
        $result = $this->query("SELECT 1 FROM login WHERE Username='" . $name . "' AND Password = '" . $password . "'");
        return $result->data_seek(0);
    }

    public function get_details_by_employee_id($empID) {
        return $this->query("SELECT FirstName,LastName,Email,EmployeeID FROM employees WHERE EmployeeID=" . $empID);
    }

    public function get_job_history_by_employee_id($empID) {
        return $this->query("SELECT title, FromDate, ToDate FROM titles WHERE EmployeeID=" . $empID);
    }

    public function get_current_job($empID) {
        return $this->query("SELECT title FROM titles WHERE EmployeeID=" . $empID . " and ToDate is NULL");
    }

    public function get_access_rights_by_user($user) {
        return $this->query("SELECT AccessLevel FROM employees INNER JOIN
                              login ON employees.EmployeeID=login.EmployeeID WHERE login.Username='" . $user . "'");
    }

    public function get_access_rights_by_ID($empID) {
        return $this->query("SELECT AccessLevel FROM employees INNER JOIN
                              login ON employees.EmployeeID=login.EmployeeID WHERE login.EmployeeID=" . $empID);
    }

    public function delete_employee($empID) {
        $this->query("DELETE FROM titles WHERE EmployeeID =  " . $empID);
        $this->query("DELETE FROM login WHERE EmployeeID = " . $empID);
        $this->query("DELETE FROM employees WHERE EmployeeID = " . $empID);
    }

    //returns all employees with current jobs only
    public function get_all_employees() {
        return $this->query("SELECT e.FirstName,e.LastName,e.Email,t.title,e.EmployeeID FROM employees AS e INNER JOIN
                            titles AS t ON e.EmployeeID = t.EmployeeID WHERE t.ToDate is NULL;");
    }

    //formats the date for mysql
    function format_date_for_sql($date) {
        if ($date == "") {
            return null;
        } else {
            $dateParts = date_parse($date);
            return $dateParts["year"] * 10000 + $dateParts["month"] * 100 + $dateParts["day"];
        }
    }

    function add_employee($fName, $lName, $eMail, $userName, $passWord, $hDate, $cPosition, $accessLevel) {

        $this->query("INSERT INTO `employees` (`FirstName`, `LastName`, `HireDate`, `Email`, `AccessLevel`) VALUES
            ('" . $fName . "', '" . $lName . "', '" . $hDate . "', '" . $eMail . "', " . $accessLevel . ")");

        $empIDarr = $this->query("SELECT EmployeeID FROM employees WHERE FirstName =\"" . $fName . "\" and LastName=\"" . $lName . "\"");

        $empID = mysqli_fetch_array($empIDarr)["EmployeeID"];




        $this->query("INSERT INTO login (EmployeeID,Username,Password) VALUES            
                ('" . $empID . "','" . $userName . "','" . $passWord . "')");


        echo "empid=" . $empID . " cpos=" . $cPosition . " hdate=" . $hDate . "that is all";

        $this->query("INSERT INTO titles (EmployeeID,Title,FromDate,ToDate) VALUES
                ('" . $empID . "','" . $cPosition . "', '" . $hDate . "', NULL)");
    }

    function update_record($empID, $fName, $lName, $eMail, $accessLevel) {
        $this->query("UPDATE employees SET FirstName = '" . $fName . "', LastName = '" . $lName . "',
                        Email = '" . $eMail . "', AccessLevel = " . $accessLevel . " WHERE EmployeeID = " . $empID);
    }

}

?>