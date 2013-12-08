<?php

		require_once __DIR__ . '/db_connect.php';

		// connecting to db
		$db = new DB_CONNECT();
		
		//$fmt = DateTime::createFromFormat('m-d-Y', '10-16-2003');
		//$fmt = DateTime::createFromFormat('Y-m-d', '2003-10-16');
		//must be y-m-d
		
		$file = "output.txt";
		$minDate = DateTime::createFromFormat('m-d-Y', '12-03-2013')->format('Y-m-d');
		$maxDate = DateTime::createFromFormat('m-d-Y', '12-10-2013')->format('Y-m-d');
		
		$a = DateTime::createFromFormat('m-d-Y', '10-03-2012')->format('Y-m-d');
		$b = DateTime::createFromFormat('m-d-Y', '02-10-2013')->format('Y-m-d');
		
		echo '10-03-2012' > '02-10-2013' ."<br/>";
		echo $a < $b."<br/>";
		
		echo "$minDate  $maxDate<br/>";
		
		$query = "SELECT * FROM `scores` WHERE creation_date>='$minDate' AND creation_date<='$maxDate'";
		
		//$query = "SELECT * FROM `scores` WHERE patientID=0";
		
		$content = file_get_contents($file);
		$content .= "\r\n".$query."\r\n";
		
		$result = mysql_query($query) or die(mysql_error());
		


		if (mysql_num_rows($result) > 0) {
		
			while( $row = mysql_fetch_assoc($result)){
				
				
				echo $row['creation_date']." ";
				echo $row['calories']." ";
				echo $row['sodium']." ";
				echo $row['patientID'];
				echo"<br/>";
				
			}
		}
		
		$c = count($result);
	
		echo "query: $query<br/>";
		echo "result:$c<br/>";
		$content .= "\r\nresults: $result\r\n";
		file_put_contents($file, $content);;
		
?>