<?php
$db = mysqli_connect('localhost','root','', 'zorro');
	
	$mob = $_POST['mob'];
	 $response=array();
	
	$statement_bal=mysqli_query($db,"select bal from balance where mob='$mob'");

	$row=mysqli_fetch_assoc($statement_bal);
	$response['bal']=$row['bal'];
	$statement_name=mysqli_query($db,"select name from user_info where mob='$mob'");
	$row=mysqli_fetch_assoc($statement_name);
	$response['name']=$row['name'];
	
echo json_encode($response);
?>