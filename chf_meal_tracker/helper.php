<?php

	define('OUTPUT_FILE' ,'output.txt');
	
	function add_to_file($file,$new_content){
	
		$content = file_get_contents($file);
		$content .= "\n$new_content\n";
		file_put_contents($file, $content);
		
	}
?>