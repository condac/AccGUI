/*
Serial dummyprogram for testing
 */

void setup() {
  //Serial.begin(9600);
  Serial.begin(115200);
}

void loop() {
  int a0 = analogRead(A0);
  int a1 = analogRead(A1);
  int a2 = analogRead(A2);
  int time = millis();
  Serial.print(time);
  Serial.print("\t");
  Serial.print(a0);
  Serial.print("\t");
  Serial.print(a1);
  Serial.print("\t");
  Serial.print(a2);
  Serial.println("\tslut");
  //delay(500);
}
