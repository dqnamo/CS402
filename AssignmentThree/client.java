import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;

public class client{
  public static void main(String[] args) {
    // Create a 2 thread pool for sender and reciever.
    ExecutorService executor = Executors.newFixedThreadPool(2);

    Scanner scan = new Scanner(System.in);
    System.out.println("Enter your name:");
    String name = scan.nextLine();

    executor.execute(new Sender(name));
    executor.execute(new Receiver());
  }
}

// Sets name and sends messages
class Sender implements Runnable{

  String name;
  
  public Sender(String name){
    this.name = name;
  }

  public void run(){
    try {
      send_message("230.0.0.0", 4321);
    }catch(IOException ex){
      ex.printStackTrace();
    }
  }

  public void send_message(String ipAddress, int port) throws IOException{
    DatagramSocket socket = new DatagramSocket();
    InetAddress group = InetAddress.getByName(ipAddress);
    Scanner scan = new Scanner(System.in);

    while(true){
      String message = this.name + ": " + scan.nextLine();
      byte[] msg = message.getBytes();
      DatagramPacket packet = new DatagramPacket(msg, msg.length, group, port);
      socket.send(packet);
    }
  }
}

//Recieves messages
class Receiver implements Runnable{
  
  public Receiver(){
    System.out.println("Connected");
  }

  public void run(){
    try {
      receive_message("230.0.0.0", 4321);
    }catch(IOException ex){
      ex.printStackTrace();
    }
  }

  public static void receive_message(String ipAddress, int port) throws IOException{
    byte[] buffer = new byte[1024];
    MulticastSocket socket = new MulticastSocket(4321);
    InetAddress group = InetAddress.getByName("230.0.0.0");
    socket.joinGroup(group);
      
    while(true){
      DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
      socket.receive(packet);
         
      String msg = new String(packet.getData(),
      packet.getOffset(),packet.getLength());
      System.out.println(msg);
    }
  }
}