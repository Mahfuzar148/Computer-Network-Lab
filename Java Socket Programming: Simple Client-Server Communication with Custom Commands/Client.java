import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;

class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8000);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);

            System.out.println("Type messages (type 'exit' to quit):");

            while (true) {
                System.out.print("Me: ");
                String message = scanner.nextLine();

                out.writeUTF(message);
                out.flush();

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }


                String response = in.readUTF();
                System.out.println("Server: " + response);

                int n=8;
                while((n-->0) && message.equalsIgnoreCase("ok")){

                    response = in.readUTF();
                    System.out.println("Server: " + response);
                }



            }


            scanner.close();
            out.close();
            in.close();
            socket.close();
            System.out.println("Client disconnected.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
