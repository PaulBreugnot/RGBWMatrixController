#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

WiFiServer server(80);
IPAddress local_IP(192, 168, 1, 1);
IPAddress gateway(192, 168, 1, 1);
IPAddress subnet(255, 255, 255, 0);

WiFiUDP Udp;
unsigned int localUdpPort = 4210;
char incomingPacket[2050];

void setup() {
  WiFi.softAPConfig(local_IP, gateway, subnet);
  WiFi.softAP(SSID, pw);
  server.begin();

  Udp.begin(localUdpPort);

  Serial.begin(115200);
  Serial1.begin(921600);
}

void loop() {
  int packetSize = Udp.parsePacket();
  if (packetSize)
  {
    Serial.printf("Received %d bytes from %s, port %d\n", packetSize, Udp.remoteIP().toString().c_str(), Udp.remotePort());
    int len = Udp.read(incomingPacket, 2050);
    if (len > 0)
    {
      incomingPacket[len] = 0;
    }
    Serial.printf("UDP packet contents: %s\n", incomingPacket);

  }
