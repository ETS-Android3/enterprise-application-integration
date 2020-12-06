package Network;

import java.io.*;
import java.net.*;

public class Network {

    public static void sendToPort(String message, String ip, int port) throws IOException {
        Socket socket = null;
        OutputStreamWriter osw;
        try {
            socket = new Socket(ip, port);
            osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            osw.write(message, 0, message.length());
            osw.flush();
        } catch (IOException e) {
            System.err.print(e);
        } finally {
            socket.close();
        }
    }
}
