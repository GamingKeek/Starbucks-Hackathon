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

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(YourSubscriptionKey, YourServiceRegion);
        speechConfig.setSpeechRecognitionLanguage("en-US");
        recognizeFromMicrophone(speechConfig);
    }

    public static void recognizeFromMicrophone (SpeechConfig speechConfig) throws
            InterruptedException, ExecutionException {
        //To recognize speech from an audio file, use `fromWavFileInput` instead of `fromDefaultMicrophoneInput`:
        //AudioConfig audioConfig = AudioConfig.fromWavFileInput("YourAudioFile.wav");
        AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();
        SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig);

        System.out.println("\nSpeak into your microphone.");
        Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
        SpeechRecognitionResult speechRecognitionResult = task.get();

        switch (speechRecognitionResult.getReason()) {
            case RecognizedSpeech:
                System.out.println("RECOGNIZED TEXT: " + speechRecognitionResult.getText());
                //TRANSLATION
                Translate translateRequest = new Translate();
                String response = null;
                try {
                    response = translateRequest.Post(speechRecognitionResult.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(prettify(response));
                //exitCode = 0;
                break;
            case NoMatch:
                System.out.println("NOMATCH: Speech could not be recognized.");
                break;
            case Canceled: {
                CancellationDetails cancellation = CancellationDetails.fromResult(speechRecognitionResult);
                System.out.println("CANCELED: Reason=" + cancellation.getReason());

                if (cancellation.getReason() == CancellationReason.Error) {
                    System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                    System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                    System.out.println("CANCELED: Did you update the subscription info?");
                }
            }
            break;
        }
    }
}