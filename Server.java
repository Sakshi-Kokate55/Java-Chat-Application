 import java.io.*;
 import java.net.*;
 class Server 
 {
    ServerSocket server;
    Socket socket;

    BufferedReader reader;
    PrintWriter writer;
    public Server()
    {
        try
        {
           server=new ServerSocket(1121);
           System.out.println("Server is ready to accept Connection ");
           System.out.println("Waiting..");
           socket=server.accept();

           reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
           writer=new PrintWriter(socket.getOutputStream());

           startReading();
           startWriting();
       
        } catch(Exception e)
        {
            e.printStackTrace();
        }
      
    }
    public void startReading()
    {
        Runnable r1=()->
        {
            System.out.println("reader Started..");
            try
            {
             while(true)
              {
                String msg=reader.readLine();
                if(msg.equals("exit"))
                {
                    System.out.println("Client terminated the chat");
                    socket.close();
                    break;
                }
                System.out.println("Client : "+msg);

                } 
             }
             catch(Exception e)
                {
                  System.out.println("Connnection Closed!");
                }
        };
        new Thread (r1).start();
    }

    public void startWriting()
    {
         Runnable r2=()->
        {
             System.out.println("writer Started..");

             try
            {
               while(!socket.isClosed())
              {
                BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));

                    String content=br1.readLine();
                    writer.println(content);
                    writer.flush();

                    if(content.equals("exit"))
                    {
                        socket.close();
                        break;
                    }
                
                }
                System.out.println("Connnection Closed!");
             }
             catch(Exception e)
                {
                   e.printStackTrace();
                }
        };

        new Thread (r2).start();
       
    }
 public static void main(String[] args)
    {
      System.out.println("This is Server");
      new Server();
    }
}
