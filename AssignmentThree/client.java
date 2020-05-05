import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class client{
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("Enter your name:");
    String name = scan.nextLine();

    Thread thread_for_sender = new Thread(new Sender(name));
    Thread thread_for_receiver = new Thread(new Receiver());
    thread_for_sender.start();
    thread_for_receiver.start();
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