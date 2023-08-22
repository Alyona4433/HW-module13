package org.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Comments {
    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://jsonplaceholder.typicode.com/users/1/posts"; // Замінить 1 з бажаним user ID

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

        if (jsonArray.size() > 0) {
            JSONObject lastPost = jsonArray.getJSONObject(jsonArray.size() - 1);
            int postId = lastPost.getIntValue("id");
            int userId = lastPost.getIntValue("userId");

            String commentsUrl = "https://jsonplaceholder.typicode.com/posts/" + postId + "/comments";

            HttpRequest commentsRequest = HttpRequest.newBuilder()
                    .uri(URI.create(commentsUrl))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> commentsResponse = client.send(commentsRequest, HttpResponse.BodyHandlers.ofString());

            System.out.println("commentsResponse.statusCode() = " + commentsResponse.statusCode());

            // Зберегти у файл
            String filename = "user-" + userId + "-post-" + postId + "-comments.json";
            FileWriter fw = new FileWriter(filename);
            fw.write(commentsResponse.body());
            fw.close();

            System.out.println("Comments saved to " + filename);
        } else {
            System.out.println("No posts found for the user.");
        }
    }
}
