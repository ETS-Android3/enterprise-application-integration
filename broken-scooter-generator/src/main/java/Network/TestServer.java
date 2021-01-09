package Network;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer extends Thread{
    private ServerSocket serverSocket;
    private int port;
    private boolean running = false;

    public TestServer(int port) {
        this.port = port;
    }

    public void startServer() {
        try
        {
            serverSocket = new ServerSocket(port, 0, InetAddress.getByName("localhost"));
            this.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void stopServer()
    {
        running = false;
        this.interrupt();
    }

    @Override
    public void run()
    {
        running = true;
        while( running )
        {
            try
            {
                System.out.println( "Listening for a connection" );

                // Call accept() to receive the next connection
                Socket socket = serverSocket.accept();

                InputStream is = socket.getInputStream();
                byte[] buffer = new byte[1024];
                int read;
                while((read = is.read(buffer)) != -1) {
                    String output = new String(buffer, 0, read);
                    System.out.print(output);
                    System.out.flush();
                };
                is.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}