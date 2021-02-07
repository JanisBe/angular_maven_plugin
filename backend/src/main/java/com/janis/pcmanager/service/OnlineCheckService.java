package com.janis.pcmanager.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.net.telnet.TelnetClient;
import java.util.Base64;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static java.lang.Integer.parseInt;

@Log4j2
@Service
public class OnlineCheckService {
    private String encodedAuth = Base64.getEncoder().encodeToString(("root" + ":" + "tajne").getBytes(StandardCharsets.UTF_8));

    public String checkOnline(String ip, String port) throws IOException {
        URL url = new URL("http://" + ip + ":" + port + "/isOnline");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(500);
        con.setReadTimeout(500);

        con.setRequestProperty("Authorization",
                "Basic " + encodedAuth);
        if (con.getInputStream() != null) {
            return "true";
        } else {
            return "false";
        }
    }

    public void runPlex(String ip, String port) throws IOException {
        URL url = new URL("http://" + ip + ":" + port + "/runPlex");
        connect(url);
    }

    public void isPlexRunning(String ip, String port) throws IOException {
        URL url = new URL("http://" + ip + ":" + port + "/isPlexRunning");
        connect(url);
    }

    public String shutDown(String ip, String port) throws IOException {
        URL url = new URL("http://" + ip + ":" + port + "/shutDown");
        connect(url);
        return "Komp zamknięty";
    }

    private String connect(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(500);
        con.setReadTimeout(500);
        con.setRequestProperty("Authorization",
                "Basic " + encodedAuth);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        log.info("telnet connected");
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content.toString();
    }

    public String runWinamp(String ip, String port, String command) throws IOException {
        URL url = new URL("http://" + ip + ":" + port + "/winamp/" + command);
        connect(url);
        return "Komp zamknięty";
    }

    private boolean ping(String ip, String port) {
        try {

            TelnetClient telnetClient = new TelnetClient();
            telnetClient.setDefaultTimeout(500);
            telnetClient.connect(ip, parseInt(port));
            telnetClient.disconnect();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
