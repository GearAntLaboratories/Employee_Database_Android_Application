<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    //Getting values
    $id = $_POST['EmployeeID'];
    require_once("Includes/db.php");

    EmployeeDB::getInstance()->delete_employee($id);
}