package org.example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DelObject {
    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://jsonplaceholder.typicode.com/users";

        //GET
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println("GET response.statusCode() = " + getResponse.statusCode());

        //response
        JSONArray jsonArray = JSONArray.parseArray(getResponse.body());

        if (jsonArray.size() > 0) {
            JSONObject firstUser = jsonArray.getJSONObject(0);
            String firstUserId = firstUser.getString("id");

            // DELETE
            String deleteUrl = url + "/" + firstUserId;
            HttpRequest deleteRequest = HttpRequest.newBuilder()
                    .uri(URI.create(deleteUrl))
                    .DELETE()
                    .build();

            HttpResponse<Void> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.discarding());

            int deleteStatusCode = deleteResponse.statusCode();
            if (deleteStatusCode >= 200 && deleteStatusCode < 300) {
                System.out.println("User deleted successfully. Status code: " + deleteStatusCode);
            } else {
                System.out.println("Failed to delete user. Status code: " + deleteStatusCode);
            }
        } else {
            System.out.println("No users found in the response.");
        }
    }
}
