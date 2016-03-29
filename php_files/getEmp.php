<?php
$id = $_GET['id'];

require_once('dbConnect.php');

//Creating sql query
$sql = "SELECT employees.FirstName, employees.LastName,employees.Email, titles.Title,titles.FromDate,titles.ToDate 
    FROM employees join titles on employees.`EmployeeID`=titles.EmployeeID where employees.EmployeeID=" . $id;

//getting result 
$r = mysqli_query($con, $sql);

//creating a blank array 
$result = array();

//looping through all the records fetched
while ($row = mysqli_fetch_array($r)) {
    $end = $row['ToDate'];
    if ($end == null) {
        $end = "Current";
    }
    //Pushing name and id in the blank array created 
    array_push($result, array(
        "name" => $row['FirstName'],
        "surname" => $row['LastName'],
        "email" => $row['Email'],
        "title" => $row['Title'],
        "start" => $row['FromDate'],
        "end" => $end
    ));
}

//Displaying the array in json format 
echo json_encode(array('result' => $result));

mysqli_close($con);
