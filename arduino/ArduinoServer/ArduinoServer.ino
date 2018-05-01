#include <ESP8266WiFi.h>

#define SSID "NodeMCU"
#define pw "12345678"

WiFiServer server(80);
IPAddress local_IP(192,168,1,1);
IPAddress gateway(192,168,1,1);
IPAddress subnet(255,255,255,0);

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
    while (client.connected())
    {
      // read line by line what the client (web browser) is requesting
      if (client.available())
      {
        String line = client.readStringUntil('\r');
        String property_value[2];
        checkProperty(property_value, line);
        Serial.println(property_value[0]);
        Serial.println(property_value[1]);
        // wait for end of client's request, that is marked with an empty line
        if (line.length() == 1 && line[0] == '\n')
        {
          client.println("HTTP\1.1 200 OK \n");
          break;
        }
      }
    }
    delay(1); // give the web browser time to receive the data

    // close the connection:
    client.stop();
    Serial.println("[Client disonnected]");
  }
}

void checkProperty(String *property_value, String line){
  int split = line.indexOf(':');
  if(split<0){
    return;
  }
  *property_value = line.substring(0,split);
  *(property_value+1) = line.substring(split+2,line.length());
  
}

