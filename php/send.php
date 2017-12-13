<?php

$db = mysqli_connect('localhost','root','', 'zorro');

$amount=$_POST['amount'];
$mob=$_POST['mob'];
$rmob=$_POST['rmob'];

$response=array();

$response['success']=false;
$response['zero_bal']=false;
$statement=mysqli_query($db,"select * from balance where mob='$rmob';");



if(mysqli_num_rows($statement)>0&& $mob!=$rmob)
{
$response['success']=true;	

$statement=mysqli_query($db,"select * from balance where mob='$mob';");
$row = mysqli_fetch_assoc($statement);
	
	if($row['bal'] >= $amount)
	{
$statement_reciever=mysqli_query($db,"update balance set bal=bal+'$amount' where mob='$rmob';");
$statement_sender=mysqli_query($db,"update balance set bal=bal-'$amount' where mob='$mob';");

 $r_trans_type = "Received";
      $r_user_type = "from ".$mob;
      mysqli_query($db,"INSERT into passbook_$rmob values (NOW(), '$r_trans_type', '$r_user_type', '$amount')");
	  
	    $s_trans_type = "Sent";
      $s_user_type = "to ".$rmob;
      mysqli_query($db,"INSERT into passbook_$mob values (NOW(), '$s_trans_type', '$s_user_type', '$amount')");
	}
	else
	{
		$response['zero_bal'] = true;
	}
}



echo json_encode($response);

?>