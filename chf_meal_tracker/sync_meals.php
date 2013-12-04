<?php

	require_once __DIR__ . '/db_connect.php';
	
	$json_string = $_POST['message']; 
    $json = json_decode($json_string,true);
	//$jst = print_r($json,TRUE);
	$file = 'output.txt';
	
	$db = new DB_CONNECT();
	
	/*
	$str = "";
	for($i=0;$i<count($json);$i++){
		foreach ($json[$i] as $key => $value) {
			$str .= $key.": ".$value."\r\n";
		}
	}
	*/
	$response = array();
	
	for($i=0;$i<count($json);$i++){

		$success = add_meal_to_db($db,$json[$i],$file);
		$res = array();
		$res["_NDB_No"] = $json[$i]["_NDB_No"];
		$res["_Type"] = get_meal_int($json[$i]["_Type"]);
		$res["_Date"] = $json[$i]["_Date"];
		$res["success"] = $success;

		array_push($response, $res);
	}
	
	$content = file_get_contents($file);
    $js =  json_encode($response);
	$content .= print_r($js,TRUE)."\r\n";
	file_put_contents($file, $content);
	echo $js;
	
	function add_meal_to_db2($meal,$file){
	
		
		$content = file_get_contents($file);
		$content .= print_r($meal,TRUE)."\r\n";
		file_put_contents($file, $content);
		
		//put meal in database
		$success = TRUE;
		
		//create return for this product
		return $success;
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
	function add_meal_to_db($db,$meal,$file){
	 
		$_NDB_No = $meal['_NDB_No'];
		$_Date = $meal['_Date'];
		$_Type = get_meal_int($meal['_Type']);
		$_Serving = $meal['_Serving'];

		
		
		$content = file_get_contents($file);
		
		// mysql inserting a new row
		$query = "INSERT INTO `meals` ( patientID, NDB_No, creation_date, serving_size, meal_type ) VALUES ( 0, $_NDB_No,'$_Date' ,$_Serving,$_Type)";
		$content .= "\r\n".$query."\r\n";
		file_put_contents($file, $content);;
		$result = mysql_query($query);
		
		
		if ($result) {
			return 1;
		} else {
			return 0;
		}
	}
?>