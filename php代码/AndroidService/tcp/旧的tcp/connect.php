 <?php   
    //确保在连接客户端时不会超时   
    set_time_limit(0);   
      
    //设置IP和端口号   
    $address='10.0.3.114';   
    $port=13448;    //调试的时候，可以多换端口来测试程序！   
      
    //创建一个SOCKET   
    if(($sock=socket_create(AF_INET,SOCK_STREAM,SOL_TCP))<0)   
    {   
		echo "socket_create() failed reason:".socket_strerror($sock)."<br>";   
    }   
      
    //绑定到socket端口   
    if(($ret=socket_bind($sock,$address,$port))<0)   
    {   
		echo "socket_bind() failed reason:".socket_strerror($ret)."<br>";   
    }   
      
    //开始监听   
    if(($ret=socket_listen($sock,4))<0)   
    {   
    echo "socket_listen() failed reason:".socket_strerror($ret)."<br>";   
    }   
      
    do {   
   		 if (($msgsock = socket_accept($sock)) < 0)   
   	     {   
   			 echo "socket_accept() failed: reason: " . socket_strerror($msgsock) . "/n"; 
   			 echo "/nThe Server is Stop……/n";
    		 break;   
   		 }   
      
   		 //发到客户端   
   		 $msg ="Welcome To Server!";   
   		 socket_write($msgsock, $msg, strlen($msg));
		 ob_flush();
		 flush();
   		 socket_close($msgsock);   
   		 
   		 echo "/nThe Server is running……/n";
   		 printf("/nThe Server is running……/n");
    } while (true);   
      
    socket_close($sock);