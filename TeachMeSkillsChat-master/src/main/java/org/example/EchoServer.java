package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EchoServer {
    private boolean running;
    public Map<String, String> words = new HashMap<>();

    public EchoServer()
    {
        running = true;

        try (ServerSocket server = new ServerSocket(8189))
        {
            while (running)
            {
                Socket socket = server.accept(); // wait before client connects

                new Thread(() -> {
                    try
                    {
                        handle(socket);
                    } catch (IOException ioException)
                    {
                        System.out.println("Client connection was broken.");
                    }
                }).start();
            }
        } catch (IOException msg)
        {
            msg.printStackTrace();
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private void handle(Socket socket) throws IOException {
        DataInputStream is;
        DataOutputStream os;

        is = new DataInputStream(socket.getInputStream());
        os = new DataOutputStream(socket.getOutputStream());
        words.put("Hello", "Hello");
        words.put("How are you?", "I'm okey, and you?");
        words.put("What day is today", Calendar.getInstance().toString());
        words.put("Who are you?", "I'm a server");
        words.put("What are you doing now?", "I'm chatting with you");
        while(true)
        {
            String msg = is.readUTF().toString();
            System.out.println("From client: " + msg);

            if (msg.equals("exit"))
            {
                os.writeUTF("Goodbye");
                os.flush();
                break;
            }
            String K = "";
            for (String Key : words.keySet()){
                if (words.get(Key).equals(msg) )  {
                   K = Key;
                    break;}}
            if (msg.equals(K))
            {
                os.writeUTF(words.get(K));
                os.flush();
            }

        }}


    public static void main(String[] args) {

        new EchoServer();
    }
}
