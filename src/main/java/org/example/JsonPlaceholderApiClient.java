
package org.example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class JsonPlaceholderApiClient<jsonArray> {
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


        FileWriter fw;
        fw = new FileWriter("body.html");
        fw.write(response.body());
        fw.close();

    }

    private HttpResponse<Object> response;
    JSONArray jsonArray = new JSONArray(response.body());
     public <jsonArray> void OpenTasks(jsonArray) {

    }

}

    public static void openTasks(JSONArray tasksArray) {
        for (int i = 0; i < tasksArray.get(); i++) {
            JSONObject task = tasksArray.getJSONObject(i);
            boolean completed = task.getBoolean("completed");
            if (!completed) {
                int taskId = task.getInteger("id");
                String title = task.getString("title");

                System.out.println("Task ID: " + taskId);
                System.out.println("Title: " + title);
                System.out.println("Status: Open");
                System.out.println();
            }
        }
    }
}
