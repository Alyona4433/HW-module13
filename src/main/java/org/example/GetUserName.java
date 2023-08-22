package org.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;

public class JsonPlaceholderApiClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://jsonplaceholder.typicode.com/users";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("response.statusCode() = " + response.statusCode());

        JSONArray jsonArray = JSON.parseArray(response.body());


        String targetUsername = "username_to_search"; //Вводимо ім'я usera


        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject user = jsonArray.getJSONObject(i);
            String username = user.getString("username");

            if (username.equals(targetUsername)) {
                Integer userId = user.getInteger("id");
                String userUrl = url + "/" + userId;

                HttpRequest userRequest = HttpRequest.newBuilder()
                        .uri(URI.create(userUrl))
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();

                HttpResponse<String> userResponse = client.send(userRequest, HttpResponse.BodyHandlers.ofString());

                System.out.println("User info for username " + targetUsername + ": " + userResponse.body());
                break;
            }
        }
    }
}
