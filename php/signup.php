<?php
	$db = mysqli_connect('localhost','root','', 'zorro');
	
	$name = $_POST['name'];
	$mob = $_POST['mob'];
	$pass = $_POST['pass'];
	$gender = $_POST['gender'];
	$que = $_POST['que'];
	$ans = $_POST['ans'];


	$response=array();
	
	$response['success'] = false;
	$statement=mysqli_query($db,"select * from user_info where mob='$mob';");
	
	if(mysqli_num_rows($statement)>0)
	{
		$response['mob_exist']=true;
	}
	else
	{
		
		$response['mob_exist']=false;
	$statement_info = mysqli_query($db,"INSERT INTO user_info values('$name', '$mob', '$gender', '$que', '$ans');");
	$statement_credentials = mysqli_query($db, "INSERT INTO user_credentials values ('$mob', '$pass');");
	$statement_balance = mysqli_query($db, "INSERT INTO balance values ($mob,'0');");
	$statement_create_passbook=mysqli_query($db,"CREATE TABLE passbook_$mob (date_time datetime, trans_type char(20), user_type char(20), amt float) ");
	if($statement_info && $statement_credentials && $statement_balance)
		$response['success'] = true;
   else
		$response['success'] = false;
	}
	
	echo json_encode($response);

?>