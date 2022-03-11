package speech;
import speech.Translate;
import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static speech.Translate.prettify;

public class SpeechRecognition {
    private static final String YourSubscriptionKey = "25773c8f0dac46439e82ad77deac3e1a";
    private static final String YourServiceRegion = "eastus";
    //private String response;

    public void main(String[] args) throws InterruptedException, ExecutionException {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(YourSubscriptionKey, YourServiceRegion);
        speechConfig.setSpeechRecognitionLanguage("es-ES");
        recognizeFromMicrophone(speechConfig);
    }

    public String recognizeFromMicrophone (SpeechConfig speechConfig) throws
            InterruptedException, ExecutionException {
        //To recognize speech from an audio file, use `fromWavFileInput` instead of `fromDefaultMicrophoneInput`:
        //AudioConfig audioConfig = AudioConfig.fromWavFileInput("YourAudioFile.wav");
        AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();
        SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig);
        PhraseList.getPhraseList(speechRecognizer);

        System.out.println("\nSpeak into your microphone.");
        Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
        SpeechRecognitionResult speechRecognitionResult = task.get();

        switch (speechRecognitionResult.getReason()) {
            case RecognizedSpeech:
                System.out.println("RECOGNIZED TEXT: " + speechRecognitionResult.getText());
                //TRANSLATION
                Translate translateRequest = new Translate();
                String response = null;
                //OrderPane orderPane = new OrderPane();
                try {
                    response = translateRequest.Post(speechRecognitionResult.getText());
                    //orderPane.setResponse(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return (prettify(response));
                //exitCode = 0;
                //break;
            case NoMatch:
                return "NOMATCH: Speech could not be recognized.";
                //break;
            case Canceled: {
                CancellationDetails cancellation = CancellationDetails.fromResult(speechRecognitionResult);

                if (cancellation.getReason() == CancellationReason.Error) {
                    return "CANCELED: ErrorCode=" + cancellation.getErrorCode() +
                    "CANCELED: ErrorDetails=" + cancellation.getErrorDetails() +
                    "CANCELED: Did you update the subscription info?";
                }

                return "CANCELED: Reason=" + cancellation.getReason();
            }
            //break;
        }
        return "your mom";
    }

    /*public String getResponse()
    {
        return response;
    }*/

}