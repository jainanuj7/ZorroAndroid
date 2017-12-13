<?php

$db = mysqli_connect('localhost','root','', 'zorro');
	
	$mob = $_POST['mob'];
	$pass = $_POST['pass'];

    $response=array();
	
	$statement=mysqli_query($db,"select pass from user_credentials where mob='$mob' ");
	
	
$response=array();
$response['success'] = false;

if(mysqli_num_rows($statement))
{

$row=mysqli_fetch_assoc($statement);
if($row['pass']==$pass)
{


$response['success']=true;
}
}
echo json_encode($response);
?>