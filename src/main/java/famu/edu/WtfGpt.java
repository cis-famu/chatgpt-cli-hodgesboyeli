package famu.edu;

import com.fasterxml.jackson.databind.ObjectMapper;

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
        String searchString2 = searchString; //new

        ObjectMapper objectMapper = new ObjectMapper();
        ChatGptRequest chatGptRequest = new ChatGptRequest("text-davinci-001", searchString, 1, 100);
        ChatGptRequest chatGptRequest2 = new ChatGptRequest("text-davinci-001", searchString2, 1, 100); //new
        String input = objectMapper.writeValueAsString(chatGptRequest);
        String input2 = objectMapper.writeValueAsString(chatGptRequest2);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/completions"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Authorization", "Bearer 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")
                .POST(HttpRequest.BodyPublishers.ofString(input))
                .build();

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/completions"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Authorization", "Bearer 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")
                .POST(HttpRequest.BodyPublishers.ofString(input2))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpClient client2 = HttpClient.newHttpClient();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var response2 = client.send(request, HttpResponse.BodyHandlers.ofString());


        if (response.statusCode() == 200) {
            ChatGptResponse chatGptResponse = objectMapper.readValue(response.body(), ChatGptResponse.class);
            String answer = chatGptResponse.choices()[0].text();
            if (!answer.isEmpty()){
                System.out.println(answer.replace("\n","").trim());
            }
        }
        else {
            System.out.println(response.statusCode());
            System.out.println(response.body());
        }

        if (response.statusCode() == 200) {
            ChatGptResponse chatGptResponse2 = objectMapper.readValue(response2.body(), ChatGptResponse.class);
            String answer = chatGptResponse2.choices()[0].text();
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