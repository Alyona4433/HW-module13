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

public class Users {
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


        FileWriter fw = new FileWriter("users.json");
        fw.write(response.body());
        fw.close();


        JSONArray jsonArray = JSON.parseArray(response.body());

        for (int i = 0; i < jsonArray.size(); i++) {

            JSONObject user = jsonArray.getJSONObject(i);
            System.out.println("User ID: " + user.getInteger("id"));
            System.out.println("Name: " + user.getString("name"));
            System.out.println("Username: " + user.getString("username"));
            System.out.println("Email: " + user.getString("email"));
            System.out.println();
        }
    }
}
