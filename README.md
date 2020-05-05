# CS402
Parallel &amp; Distributed Systems module in Maynooth University.

## Assignment Descriptions
### Assignment Three - Multicast Chat Application
Implement a simple chat client which transmits user text messages entered at the keyboard to a multicast address and also can simultaneously receive messages sent from other clients on other machines sent to the same multicast address.

### Assignment Five - XML Data To HTML Page
The following REST API URL returns an XML document containing realtime information about Train Times from Enfield Station with a 90min look ahead period.

http://api.irishrail.ie/realtime/realtime.asmx/getStationDataByCodeXML_WithNumMins?StationCode=ENFLD&NumMins=90&format=xml

Write a program that can fetch this realtime xml feed for your chosen stop, parses it and displays the train times in a more readable tabular form (in a HTML page perhaps) with Expected Arrival Time, Origin, Destination, Expected Departure Time, Arrival Time at Destination.

### Assignment Six - Sockets Client Server Currency Conversion
Create a Java socket based client-server style application where the server implements an
executor service with a threadpool for executing incoming requests. The service doesn't have
to do anything significant, for example a simple calculation of some kind would suffice. The
server should print out details of incoming client connections as well as returning results to
the client.
