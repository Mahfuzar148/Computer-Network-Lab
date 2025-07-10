

## 📌 **Topic Name:**

**"Java Socket Programming: Simple Client-Server Communication with Custom Commands"**

---

## 📄 **P Documentation**

### 🔷 Project Title:

**Java Socket Programming: Simple Client-Server Communication with Custom Commands**

---

### 🔷 Objective:

To implement a **bi-directional communication** system between a **client and a server** using **TCP sockets** in Java. The server listens on a specific port and responds to predefined client commands such as `Hi`, `Ok`, and `Date`.

---

### 🔷 Technologies Used:

* **Java SE**
* **TCP/IP Sockets**
* `java.net.Socket`, `java.net.ServerSocket`
* `DataInputStream` / `DataOutputStream`

---

### 🔷 Components:

#### ✅ **1. Server**

* Listens for incoming client connections on port **8000**
* Processes client messages
* Responds to:

  * `Hi` → "Hello."
  * `Ok` → Sends "Ok" message **nine** times (1 main + 8 extra)
  * `Date` → Sends current system date
  * Any other message → "Unknown command!"
* Gracefully disconnects on `exit` command

##### 🔹 Sample Code: `Server.java`

```java
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

                int n = 8;
                while ((n-- > 0) && message.equalsIgnoreCase("ok")) {
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
```

---

#### ✅ **2. Client**

* Connects to server at `localhost:8000`
* Sends user-input messages
* Receives and displays server responses
* Special behavior: If message is `ok`, expects and displays **9 replies**
* Terminates gracefully on `exit` command

##### 🔹 Sample Code: `Client.java`

```java
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

                int n = 8;
                while ((n-- > 0) && message.equalsIgnoreCase("ok")) {
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
```

---

### 🔷 Communication Flow Diagram:

```
Client                           Server
  |       Connect (Socket)         |
  |------------------------------->|
  |                                |
  |     Send Command (Hi/Ok/Date)  |
  |------------------------------->|
  |                                |
  |     Receive Response (once/9x) |
  |<-------------------------------|
  |                                |
  |      Send 'exit' to terminate  |
  |------------------------------->|
  |                                |
```

---

### 🔷 Example Interaction:

```
[Client Console]
Type messages (type 'exit' to quit):
Me: Hi
Server: Hello.

Me: Date
Server: 2025-07-10

Me: ok
Server: Ok 
Server: Ok 
Server: Ok 
...
(Server: repeated 9 times)

Me: exit
Client disconnected.
```

---

### 🔷 Features:

* ✅ Real-time text messaging
* ✅ Handles multiple command types
* ✅ Demonstrates socket read/write operations
* ✅ Graceful exit handling
* ✅ Simple message loop with repeated responses

---

### 🔷 Limitations & Future Enhancements:

| Current Limitations     | Future Improvements                        |
| ----------------------- | ------------------------------------------ |
| Single client at a time | Multi-threaded server for multiple clients |
| No GUI                  | Add JavaFX or Swing UI                     |
| Basic protocol          | Use JSON or structured message formats     |
| Hardcoded port & IP     | Make configurable                          |

---

### 🔷 Conclusion:

This project illustrates the **fundamentals of TCP socket programming** in Java. It helps learners understand how to:

* Open a socket
* Exchange messages
* Handle inputs and outputs
* Work with basic client-server logic


