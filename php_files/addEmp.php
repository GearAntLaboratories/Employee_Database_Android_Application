<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Getting values
                $fn = $_POST['FirstName'];
                $ln = $_POST['LastName'];
                $hd = $_POST['FromDate'];
                $em = $_POST['Email'];
                $title = $_POST['Title'];
                $acclevel = $_POST['AccessLevel'];
                $un = $_POST['Username'];
                $pw = $_POST['Password'];
                
                if ($acclevel=="1")$al=1; else $al=0;
		
                require_once("Includes/db.php");
                EmployeeDB::getInstance()->add_employee($fn,$ln,$em,$un, $pw, $hd,$title,$al);
	}