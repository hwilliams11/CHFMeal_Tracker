<?php

	
	
	$json_string = $_POST['message']; 
	//$json_string='[{"_Date":"2013-12-10","_Type":"BREAKFAST","_NDB_No":"1158","calories":"3000","sodium":"600","_Serving":"1.0"}]';
    $json = json_decode($json_string,true);
	
	$file = 'output.txt';
	
	
	
	
	$response = array();
	
	for($i=0;$i<count($json);$i++){
		echo "in loop";
		$success = add_meal_to_db($json[$i],$file);
		$res = array();
		$res["_NDB_No"] = $json[$i]["_NDB_No"];
		$res["_Type"] = get_meal_int($json[$i]["_Type"]);
		$res["_Date"] = $json[$i]["_Date"];
		$_date=$res["_Date"];
		$res["success"] = $success;

		array_push($response, $res);
	}
	
	$content = file_get_contents($file);
    $js =  json_encode($response);
	$content .= print_r($js,TRUE)."\r\n";
	file_put_contents($file, $content);
	echo $js;
	
	//send alert if necessary
	$con=mysqli_connect("hit4.nimbus.cip.gatech.edu:3306","group1","group1","healthit");
	// Check connection
	if (mysqli_connect_errno())
	{
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	$patientID=3;
	$query = "SELECT SUM(calories) AS calories_sum, SUM(sodium) AS sodium_sum from meals where creation_date='$_date' and patientID=$patientID";
	$file = 'output.txt';
	$content = file_get_contents($file);
	$content .= "\r\n".$query."\r\n";

	file_put_contents($file, $content);
	$result = mysqli_query($con,$query);
	$act_calories=0;
	$act_sodium=0;		
	if($row = mysqli_fetch_array($result))
	{
	  echo $row["calories_sum"]." ".$row["sodium_sum"];
	  echo "<br>";
	  $act_calories=$row["calories_sum"];
	  $act_sodium=$row["sodium_sum"];
	} 
	$query = "SELECT calories, sodium from scores where creation_date='$_date' and patientID=$patientID";
	$file = 'output.txt';
	$content = file_get_contents($file);
	$content .= "\r\n".$query."\r\n";

	file_put_contents($file, $content);
	$result = mysqli_query($con,$query);
	$desired_calories=10000000;
	$desired_sodium=10000000;		
	if($row = mysqli_fetch_array($result))
	{
	  echo $row["calories"]." ".$row["sodium"];
	  echo "<br>";
	  $desired_calories=$row["calories"];
	  $desired_sodium=$row["sodium"];
	} 
	mysqli_close($con);
	if($desired_calories<$act_calories||$desired_sodium<$act_sodium){
		$RecipientID=1008;
		$PatientID=1394;
		$msg_body="";
		if($desired_calories<$act_calories){
			$msg_body=$msg_body."Calories intake is above budget $desired_calories calories on [$_date] for ".($act_calories-$desired_calories)." calories.";
			echo $msg_body;
		}
		if($desired_sodium<$act_sodium){
			$msg_body=$msg_body."Sodium intake is above budget $desired_sodium mg on [$_date] for ".($act_sodium-$desired_sodium)." mg.";
			echo $msg_body;
		}
		send_meal_alert($RecipientID,$PatientID,$msg_body);
	}
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
	function get_meal_int($type){
	
		if( $type == 'BREAKFAST'){
			return 0;
		}else if($type == 'LUNCH'){
			return 1;
		}else if($type == 'DINNER'){
			return 2;
		}else if($type == 'SNACK'){
			return 3;
		}
	}
	function add_meal_to_db($meal,$file){
	 
		$_NDB_No = $meal['_NDB_No'];
		$_Date = $meal['_Date'];
		$_Type = get_meal_int($meal['_Type']);
		$_Serving = $meal['_Serving'];
		$calories = $meal['calories'];
		$sodium = $meal['sodium'];

		
		
		$content = file_get_contents($file);
		#insert new record to scores table. 
		$con=mysqli_connect("hit4.nimbus.cip.gatech.edu:3306","group1","group1","healthit");
		// Check connection
		if (mysqli_connect_errno())
		  {
		  echo "Failed to connect to MySQL: " . mysqli_connect_error();
		  }

		// mysql inserting a new row
		$query = "INSERT INTO `meals` ( patientID, NDB_No, creation_date, serving_size, meal_type, calories, sodium ) VALUES ( 3, $_NDB_No,'$_Date' ,$_Serving,$_Type, $calories, $sodium)";
		$content .= "\r\n".$query."\r\n";
		file_put_contents($file, $content);
		$result = mysqli_query($con,$query);
		

		mysqli_close($con);

		if ($result) {
			return 1;
		} else {
			return 0;
		}
	}
?>