import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws JSONException, IOException {
        final String template = "https://codeforces.com/api/user.status?handle=%s";
        Scanner sc = new Scanner(System.in);
        while (true) {
            String name = sc.next();
            URL url = new URL(String.format(template, name));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            StringBuilder result = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            } catch (IOException e) {
                System.out.println("Неправильный ник, введите еще раз!");
                continue;
            }
            JSONArray obj = new JSONObject(result.toString()).getJSONArray("result");
            Set<String> cntSolves = new HashSet<>();
            for (int i = 0; i < obj.length(); ++i) {
                try {
                    String id = obj.getJSONObject(i).getJSONObject("problem").getInt("contestId") + obj.getJSONObject(i).getJSONObject("problem").getString("index");
                    String status = obj.getJSONObject(i).getString("verdict");
                if (status.equals("OK"))
                    cntSolves.add(id);
                }
                 catch (JSONException e) {
                    continue;
                }

            }
            System.out.println("Количество правильно решенных задач: " + name + " " + cntSolves.size());
        }

    }
}
