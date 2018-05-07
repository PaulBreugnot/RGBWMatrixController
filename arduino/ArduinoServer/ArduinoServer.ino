#include "ESP8266WiFi.h"

#include "httpRequest.h"

#define SSID "NodeMCU"
#define pw "12345678"

WiFiServer server(80);
IPAddress local_IP(192, 168, 1, 1);
IPAddress gateway(192, 168, 1, 1);
IPAddress subnet(255, 255, 255, 0);

void setup() {
  WiFi.softAPConfig(local_IP, gateway, subnet);
  WiFi.softAP(SSID, pw);
  server.begin();

  Serial.begin(115200);
}

void loop() {
  WiFiClient client = server.available();
  // wait for a client (web browser) to connect
  if (client)
  {
    Serial.println("\n[Client connected]");
    String headData;
    httpRequest request;
    while (client.connected())
    {
      // read line by line what the client (web browser) is requesting
      if (client.available())
      {
        String line = client.readStringUntil('\r');
        headData.concat(line);
        // wait for end of client's request, that is marked with an empty line
        if (line.length() == 1 && line[0] == '\n')
        {
          httpHead httpHead(headData);
          request.setHttpHead(httpHead);
          Serial.println(request.getRequest());
          if (request.getMethod().compareTo("GET") == 0) {
            client.println("HTTP\1.1 200 OK \n");
            break;
          }
          else {
            //read body
            client.read();
            int contentLength = request.getRequestProperty("Content-Length").toInt();
            String bodyData;
            int charReadNumber = 0;
            while (charReadNumber < contentLength) {
              while (!client.available()) {}
              char c = client.read();
              bodyData += c;
              charReadNumber++;
            }
            httpBody httpBody(bodyData);
            request.setHttpBody(httpBody);
            Serial.println(request.getBody());
          }
          client.println("HTTP\1.1 200 OK \n");
          break;
        }
      }
    }
    delay(1); // give the web browser time to receive the data

    // close the connection:
    client.stop();
    Serial.println("\n[Client disconnected]");
  }
}

