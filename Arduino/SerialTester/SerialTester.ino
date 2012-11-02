/*
Serial dummyprogram for testing
 */

void setup() {
  Serial.begin(9600);
}

void loop() {
  int a0 = analogRead(A0);
  Serial.println(a0);
  delay(500);
}
