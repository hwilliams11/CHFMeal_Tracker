<?php

	ob_clean();
	send_scores_report();
	
	function send_scores_report(){
	
		require_once __DIR__ . '/db_connect_remote.php';
		require_once __DIR__ . '/helper.php';
		//require_once __DIR__ . '/../tcpdf_include.php';
		require_once __DIR__ . '/../tcpdf_6_0_051/tcpdf/tcpdf.php';
		require_once("../pChart2.1.3/pChart2.1.3/class/pDraw.class.php"); 
		require_once("../pChart2.1.3/pChart2.1.3/class/pImage.class.php"); 
		require_once("../pChart2.1.3/pChart2.1.3/class/pData.class.php");
		
		$db = new DB_CONNECT();
		
		$data = get_all_scores();
		//echo print_r($data,true);
		create_plots($data);
		create_report($data);
	
	}
	/*************************************
	*get all the actual and ideal scores
	**************************************/
	function get_all_scores(){
	
		
		$query = 'select creation_date,sum(calories) as total_calories,sum(sodium) as total_sodium from meals group by creation_date';
		
		$dates=array();
		$actual_calories_vals=array();
		$ideal_calories_vals=array();
		$actual_sodium_vals=array();
		$ideal_sodium_vals=array();
		
		$result = mysql_query( $query );
		$data = array();
		if (mysql_num_rows($result) > 0) {
			
			while( $row = mysql_fetch_assoc($result)){
			
				
				$date = $row['creation_date'];
				$actual_calories = $row['total_calories'];
				$actual_sodium = $row['total_sodium'];
				$query = 'select calories,sodium from scores where creation_date="'.$date.'"';
				$result2 = mysql_query( $query );
				array_push($dates,$date);
				array_push($actual_calories_vals,$actual_calories);
				array_push($actual_sodium_vals,$actual_sodium);
				
				if (mysql_num_rows($result2) > 0) {
			
						$row2 = mysql_fetch_assoc($result2);
						$ideal_calories = $row2['calories'];
						$ideal_sodium = $row2['sodium'];
						array_push($ideal_calories_vals,$ideal_calories);
						array_push($ideal_sodium_vals,$ideal_sodium);
				}else{
				
					$ideal_calories = 1500;
					$ideal_sodium = 2200;
				}
				//$data_row = array('creation_date'=>$date,'actual_calories'=>$actual_calories,'ideal_calories'=>$ideal_calories,'actual_sodium'=>$actual_sodium,'ideal_sodium'=>$ideal_sodium);
				//$data[$date]=array('actual_calories'=>$actual_calories,'ideal_calories'=>$ideal_calories,'actual_sodium'=>$actual_sodium,'ideal_sodium'=>$ideal_sodium);
			}
			
		}
		$data["dates"]=$dates;
		$data["actual_calories"]=$actual_calories_vals;
		$data["ideal_calories"]=$ideal_calories_vals;
		$data["actual_sodium"]=$actual_sodium_vals;
		$data["ideal_sodium"]=$ideal_sodium_vals;
		
		return $data;
	}
		
	//create plot
	function create_report($data){
	
		ob_clean();
		// create new PDF document
		$pdf = new TCPDF(PDF_PAGE_ORIENTATION, PDF_UNIT, PDF_PAGE_FORMAT, true, 'UTF-8', false);

		// set document information
		//$pdf->SetCreator(PDF_CREATOR);
		$pdf->SetTitle('Patient 3: Amanda Owens Sodium and Calorie Summary');

		// set default header data
		//$pdf->SetHeaderData(PDF_HEADER_LOGO, PDF_HEADER_LOGO_WIDTH, PDF_HEADER_TITLE.' 001', PDF_HEADER_STRING, array(0,64,255), array(0,64,128));
		
		$pdf->SetHeaderData('', PDF_HEADER_LOGO_WIDTH, 'Patient 2225: Amanda Owens Sodium and Calorie Summary', '', array(0,64,255),array(0,64,128));
		
		$pdf->setFooterData(array(0,64,0), array(0,64,128));

		// set header and footer fonts
		$pdf->setHeaderFont(Array(PDF_FONT_NAME_MAIN, '', PDF_FONT_SIZE_MAIN));
		$pdf->setFooterFont(Array(PDF_FONT_NAME_DATA, '', PDF_FONT_SIZE_DATA));

		// set default monospaced font
		$pdf->SetDefaultMonospacedFont(PDF_FONT_MONOSPACED);

		// set margins
		$pdf->SetMargins(PDF_MARGIN_LEFT, PDF_MARGIN_TOP, PDF_MARGIN_RIGHT);
		$pdf->SetHeaderMargin(PDF_MARGIN_HEADER);
		$pdf->SetFooterMargin(PDF_MARGIN_FOOTER);

		// set auto page breaks
		$pdf->SetAutoPageBreak(TRUE, PDF_MARGIN_BOTTOM);

		// set image scale factor
		$pdf->setImageScale(PDF_IMAGE_SCALE_RATIO);

		// set some language-dependent strings (optional)
		if (@file_exists(dirname(__FILE__).'/lang/eng.php')) {
			require_once(dirname(__FILE__).'/lang/eng.php');
			$pdf->setLanguageArray($l);
		}

		// ---------------------------------------------------------

		// set default font subsetting mode
		$pdf->setFontSubsetting(true);

		// Set font
		// dejavusans is a UTF-8 Unicode font, if you only need to
		// print standard ASCII chars, you can use core fonts like
		// helvetica or times to reduce file size.
		$pdf->SetFont('dejavusans', '', 14, '', true);

		// Add a page
		// This method has several options, check the source code documentation for more information.
		$pdf->AddPage();

		// set text shadow effect
		$pdf->setTextShadow(array('enabled'=>true, 'depth_w'=>0.2, 'depth_h'=>0.2, 'color'=>array(196,196,196), 'opacity'=>1, 'blend_mode'=>'Normal'));

		// Set some content to print
$html = <<<EOD
<h3>Patient ID: 0 Name: John Doe</h3>
<h3>Date: 12/10/2013</h3>
<hr>
<i>This is the first example of TCPDF library.</i>
<p>This text is printed using the <i>writeHTMLCell()</i> method but you can also use: <i>Multicell(), writeHTML(), Write(), Cell() and Text()</i>.</p>
<p>Please check the source code documentation and other examples for further information.</p>
<p style="color:#CC0000;">TO IMPROVE AND EXPAND TCPDF I NEED YOUR SUPPORT, PLEASE <a href="http://sourceforge.net/donate/index.php?group_id=128076">MAKE A DONATION!</a></p>
EOD;

		// Print text using writeHTMLCell()
		//$pdf->writeHTMLCell(0, 0, '', '', $html, 0, 1, 0, true, '', true);
		$pdf->Image('calories.jpg', '', '', 100, 100, '', '', 'T', false, 300, 'C', false, false, 1, false, false, false);
		
		$pdf->Ln();
		
		$pdf->Image('sodium.jpg', '', '', 100, 100, '', '', 'T', false, 300, 'C', false, false, 1, false, false, false);
		// ---------------------------------------------------------

		$table = create_table($data);
		$pdf->AddPage();
		//$pdf->writeHTMLCell(0, 0, '', '', $table, 0, 1, 0, true, '', true);
		$pdf->writeHTML($table, true, false, false, false, '');
		// Close and output PDF document
		// This method has several options, check the source code documentation for more information.
		$filename = 'CHF_Report_Amanda_Ownes.pdf';
		//$base64 = $pdf->Output($filename,'E');
		$pdf->Output($filename,'I');
		//echo $base64;
		//send_report_HIE(1394,$base64,$filename);
		
		echo 'Done';


		
	}
	function create_table($data){
	
		$dates = $data["dates"];
		
		$table = "<table style=\"border-collapse:collapse;border:1px;\"><tr style=\"color:#444444\"><th>Date</th><th>Actual Calories</th><th>Ideal Calories</th><th>Actual Sodium</th><th>Ideal Sodium</th></tr>";
		//echo print_r($dates,true);
		
		$blue = "#62da97";
		$color = $blue;
		for( $i=0;$i<count($dates);$i++){
			
			//$table.= '<tr><td>'.number_format($data["actual_calories"][$i],2).'</td><td>'.number_format($data["ideal_calories"][$i],2).'</td>';
			//$table.= '<td>'.number_format($data["actual_sodium"][$i],2).'</td><td>'.number_format($data["ideal_sodium"][$i],2).'</td></tr>';
		
			
			
			if( $data["actual_calories"][$i]>$data["ideal_calories"][$i] || $data["actual_sodium"][$i]>$data["ideal_sodium"][$i] ){
			
				$table .= '<tr style="background-color:'.$color.'"><td>'.$dates[$i].'</td>';
				if( $data["actual_calories"][$i]>$data["ideal_calories"][$i] ){
					
					$table .= '<td style="color:red">'.number_format($data["actual_calories"][$i],2).'</td><td>'.number_format($data["ideal_calories"][$i],2).'</td>';
				}else{
					$table .= '<td>'.number_format($data["actual_calories"][$i],2).'</td><td>'.number_format($data["ideal_calories"][$i],2).'</td>';
				}
				if( $data["actual_sodium"][$i]>$data["ideal_sodium"][$i] ){
					$table.= '<td style="color:red">'.number_format($data["actual_sodium"][$i],2).'</td><td>'.number_format($data["ideal_sodium"][$i],2).'</td></tr>';
				}
				else{
					$table.= '<td>'.number_format($data["actual_sodium"][$i],2).'</td><td>'.number_format($data["ideal_sodium"][$i],2).'</td></tr>';
				}
				if($color == $blue){
					$color = 'white';
				}else{
					$color = $blue;
				}
			}
		}
		$table.="</table>";
		return $table;
	}
	function send_report_HIE($PatientID,$Base64Document,$DocumentName){
		$url="http://hit2.nimbus.cip.gatech.edu/HIESvc/HIE.svc/SendReport";
		$method="POST";
		$data=array("PatientID"=>$PatientID,"Base64Document"=>$Base64Document,"DocumentName"=>$DocumentName);

		$result=CallAPI($method, $url, json_encode($data));
		
		$content = file_get_contents('output.txt');
		$content .= $result;
		file_put_contents('output.txt', $content);
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
	function create_plots($data){
	
		/* Create your dataset object */ 
		$myData = new pData(); 
		 
		/* Add data in your dataset */ 
		$myData->addPoints($data["actual_calories"],"Actual");
		$myData->addPoints($data["ideal_calories"],"Ideal");
		$myData->setSerieWeight("Actual",2);
		$myData->setSerieWeight("Ideal",2);
		$myData->setAxisName(0,"Calories");
		$myData->addPoints($data["dates"],"Labels");
		$myData->setSerieDescription("Labels","Days");
		$myData->setAbscissa("Labels");
		//$myData->addPoints($ideal_calories);
		 
		/* Create a pChart object and associate your dataset */ 
		$myPicture = new pImage(800,430,$myData);
		 
		/* Choose a nice font */
		$myPicture->setFontProperties(array("FontName"=>"../pChart2.1.3/pChart2.1.3/fonts/Forgotte.ttf","FontSize"=>11));
		$myPicture->drawText(450,35,"Actual and Ideal Calories",array("FontSize"=>20,"Align"=>TEXT_ALIGN_BOTTOMMIDDLE));
		  
		/* Define the boundaries of the graph area */
		$myPicture->setGraphArea(60,40,770,390);
		/* Draw the scale, keep everything automatic */ 
		$myPicture->drawScale();
		/* Draw the scale, keep everything automatic */ 
		$myPicture->drawSplineChart();
		/* Build the PNG file and send it to the web browser */ 
		$myPicture->Render("calories.jpg");
		
				/* Create your dataset object */ 
		$myData = new pData(); 
		 
		/* Add data in your dataset */ 
		$myData->addPoints($data["actual_sodium"],"Actual");
		$myData->addPoints($data["ideal_sodium"],"Ideal");
		$myData->setAxisName(0,"Sodium");
		$myData->addPoints($data["dates"],"Labels");
		$myData->setSerieDescription("Labels","Days");
		$myData->setAbscissa("Labels");
		$myData->setSerieWeight("Actual",2);
		$myData->setSerieWeight("Ideal",2);
		 
		/* Create a pChart object and associate your dataset */ 
		$myPicture = new pImage(800,430,$myData);
		 
		/* Choose a nice font */
		$myPicture->setFontProperties(array("FontName"=>"../pChart2.1.3/pChart2.1.3/fonts/Forgotte.ttf","FontSize"=>11));
		$myPicture->drawText(450,35,"Actual and Ideal Sodium",array("FontSize"=>20,"Align"=>TEXT_ALIGN_BOTTOMMIDDLE));
		  
		/* Define the boundaries of the graph area */
		$myPicture->setGraphArea(60,40,770,390);
		/* Draw the scale, keep everything automatic */ 
		$myPicture->drawScale();
		/* Draw the scale, keep everything automatic */ 
		$myPicture->drawSplineChart();
		/* Build the PNG file and send it to the web browser */ 
		$myPicture->Render("sodium.jpg");

	}
	
?>