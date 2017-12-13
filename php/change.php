<?php
	$db = mysqli_connect("localhost", "root", "", "zorro");

	$mob = $_POST['mob'];
	$newpass = $_POST['pass'];
	$ans = $_POST['ans'];
	$response=array();
	$statement_ans = mysqli_query($db, "SELECT answer from user_info where mob = '$mob'");
	$row = mysqli_fetch_assoc($statement_ans);
	if($row['answer'] == $ans)
	{
		$response['success'] = true;
		mysqli_query($db, "UPDATE user_credentials set pass='$newpass' WHERE mob = '$mob'");
	}
	
	else
		$response['success'] = false;
	
	echo json_encode($response);
?>
