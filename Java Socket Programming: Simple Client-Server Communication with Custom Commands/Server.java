import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server started. Listening for incoming connections...");

            Socket socket = serverSocket.accept();

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            while (true) {
                String message = in.readUTF();

                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected.");
                    break;
                }

                String reply;


                if (message.equalsIgnoreCase("Ok")) {
                    reply = "Ok ";
                } else if (message.equalsIgnoreCase("Hi")) {
                    reply = "Hello.";
                } else if (message.equalsIgnoreCase("Date")) {
                    reply = LocalDate.now().toString();
                } else {
                    reply = "Unknown command!";
                }


                out.writeUTF(reply);
                out.flush();

                int n=8;

                while((n-->0) && message.equalsIgnoreCase("ok")){
                    out.writeUTF(reply);
                    out.flush();
                }

                System.out.println("Client: " + message);
                System.out.println("Server: " + reply);
            }


            in.close();
            out.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
