 import java.io.*;
 import java.net.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class Client extends JFrame
 {
    Socket socket;

    BufferedReader reader;
    PrintWriter writer;

    private JLabel heading=new JLabel("Client Area");
    private JTextArea messageArea=new JTextArea();
    private JTextField messageInput=new JTextField();
    private Font font=new Font("Roboto",Font.PLAIN,20);

    public Client()
    {
        try
        {
           System.out.println("Sending request to server");
           socket=new Socket("127.0.0.1",1121);
           System.out.println("Connection Done!");
           
           reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
           writer=new PrintWriter(socket.getOutputStream());
           
           createGUI();
           handleEvents();
           startReading();

        }
        catch(Exception e)
        {
           // e.printStackTrace();
        }
    }
    private void handleEvents()
    {
        messageInput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                
               
            }

            @Override
            public void keyPressed(KeyEvent e) {
        

            }

            @Override
            public void keyReleased(KeyEvent e) {
            
                if(e.getKeyCode() == 10)
                {
                   // System.out.println("You Have Pressed Enter Button!");
                   String contentToSend=messageInput.getText();
                   messageArea.append("Me :"+contentToSend+"\n");
                   writer.println(contentToSend);
                   writer.flush();
                   messageInput.setText("");
                   messageInput.requestFocus();
                }
            }
            
        });
    }
    private void createGUI()
    {
        this.setTitle("Client Messanger[END]");
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        heading.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);

        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        messageArea.setEditable(false);
        messageInput.setHorizontalAlignment(SwingConstants.CENTER);

        this.setLayout(new BorderLayout());

        this.setLayout(new BorderLayout());
        this.add(heading,BorderLayout.NORTH);
        JScrollPane jScrollPane=new JScrollPane(messageArea);
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(messageInput,BorderLayout.SOUTH);

        this.setVisible(true);
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
                    System.out.println("Server terminated the chat");
                    JOptionPane.showMessageDialog(this, "Server Terminated the chat");
                    messageInput.setEnabled(false);
                    socket.close();
                    break;
                }
               // System.out.println("Server : "+msg);
                messageArea.append("Server : "+msg+"\n");
                } 
             }
             catch(Exception e)
                {
                    System.out.println("Connnection Closed!");
                }
        };
        new Thread (r1).start();
    }

    
 public static void main(String[] args)
    {
       System.out.println("This is Client..");
       new Client();
    }
}
