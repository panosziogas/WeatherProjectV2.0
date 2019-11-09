#include "DHT.h"
#include <Wire.h>
#include <SFE_BMP180.h>
#include <SPI.h>
#include <Ethernet.h>

#define DHTPIN 7     // what pin we're connected to
#define DHTTYPE DHT22   // DHT 22  (AM2302)
#define ALTITUDE 200.6

DHT dht(DHTPIN, DHTTYPE);
SFE_BMP180 bmp180;

byte mac[] = {
  0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED
};
byte ip[] = { 192, 168, 1, 5 };
EthernetClient client;
EthernetServer server(80);

void setup() {
  Serial.begin(9600);
  dht.begin();
  if (bmp180.begin()) {
    Serial.println("BMP180 initialization succeeded");
  } else {
    Serial.println("BMP180 initializationit failed");
  }
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
  }
}

void loop() {
  double humidity = getHumidityData();
  double temperature = getTemperatureData();
  double pressure = getPressure();

  // Check if any reads failed and exit early (to try again).
  if (isnan(humidity) || isnan(temperature) || isnan(pressure)) {
    Serial.println("Failed to read from sensor!");
    return;
  }
  printDataToSerial(humidity, temperature, pressure);
  postDataToServer(getJsonPayload(humidity, temperature, pressure));
  delay(600000);
}

void postDataToServer(String payload) {
  Serial.println("HTTP connection log begin*************************");
  byte serverName[] = {192, 168, 1, 1};
  Serial.println("Sending payload to server: " + payload);
  if (client.connect(serverName, 8080)) {
    Serial.println("Connection to remote server succeeded");
    String conne = "POST /weather-station HTTP/1.1";
    client.println(conne);
    client.println("Content-Type: application/json");
    client.print("Content-Length:");
    client.println(payload.length());
    client.println();
    client.println(payload);
  } else {
    Serial.println("Connection to remote server failed");
  }
  if (client.available()) {
    char c = client.read();
    Serial.print(c);
  }
  if (!client.connected()) {
    Serial.println("Weather station module is not connected to remote server");
    client.stop();
  }
  Serial.println("HTTP connection log end***************************");
}

String getJsonPayload(double humidity , double temperature , double pressure) {
  String payload = "{\"temperature\":" + String(temperature, 2) + ",\"humidity\":" + String(humidity, 2) + ",\"atmPressure\":" + String(pressure, 2) + "}";
  return payload;
}

void printDataToSerial(double humidity , double temperature , double pressure) {
  Serial.println("Serial output begin*************************");
  Serial.print("Humidity: ");
  Serial.print(humidity);
  Serial.println(" %");
  Serial.print("Temperature: ");
  Serial.print(temperature);
  Serial.println(" *C ");
  Serial.print("Pressure: ");
  Serial.print(pressure);
  Serial.println(" mb ");
  Serial.println("Serial output end***************************");
}

double getHumidityData() {
  return dht.readHumidity();
}

double getTemperatureData() {
  return dht.readTemperature();
}

double getPressure()
{
  char status;
  double T, P, p0, a;

  // You must first get a temperature measurement to perform a bmp180 reading.

  // Start a temperature measurement:
  // If request is successful, the number of ms to wait is returned.
  // If request is unsuccessful, 0 is returned.

  status = bmp180.startTemperature();
  if (status != 0)
  {
    // Wait for the measurement to complete:

    delay(status);

    // Retrieve the completed temperature measurement:
    // Note that the measurement is stored in the variable T.
    // Use '&T' to provide the address of T to the function.
    // Function returns 1 if successful, 0 if failure.

    status = bmp180.getTemperature(T);
    if (status != 0)
    {
      // Start a bmp180 measurement:
      // The parameter is the oversampling setting, from 0 to 3 (highest res, longest wait).
      // If request is successful, the number of ms to wait is returned.
      // If request is unsuccessful, 0 is returned.

      status = bmp180.startPressure(3);
      if (status != 0)
      {
        // Wait for the measurement to complete:
        delay(status);

        // Retrieve the completed bmp180 measurement:
        // Note that the measurement is stored in the variable P.
        // Use '&P' to provide the address of P.
        // Note also that the function requires the previous temperature measurement (T).
        // (If temperature is stable, you can do one temperature measurement for a number of bmp180 measurements.)
        // Function returns 1 if successful, 0 if failure.

        status = bmp180.getPressure(P, T);
        if (status != 0)
        {
          return (P);
        }
        else Serial.println("error retrieving bmp180 measurement\n");
      }
      else Serial.println("error starting bmp180 measurement\n");
    }
    else Serial.println("error retrieving temperature measurement\n");
  }
  else Serial.println("error starting temperature measurement\n");
}
