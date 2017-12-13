<?php 

$db = mysqli_connect('localhost','root','', 'zorro');

$amount=$_POST['amount'];
$mob=$_POST['mob'];

$response=array();
$response['success']=false;

$statement_add=mysqli_query($db,"update balance set bal=bal+'$amount' where mob='$mob';");
 $s_trans_type = "Money Added";
      $s_user_type = "";
      mysqli_query($db,"INSERT into passbook_$mob values (NOW(), '$s_trans_type', '$s_user_type', '$amount')");
if($statement_add)
{
$response['success']=true;	
}


echo json_encode($response);
?>