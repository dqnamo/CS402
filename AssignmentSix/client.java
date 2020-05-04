import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.BufferedReader;

class client {
  public static void main(String [] args) throws IOException{
    Socket s = new Socket("localhost",4999);

    PrintWriter pr = new PrintWriter(s.getOutputStream());
    Scanner scan = new Scanner(System.in);

    System.out.println("Enter amount(in euro) to convert:");
    BigDecimal input = new BigDecimal(scan.nextLine());

    pr.println(input);
    pr.flush();

    InputStreamReader in = new InputStreamReader(s.getInputStream());
    BufferedReader bf = new BufferedReader(in);

    System.out.println(bf.readLine());
    System.out.println(bf.readLine());
    System.out.println(bf.readLine());
  }
}