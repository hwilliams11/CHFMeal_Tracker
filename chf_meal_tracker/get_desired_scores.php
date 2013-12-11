<?php 
	
    #Ensure that the client has provided a value for "FirstNameToSearch" 
    if (isset($_POST["patientID"])&& $_POST["patientID"]!= ""){ 
         
        #Setup variables 
        $patientID = $_POST["patientID"]; 
        $calorie_budget=0;
		$sodium_budget=0; 
		switch ($patientID) {
			case "1":
				$calorie_budget=2000;
				$sodium_budget=2000;
				break;
			case "2":
				$calorie_budget=2000;
				$sodium_budget=2000;
				break;
			case "3":
				$calorie_budget=2000;
				$sodium_budget=2000;
				break;
		} 
		#update server db
		
		#insert new record to scores table. 
		$con=mysqli_connect("hit4.nimbus.cip.gatech.edu:3306","group1","group1","healthit");
		// Check connection
		if (mysqli_connect_errno())
		  {
		  echo "Failed to connect to MySQL: " . mysqli_connect_error();
		  }

		$result = mysqli_query($con,"INSERT INTO `scores` (creation_date, calories, sodium, patientID) VALUES( CURDATE(),$calorie_budget,$sodium_budget,$patientID )");


		mysqli_close($con);		
		
        #Build the result array (Assign keys to the values) 
        $result_data = array( 
            'calorie_budget' => $calorie_budget, 
            'sodium_budget' => $sodium_budget, 
            ); 

        #Output the JSON data 
        echo json_encode($result_data);  
    }else{ 
        echo "Could not complete query. Missing parameter";  
    } 
?>