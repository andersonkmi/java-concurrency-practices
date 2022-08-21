package org.codecraftlabs.concurrency.future;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;

public class APICaller implements Callable<String> {
    private final String urlAddress;

    public APICaller(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    @Override
    public String call() throws IOException {
        return callUrl();
    }

    private String callUrl() throws IOException {
        URL url = new URL(urlAddress);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();
        return content.toString();
    }

    public static void main(String[] args) throws IOException {
        APICaller caller = new APICaller("https://www.uol.com.br");
        System.out.println(caller.call());
    }
}
