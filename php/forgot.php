<?php
	$db = mysqli_connect("localhost", "root", "", "zorro");

	$mob = $_POST['mob'];
	$response=array();
	$statement = mysqli_query($db, "SELECT mob from user_info where mob = $mob");
	if(mysqli_num_rows($statement) > 0)
		$response['success'] = true;
  else
		$response['success'] = false;

	echo json_encode($response);

?>
