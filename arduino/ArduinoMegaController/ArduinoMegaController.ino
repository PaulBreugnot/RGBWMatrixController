#include <Adafruit_NeoPixel.h>

#define PIN1 4
#define PIN2 6
#define PINALIM 9

#define NUM_LEDS 256


#define BRIGHTNESS 255
Adafruit_NeoPixel strip1 = Adafruit_NeoPixel(NUM_LEDS, PIN1, NEO_GRBW + NEO_KHZ800);
Adafruit_NeoPixel strip2 = Adafruit_NeoPixel(NUM_LEDS, PIN2, NEO_GRBW + NEO_KHZ800);



uint8_t leds[NUM_LEDS*2*4];
uint8_t check;
uint8_t ref=1;

void setup()
{
  Serial.begin(9600);

  pinMode(PIN1, OUTPUT);
  pinMode(PIN2, OUTPUT);
  pinMode(PINALIM,INPUT);
  Serial1.begin(921600);
  strip1.setBrightness(BRIGHTNESS);
  strip1.begin();
  strip2.setBrightness(BRIGHTNESS);
  strip2.begin();
  for (int i = 0; i < NUM_LEDS; i++) {
    strip1.setPixelColor(i, 0, 0, 0, 5);
    strip2.setPixelColor(i, 0, 0, 0, 5);
  }
  strip1.show(); 
  strip2.show();
}



void loop()
{ 
    if (digitalRead(PINALIM)){
       Serial.print("Pin Alim");
      
    if (Serial1.available()) 
      {
      check=Serial1.read();
      
      
      if (check == ref ){
        check=0;
        
        

 
        Serial1.readBytes(leds, NUM_LEDS*2*4); 
       
        
        
        for (int i=0;i<NUM_LEDS;i++){
        
        strip1.setPixelColor(i, leds[4*i],leds[4*i+1],leds[4*i+2],leds[4*i+3]);
        strip2.setPixelColor(i, leds[4*(i+NUM_LEDS)],leds[4*(i+NUM_LEDS)+1],leds[4*(i+NUM_LEDS)+2],leds[4*(i+NUM_LEDS)+3]);
        
          
        }
      
      strip1.show();
      strip2.show();
      }
  
  
}
    }
    else{
      for (int i=0;i<NUM_LEDS;i++){
        
        strip1.setPixelColor(i, 0 ,0,0,0);
        strip2.setPixelColor(i, 0 ,0,0,0);
      }
      strip1.show();
      strip2.show();
      }
    }
     
