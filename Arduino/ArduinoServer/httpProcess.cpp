#include "httpRequest.h"

String processGet(httpRequest request) {
  return "HTTP/1.1 200 OK \n";
}

String processPost(httpRequest request) {
  String values = request.getRequestVariable("values");
  int size = values.length();
  //Serial.println(size);
  char buffer[size];
  for (int i = 0; i < size; i++){
    buffer[i] = values[i];
    //Serial.print(buffer[i], BIN);Serial.print(" ");
  }
  Serial1.write(buffer, size);
  return "HTTP/1.1 200 OK \n";
}

String process(httpRequest request) {
  if (request.getMethod().compareTo("GET") == 0) {
    return processGet(request);
  }
  else if (request.getMethod().compareTo("POST") == 0) {
    return processPost(request);
  }
}


