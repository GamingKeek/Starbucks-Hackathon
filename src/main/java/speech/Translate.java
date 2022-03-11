package speech;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.okhttp.*;

import java.io.IOException;

public class Translate {
    private static final String subscriptionKey = "49712e8679c94a6581a73861cfe148cd";

    // Add your location, also known as region. The default is global.
    // This is required if using a Cognitive Services resource.
    private static final String location = "eastus";

    HttpUrl url = new HttpUrl.Builder()
            .scheme("https")
            .host("api.cognitive.microsofttranslator.com")
            .addPathSegment("/translate")
            .addQueryParameter("api-version", "3.0")
            .addQueryParameter("from", "es")
            //.addQueryParameter("to", "de")
            .addQueryParameter("to", "en")
            .build();

    // Instantiates the OkHttpClient.
    OkHttpClient client = new OkHttpClient();

    // This function performs a POST request.
    public String Post(String textToTranslate) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "[{\"Text\": \"" + textToTranslate + "\"}]");
        Request request = new Request.Builder().url(url).post(body)
                .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey)
                .addHeader("Ocp-Apim-Subscription-Region", location)
                .addHeader("Content-type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    // This function prettifies the json response.
    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(json_text);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

    public static void main(String[] args) {
        try {
            Translate translateRequest = new Translate();
            String response = translateRequest.Post("example");
            System.out.println(prettify(response));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}