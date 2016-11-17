<?php
    header('Content-type: application/json');
    $con = mysqli_connect("localhost", "sachjvmz", "sachin123", "sachjvmz_LetsMove")or die("Connection failed: " . mysqli_error);
    // Check connection
    
    
    
    //mysql_select_db("sachjvmz_LetsMove");
    
    $query="select post_id, user_id,name,product_type,weight,pic_name,from_address,to_address,pickup_date,max_amount from post where status='1'";
    $r = mysqli_query($con,$query);
    $post=array();
    while($row = mysqli_fetch_assoc($r)){
        
        $post[] = $row;
        
        /*$post_id = $row['post_id'];
		$user_id = $row['user_id'];
        $name = $row['name'];
        $type = $row['product_type'];
		$weight = $row['weight'];
        $from = $row['from_address'];
        $to = $row['to_address'];
        $pic_name = $row['pic_name'];
        $max_amount = $row['max_amount'];
        $pickup_date = $row['pickup_date'];

        $post[] = array('post_id' => $post_id);
        $post[] = array('user_id' => $user_id);
        $post[] = array('name' => $name);
        $post[] = array('type' => $type);
        $post[] = array('weight' => $weight);
        $post[] = array('from' => $from);
        $post[] = array('to' => $to);
        $post[] = array('pic_name' => $pic_name);
	
        $post[] = array('max_amount' => $max_amount);
        $post[] = array('pickup_date' => $pickup_date);*/
        
        
		

 }
     $response['post'] = $post;
   echo json_encode($response);
		
		
	
    
?>
