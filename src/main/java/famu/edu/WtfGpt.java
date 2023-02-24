package famu.edu;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class WtfGpt {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a string to search for: ");
        String searchString = scanner.nextLine();

        ObjectMapper objectMapper = new ObjectMapper();
        new ChatGptRequest("text-davinci-001", searchString,1, 100);
        String input = objectMapper.writeValueAsString(chatGptRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/completions"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Authorization", "Bearer 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")
                .POST(HttpRequest.BodyPublishers.ofString(input))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() = 200) {
            ChatGptResponse chatGptResponse = objectMapper.readValue(response.body(), ChatGptResponse.class);
            String answer = chatGptResponse.choices()[0].text() + "\n" + chatGptResponse.choices()[1].text();
            if (!answer.isEmpty()){
                System.out.println(answer.replace("\n","").trim());
            }
        }
        else {
            System.out.println(response.statusCode());
            System.out.println(response.body());
        }
    }
}