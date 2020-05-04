import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;

public class server {
  public static void main(String [] args) throws IOException{
    System.out.println("Server has started...");

    ServerSocket server = new ServerSocket(4999);
    ExecutorService executor = Executors.newFixedThreadPool(4);

    while(true){
      Socket s = server.accept();
      System.out.println("New client connected");

      executor.execute(new CurrencyConversion(s));
    }
  }
}

class CurrencyConversion implements Runnable{

  private Socket socket;
  private BigDecimal amount;

  public CurrencyConversion(Socket s) throws IOException{
    this.socket = s;
  }

  public void run(){
    try {
      InputStreamReader in = new InputStreamReader(this.socket.getInputStream());
      BufferedReader bf = new BufferedReader(in);
      this.amount = new BigDecimal(bf.readLine());
      System.out.println("Input recieved");
      calculate_results(this.socket,this.amount);
    }catch (IOException e) {
      e.printStackTrace();
    }
  }

  static void calculate_results(Socket s, BigDecimal n) throws IOException{
    PrintWriter pr = new PrintWriter(s.getOutputStream());

    pr.println("USD: " + convert_to_usd(n));
    pr.println("GBP: " + convert_to_gbp(n));
    pr.println("YEN: " + convert_to_yen(n));

    System.out.println("Sent Response");
    pr.flush();
  }

  static BigDecimal convert_to_usd(BigDecimal n){
    BigDecimal rate = new BigDecimal("1.09");
    return n.multiply(rate);
  }

  static BigDecimal convert_to_gbp(BigDecimal n){
    BigDecimal rate = new BigDecimal("0.88");
    return n.multiply(rate);
  }

  static BigDecimal convert_to_yen(BigDecimal n){
    BigDecimal rate = new BigDecimal("116.78");
    return n.multiply(rate);
  }
}