# Starbucks-Hackathon
All classes are in Starbucks-Hackathon/src/main/java/speech/

Run StarbucksDisplay.java to open application

To start the microphone to listen, press any of the audio buttons and wait til "Speak in microphone" pops up in terminal. There is a pause after each button is pressed 
(Thread.sleep(5000)) and then it will start listening so the audio file doesn't interrupt when played. The translated text will show in the text area.
Press the audio buttons again if you want to turn the microphone on again.

Make Sure to
1. configure to JDK 8
2. have gradle downloaded
3. make sure Azure subscription keys and region are inserted for Translate and Speech to Text Resource (in Translate.java and SpeechRecognition.java)
4. The current language is set to listen for Spanish (es-ES) and translate to English (en-EN), change accordingly in Translate.java and SpeechRecognition.java
   a. Code Segment in Translate.java
     {.addQueryParameter("from", "es") //spanish
     .addQueryParameter("to", "en") //english }
   b. Code Segment in SpeechRecognition.java
    {speechConfig.setSpeechRecognitionLanguage("es-ES"); //spanish}
