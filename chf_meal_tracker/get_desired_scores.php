<?php

	require_once __DIR__ . '/db_connect.php';
	
	$json_string = $_POST['message']; 
    $request_info= json_decode($json_string,true);
	$jst = print_r($request_info,TRUE);
	$file = 'output.txt';
	
		
	$db = new DB_CONNECT();
	

	$response = array();
	$test = array();
	//$test["Hello"]="Bye";
	//array_push($response,$test);
	

	if( count($request_info)> 0 ){
	
		$calories = array();
		$sodium = array();
	
		
		for($i=0;$i<count($request_info);$i++){

			$res=array();
			//$res["patient"] = $request_info["patient"]
			//$res["date"]=$request_info["date"];
			//$res["calories"]=$calories["date"];
			//$res["sodium"]=$calories["sodium"];
			
			$res = get_scores($request_info[$i],$file);
			
			array_push($response,$res);
		}
	}
	$content = file_get_contents($file);
    $js =  json_encode($response);
	$content .= print_r($js,TRUE)."\r\n";
	file_put_contents($file, $content);
	
	echo $js;

	
	function get_scores($request_info,$file){
	
	
		$res = array();
		$id = $request_info["patientID"];
		
		$date = DateTime::createFromFormat('Y-m-d', $request_info["date"])->format('Y-m-d');
			
		
		$query = "SELECT * FROM `scores` WHERE creation_date=\"$date\" AND patientID=$id";
		add_to_file($file,$query);
		
		$result = mysql_query($query);

		if($result){
			if (mysql_num_rows($result) > 0) {
			
				$row = mysql_fetch_assoc($result);
				$data = $row['creation_date']." ".$row['calories']." ".$row['sodium']." ".$row['patientID']."\n";

				add_to_file($file,$data);
				
				$res["patient"] = $id;
				$res["date"]=$date;
				$res["calories"]=$row["calories"];
				$res["sodium"]=$row["sodium"];
			}
		}
		
	
		return $res;
	}
	function add_to_file($file,$new_content){
	
		$content = file_get_contents($file);
		$content .= "\n$new_content\n";
		file_put_contents($file, $content);
		
	}

?>