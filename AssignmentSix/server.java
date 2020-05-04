import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class server {
  public static void main(String [] args) throws IOException{
    System.out.println("Server has started");
    ServerSocket ss = new ServerSocket(4999);
    Socket s = ss.accept();

    System.out.println("Client Connected");

    InputStreamReader in = new InputStreamReader(s.getInputStream());
    BufferedReader bf = new BufferedReader(in);
    String input = bf.readLine();

    BigDecimal n = new BigDecimal(input);

    System.out.println("Client wants to convert " + n + " euros");
    calculate_results(s,n);
  }

  static void calculate_results(Socket s, BigDecimal n) throws IOException{
    PrintWriter pr = new PrintWriter(s.getOutputStream());

    pr.println("USD: " + convert_to_usd(n));
    pr.println("GBP: " + convert_to_gbp(n));
    pr.println("YEN: " + convert_to_yen(n));

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