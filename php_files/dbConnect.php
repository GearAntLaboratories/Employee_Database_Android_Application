<?php

define('HOST', 'localhost');
define('USER', 'grantfpu_admin');
define('PASS', 'headcore2');
define('DB', 'grantfpu_employeeDB');

$con = mysqli_connect(HOST, USER, PASS, DB) or die('Unable to Connect');
