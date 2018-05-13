#include "httpRequest.h"

httpRequest::httpRequest() {}

void httpRequest::setHttpHead(httpHead httphead) {
  head = httphead;
}

void httpRequest::setHttpBody(httpBody httpbody) {
  body = httpbody;
}

String httpRequest::getMethod() {
  return head.getMethod();
}

String httpRequest::getRequest() {
  return head.getRequest();
}

String httpRequest::getHeaders() {
  return head.getHeaders();
}

String httpRequest::getBody() {
  return body.getBody();
}

String httpRequest::getRequestProperty(String property) {
  int propertyIndex = getHeaders().indexOf(property);
  if (propertyIndex != -1) {
    int endIndex = getHeaders().indexOf('\n', propertyIndex);
    return getHeaders().substring(propertyIndex + property.length() + 2, endIndex);
  }
  return "";
}

String httpRequest::getRequestVariable(String variable) {
  if (getMethod().compareTo("GET") == 0) {
    return "";
  }
  else if (getMethod().compareTo("POST") == 0) {
    int variableIndex = getBody().indexOf(variable);
    if (variableIndex != -1) {
      //int endIndex = getBody().indexOf('\n', variableIndex);
      int endIndex = getBody().length();
      return getBody().substring(variableIndex + variable.length() + 1, endIndex);
    }
    return "";
  }
}

httpHead::httpHead() {}

httpHead::httpHead(String headData) {
  setMethod(headData);
  setRequest(headData);
  setHeaders(headData);
}

void httpHead::setMethod(String data) {
  method = data.substring(0, data.indexOf(' '));
}

String httpHead::getMethod() {
  return method;
}

void httpHead::setRequest(String data) {
  request = data.substring(0, data.indexOf('\n'));
}

String httpHead::getRequest() {
  return request;
}

void httpHead::setHeaders(String data) {
  headers = data.substring(request.length(), data.indexOf("\n\n"));
}

String httpHead::getHeaders() {
  return headers;
}

httpBody::httpBody() {}

httpBody::httpBody(String bodyData) {
  body = bodyData;
}

String httpBody::getBody() {
  return body;
}

