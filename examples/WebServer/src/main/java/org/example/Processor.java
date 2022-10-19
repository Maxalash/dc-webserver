package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Processor of HTTP request.
 */
public class Processor {
  private final Socket socket;
  private final HttpRequest request;

  public Processor(Socket socket, HttpRequest request) {
    this.socket = socket;
    this.request = request;
  }

  public boolean checkForPrime(int inputNumber){
    boolean isItPrime = true;
 
    if(inputNumber <= 1) {
        isItPrime = false;
 
        return isItPrime;
      } else{
        for (int i = 2; i<= inputNumber/2; i++) {
          if ((inputNumber % i) == 0){
            isItPrime = false;
 
            break;
          }
        }
 
        return isItPrime;
      }
  }

  public void process() throws IOException {
    System.out.println("Got request:");
    System.out.println(request.toString());
    String[] req = request.toString().split(" ");
    String res = req[1];
    req = res.split("/");
    System.out.println(req[1]);
    System.out.flush();

    PrintWriter output = new PrintWriter(socket.getOutputStream());

    if (req[1].contains("page")) {
      String val = req[2];
      output.println("HTTP/1.1 200 OK");
      output.println("Content-Type: text/html; charset=utf-8");
      output.println();
      output.println("<html>");
      output.println("<head><title>"+val+"</title></head>");
      output.println("<body><h1>Simple " + val + " page</h1></body>");
      output.println("</html>");
      output.flush();
      socket.close();

    } else if (req[1].contains("delete")) {
      String val = req[2];
      output.println("HTTP/1.1 200 OK");
      output.println("Content-Type: text/html; charset=utf-8");
      output.println();
      output.println("<html>");
      output.println("<head><title>Delete "+val+"</title></head>");
      output.println("<body><h1>Deleting "+val+"</h1></body>");
      output.println("</html>");
      output.flush();
      socket.close();

    } else if (req[1].contains("primes")) {
      Integer val = 0;
      
      val = Integer.parseInt(req[2]);
      int sum = 0;
      int ult = 0;
      while (sum <= val) {
        if(checkForPrime(sum)){
          ult +=sum;
        }
        sum++;
      }
      output.println("HTTP/1.1 200 OK");
      output.println("Content-Type: text/html; charset=utf-8");
      output.println();
      output.println("<html>");
      output.println("<head><title>Primes sum</title></head>");
      output.println("<body><p>Sum of prime numbers till " + val + " "+ult+"</p></body>");
      output.println("</html>");
      output.flush();
      socket.close();

    } else {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      output.flush();
      socket.close();

    }
  }
}
