package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeServer {
    public static void main(String[] args) throws IOException {
        // подключить telnet -> конфигурация

        // IP 127.0.0.1
        ServerSocket server = new ServerSocket(8189); // localhost + port
        Socket socket = server.accept(); // получить доступ
        System.out.println("Client connected");

        InputStream is = socket.getInputStream(); // ввожу
        OutputStream os = socket.getOutputStream(); // вывожу

        String response = "Welcome to local server: ";

        os.write(response.getBytes(StandardCharsets.UTF_8));

        byte[] buffer = new byte[256];

        while(true)
        {
            int read = is.read(buffer); // сколько элементов прочитал
            String msg = new String (buffer, 0, read);
            System.out.print(msg);

            if (msg.equals("~"))
            {
                LocalDateTime now = LocalDateTime.now();
                response = now.format(DateTimeFormatter.ISO_DATE_TIME);
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
