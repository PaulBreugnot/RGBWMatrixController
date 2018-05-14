#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

#define SSID "NodeMCU"
#define pw "12345678"

WiFiServer server(80);
IPAddress local_IP(192, 168, 1, 1);
IPAddress gateway(192, 168, 1, 1);
IPAddress subnet(255, 255, 255, 0);

WiFiUDP Udp;
unsigned int localUdpPort = 80;
char incomingPacket[1025];

byte serialBuffer[2049];
bool firstPartReceived = false;

void setup() {
  WiFi.softAPConfig(local_IP, gateway, subnet);
  WiFi.softAP(SSID, pw);
  server.begin();

  Udp.begin(localUdpPort);

  Serial1.begin(1000000);
}

void loop() {
  int packetSize = Udp.parsePacket();
  if (packetSize)
  {
    //Serial.printf("Received %d bytes from %s, port %d\n", packetSize, Udp.remoteIP().toString().c_str(), Udp.remotePort());
    int len = Udp.read(incomingPacket, 1025);
    if(!firstPartReceived){
      for(int i = 0; i < 1025; i++){
        serialBuffer[i] = incomingPacket[i];
      }
      firstPartReceived = true;
    }
    else{
            for(int i = 0; i < 1025; i++){
        serialBuffer[1025 + i] = incomingPacket[i];
      }
      firstPartReceived = false;
      //Serial.println("Full matrix received");
      Serial1.write(serialBuffer, 2049);
    }
    /*if (len > 0)
    {
      incomingPacket[len] = 0;
    }*/
    //Serial.printf("UDP packet contents: %s\n", incomingPacket);

  }
}
