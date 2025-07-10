

# 📄 **Documentation: SendMail.java**

## 📌 **Overview**

This Java program demonstrates how to send an email through **Gmail SMTP** using **low-level socket programming** and **SSL**. It manually establishes a secure connection to Gmail's SMTP server, authenticates using base64-encoded credentials, and sends an email containing:

* Subject & Body
* Sender’s IP address
* Sender’s MAC address
* Timestamp

---

## 🧱 **Package Imports**

```java
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
```

* `BufferedReader`, `DataOutputStream`: for socket communication
* `InetAddress`, `NetworkInterface`: to get IP and MAC addresses
* `SSLSocket`, `SSLSocketFactory`: for SSL-enabled socket communication
* `Base64`: for encoding username and password
* `LocalDateTime`: to get the current date and time

---

## 🔧 **Class Declaration**

```java
public class SendMail {
```

This is the main class of the program.

---

## 🌐 **Global Variables**

```java
private static DataOutputStream out;
public static BufferedReader in;
```

* `out`: for sending data to the server
* `in`: for reading server responses

---

## 🧠 **main() Method**

```java
public static void main(String [] args) throws Exception
```

### ✔️ Purpose:

* Connects to Gmail's SMTP server using SSL
* Performs authentication
* Sends an email with system details

---

### 🧾 **Steps in main():**

#### ✅ 1. Email Credentials

```java
String user = "s2110976123@ru.ac.bd";
String pass = "fhritlcohospmnkn";
```

* Gmail email address and app-specific password

> ⚠️ Do not hardcode credentials in production! Use environment variables or secure vaults.

---

#### ✅ 2. Encode Credentials in Base64

```java
String username = Base64.getEncoder().encodeToString(user.getBytes());
String password = Base64.getEncoder().encodeToString(pass.getBytes());
```

---

#### ✅ 3. Create SSL Socket to Gmail SMTP

```java
SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket("smtp.gmail.com", 465);
```

* Connects securely to `smtp.gmail.com` on port `465`.

---

#### ✅ 4. Initialize Input and Output Streams

```java
out = new DataOutputStream(socket.getOutputStream());
in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
```

---

#### ✅ 5. SMTP Protocol Communication

* **EHLO**: Identify the client
* **AUTH LOGIN**: Start authentication
* **Username/Password**: Sent as base64
* **MAIL FROM / RCPT TO**: Set sender and recipient
* **DATA**: Compose the email
* **QUIT**: End the session

Each command is sent using:

```java
send("command_here");
System.out.println("Server: " + in.readLine());
```

---

#### ✅ 6. Email Content

```java
send("FROM: s2110976123@ru.ac.bd");
send("TO: mahfuzar148@gmail.com");
send("SUBJECT: 3Y2S2023" );
send("Mail Body here" );
send("Mac: " + getMac());
send("IP: " + getIPAddress());
send(LocalDateTime.now().toString());
send(".");
```

* Email body includes static message, system MAC, IP, and timestamp
* `.` marks end of email content (per SMTP spec)

---

## 🧰 **Helper Methods**

### 🔹 `send(String message)`

Sends a message to the server and waits 1 second.

```java
public static void send(String message) throws Exception {
    out.writeBytes(message + "\r\n");
    out.flush();
    Thread.sleep(1000);
    System.out.println("Client: " + message);
}
```

---

### 🔹 `getMac()`

Returns MAC address of the local machine.

```java
private static String getMac() {
    ...
}
```

---

### 🔹 `getIPAddress()`

Returns local IP address.

```java
private static String getIPAddress() throws Exception {
    InetAddress ip = InetAddress.getLocalHost();
    return ip.getHostAddress();
}
```

---

## 📬 **SMTP Communication Example (Simplified Log)**

```
Client: EHLO smtp.gmail.com
Server: 250-smtp.gmail.com at your service...

Client: AUTH LOGIN
Server: 334 VXNlcm5hbWU6

Client: [Base64-encoded user]
Server: 334 UGFzc3dvcmQ6

Client: [Base64-encoded password]
Server: 235 Authentication successful

Client: MAIL FROM: <you@example.com>
Server: 250 OK

Client: RCPT TO: <target@example.com>
Server: 250 OK

Client: DATA
Server: 354 Start mail input

Client: FROM: you@example.com
Client: TO: target@example.com
Client: SUBJECT: Test
Client: MAC: XX:XX:XX:XX
Client: IP: 192.168.0.10
Client: 2025-07-10T09:00:00
Client: .
Server: 250 Message accepted

Client: QUIT
Server: 221 Bye
```

---

## ⚠️ **Important Notes**

1. **App Password**: The password used must be an **App Password** (not regular Gmail password), enabled via [Google Account > Security > App Passwords](https://myaccount.google.com/apppasswords).
2. **SMTP Configuration**:

   * Host: `smtp.gmail.com`
   * Port: `465` (SSL)
3. **Gmail Restrictions**:

   * Direct login via SMTP might be blocked without proper configuration.
   * Use trusted application settings.

---

## ✅ **Conclusion**

This Java program demonstrates a **manual SMTP email sending system using SSL sockets**. It also fetches system-level information like IP and MAC addresses to include in the email, showcasing practical usage of network and time APIs.

