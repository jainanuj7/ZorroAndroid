<?php 

$db = mysqli_connect('localhost','root','', 'zorro');
$mob=$_POST['mob'];

$response=array();

$response['success']=true;

$statement=mysqli_query($db,"select * from passbook_$mob;");

$i=1;

while($row=mysqli_fetch_assoc($statement))
{
	$response['str'.$i]=$row['trans_type']." ".$row['user_type'];
	$response['other_str'.$i]=$row['date_time'];
	$response['another_str'.$i]="Rs.".$row['amt'];
	$i=$i+1;
	
}




$response['iter']=$i;

echo json_encode($response);
?>