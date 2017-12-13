<?php
	$db = mysqli_connect("localhost", "root", "", "zorro");

	$mob = $_POST['mob'];
	$response=array();
	$statement = mysqli_query($db, "SELECT question from user_info where mob = '$mob'");
	$row = mysqli_fetch_assoc($statement);
	$response['que'] = $row['question'];
	echo json_encode($response);
?>
