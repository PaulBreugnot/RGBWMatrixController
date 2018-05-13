#include "Arduino.h"

class httpHead {
    private :
    String method;
    String request;
    String headers;

    void setMethod(String data);
    void setRequest(String data);
    void setHeaders(String data);

  public :
    httpHead();
    httpHead(String headData);
    String getMethod();
    String getRequest();
    String getHeaders();
    
};

class httpBody {
    private :
    String body;

  public :
    httpBody();
    httpBody(String bodyData);
    String getBody();
};


class httpRequest {
  private :
    httpHead head;
    httpBody body;

  public :
    httpRequest();
    void setHttpHead(httpHead head);
    void setHttpBody(httpBody body);
    String getMethod();
    String getRequest();
    String getHeaders();
    String getBody();
    String getRequestProperty(String property);
    String getRequestVariable(String property);
};


