import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import java.net.InetAddress;
import java.net.NetworkInterface;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import java.time.LocalDateTime;

import java.util.Base64;
import java.util.Scanner;



public class SendMail {

    private static DataOutputStream out;
    public static BufferedReader in;

    public static void main(String [] args) throws Exception {

        String user = "s2110976123@ru.ac.bd";
        String pass = "fhritlcohospmnkn";

        String username = new String(Base64.getEncoder().encode(user.getBytes()));
        String password = new String(Base64.getEncoder().encode(pass.getBytes()));
        SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket("smtp.gmail.com", 465);
        out = new DataOutputStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        send("EHLO smtp.gmail.com");
        System.out.println("Server: " + in.readLine());
        System.out.println("Server: " + in.readLine());
        System.out.println("Server: " + in.readLine());
        System.out.println("Server: " + in.readLine());
        System.out.println("Server: " + in.readLine());
        System.out.println("Server: " + in.readLine());
        System.out.println("Server: " + in.readLine());
        System.out.println("Server: " + in.readLine());
        System.out.println("Server: " + in.readLine());

        send("AUTH LOGIN");
        System.out.println("Server: " + in.readLine());

        send(username);
        System.out.println("Server: " + in.readLine());

        send(password);
        System.out.println("Server: " + in.readLine());

        send("MAIL FROM: <s2110976123@ru.ac.bd>");
        System.out.println("Server: " + in.readLine());

        send("RCPT TO: <mahfuzar148@gmail.com>");
        System.out.println("Server: " + in.readLine());

        send("DATA");
        System.out.println("Server: " + in.readLine());

        send("FROM: s2110976123@ru.ac.bd");
        send("TO: mahfuzar148@gmail.com");
        send("SUBJECT: 3Y2S2023" );
        send("Mail Body here" );
        send("Mac: " + getMac());
        send("IP: " + getIPAddress());
        send(LocalDateTime.now().toString());
        send(".");
        System.out.println("Server: " + in.readLine());

        send("QUIT");
        System.out.println("Server: " + in.readLine());



        out.close();
        in.close();
        socket.close();
    }


    // ✅ Function to Get MAC Address
    private static String getMac() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();

            if (mac == null) return "Unknown";

            StringBuilder sb = new StringBuilder();
            for (byte b : mac) {
                sb.append(String.format("%02X:", b));
            }
            return sb.substring(0, sb.length() - 1); // Remove last colon
        } catch (Exception e) {
            return "Error retrieving MAC";
        }
    }

    public static void send(String message) throws Exception {
        out.writeBytes(message + "\r\n");
        out.flush();
        Thread.sleep(1000);
        System.out.println("Client: " + message);
    }

    // ✅ Get Sender's IP Address
    private static String getIPAddress() throws Exception {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();  // Returns IPv4 Address
    }


}
