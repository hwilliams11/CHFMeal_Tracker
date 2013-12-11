<?php

// Method: POST, PUT, GET etc
// Data: array("param" => "value") ==> index.php?param=value
/*
API Name :  SendMessage 
Description: Send an alert message to a physician
Method : POST

Params (case sensitive):
RecipientID: (recipient physician ID (e.g. 1008))
PatientID: (patient id (e.g. 1394))
Body: (Message/Notification body)
Subject: (Message/Notification Subject)
Priority: High/Medium/Low (based on alrety priority)

*/
$con=mysqli_connect("hit4.nimbus.cip.gatech.edu:3306","group1","group1","healthit");
// Check connection
if (mysqli_connect_errno())
{
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$_date='2013-12-11';
// mysql inserting a new row
$query = "SELECT SUM(calories) AS calories_sum, SUM(sodium) AS sodium_sum from meals where creation_date='$_date'";
$file = 'output.txt';
$content = file_get_contents($file);
$content .= "\r\n".$query."\r\n";

file_put_contents($file, $content);
$result = mysqli_query($con,$query);
		
if($row = mysqli_fetch_array($result))
{
  echo $row["calories_sum"]." ".$row["sodium_sum"];
  $desired_calories=100;
  $desired_sodium=600;
  if($row["calories_sum"]>$desired_calories){
	echo "above!";
  }
  if($row["sodium_sum"]<$desired_sodium){
	echo "below!";
  }
  
 }

mysqli_close($con);
function send_meal_alert($RecipientID,$PatientID,$msg_body){
	$url="http://hit2.nimbus.cip.gatech.edu/HIESvc/HIE.svc/SendMessage";
	$method="POST";
	$data=array("RecipientID" => $RecipientID,"PatientID"=>$PatientID,"Body"=>$msg_body,"Subject"=>"Meal Alert","Priority"=>"high");

	$result=CallAPI($method, $url, json_encode($data));

	echo $result;
}
function CallAPI($method, $url, $data = false)
{
    $ch = curl_init($url);                                                                      
	curl_setopt($ch, CURLOPT_CUSTOMREQUEST, $method);                                                                     
	curl_setopt($ch, CURLOPT_POSTFIELDS, $data);                                                                  
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);                                                                      
	curl_setopt($ch, CURLOPT_HTTPHEADER, array(                                                                          
		'Content-Type: application/json',                                                                                
		'Content-Length: ' . strlen($data))                                                                       
	);                      

    return curl_exec($ch);
}
?>