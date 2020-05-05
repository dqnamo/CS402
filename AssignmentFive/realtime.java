import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class realtime {
  public static void main(String[] args) throws IOException, ParserConfigurationException, org.xml.sax.SAXException{
    File f = new File("display.html");

    URL source = new URL("http://api.irishrail.ie/realtime/realtime.asmx/getStationDataByCodeXML_WithNumMins?StationCode=MYNTH&NumMins=90&format=xml");

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.parse(source.openStream());
    BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));

    StringBuilder bldr = new StringBuilder();
    String str;
    BufferedReader in = new BufferedReader(new FileReader(f));
    while((str = in.readLine())!=null)
      bldr.append(str);

    in.close();
    String content = bldr.toString();

    String realtime = "";

    //Get realtime info
    NodeList trains = doc.getElementsByTagName("objStationData");

    for(int i = 0; i < trains.getLength(); i++){
      Node train_node = trains.item(i);
      Element train = (Element) train_node;

      String train_info = "<div class='card mt-5'><div class='card-header'>"
        + "Destination: " + train.getElementsByTagName("Destination").item(0).getTextContent() + "</div>"
        + "<ul class='list-group list-group-flush'>"
        + "<li class='list-group-item'> Expected Arrival Time: " + train.getElementsByTagName("Exparrival").item(0).getTextContent() + "</li>"
        + "<li class='list-group-item'> Origin: " + train.getElementsByTagName("Origin").item(0).getTextContent() + "</li>"
        + "<li class='list-group-item'> Expected Departure Time: " + train.getElementsByTagName("Expdepart").item(0).getTextContent() + "</li>"
        + "<li class='list-group-item'> Arrival Time at Destination: " + train.getElementsByTagName("Destinationtime").item(0).getTextContent() + "</li>"
        + "</ul></div>";
      
        realtime += train_info;
    }

    //Reset File
    PrintWriter writer = new PrintWriter(f);
    writer.print("");
    writer.close();

    realtime = "<!-- StartInfo -->" + realtime + "<!-- EndInfo -->";
    content = content.replaceAll("<!-- StartInfo -->.*<!-- EndInfo -->", realtime);

    //Write new realtime info
    bw.write(content);
    bw.close();
  }
}