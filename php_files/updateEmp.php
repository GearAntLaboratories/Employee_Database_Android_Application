<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    //Getting values
    $id = $_POST['EmployeeID'];
    $fn = $_POST['FirstName'];
    $ln = $_POST['LastName'];
    $em = $_POST['Email'];
    $acclevel = $_POST['AccessLevel'];


    if ($acclevel == "1")
        $al = 1;
    else
        $al = 0;

    require_once("Includes/db.php");
    EmployeeDB::getInstance()->update_record($id, $fn, $ln, $em, $al);
}