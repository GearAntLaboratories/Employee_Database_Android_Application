<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    //Getting values
    $un = $_POST['Username'];
    $pw = $_POST['Password'];
    require_once("Includes/db.php");

    $logonSuccess = false;
    //verify user's credentials
    $logonSuccess = (EmployeeDB::getInstance()->verify_employee_credentials($_POST['Username'], $_POST['Password']));
    if (!$logonSuccess)
        $logonSuccess = false;
    $nRights = mysqli_fetch_array(EmployeeDB::getInstance()->get_access_rights_by_user($un));

    if ($nRights[0] == 1) {
        $rights = "1";
    } else
        $rights = "0";

    //creating a blank array 
    $result = array();

    array_push($result, array(
        "username" => $un,
        "success" => $logonSuccess,
        "rights" => $rights
    ));

    echo json_encode(array('result' => $result));
}