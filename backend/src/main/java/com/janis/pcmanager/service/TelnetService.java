package com.janis.pcmanager.service;

import org.apache.commons.net.telnet.TelnetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.lang.Integer.parseInt;

@Service
public class TelnetService {
    private static final Logger logger = LoggerFactory.getLogger(TelnetService.class);

    public void connect(String ip, String port) {
        String username = "root";
        String password = "HasloRouter12#";

        try {
            TelnetClient client = new TelnetClient();
            client.connect(ip, parseInt(port));
            logger.trace("TelnetCommandThread Username ({})", username);
            receive(client); // user:
            send(client, username);
            receive(client); // password:
            send(client, password);
            receive(client); // welcome text
            send(client, "/usr/sbin/wol -i 192.168.10.255 -p 7 2C:FD:A1:C1:04:44");
            Thread.sleep(1000L); // response not needed - may be interesting
            receive(client);
            logger.trace("TelnetCommandThread ok send");
            client.disconnect();
        } catch (Exception e) {
            logger.warn("Error processing command", e);
        }
    }

    private void send(TelnetClient client, String data) {
        logger.trace("Sending data ({})...", data);
        try {
            data += "\r\n";
            client.getOutputStream().write(data.getBytes());
            client.getOutputStream().flush();
        } catch (IOException e) {
            logger.warn("Error sending data", e);
        }
    }

    private String receive(TelnetClient client) {
        StringBuffer strBuffer;
        try {
            strBuffer = new StringBuffer();

            byte[] buf = new byte[4096];
            int len;

            Thread.sleep(750L);

            while ((len = client.getInputStream().read(buf)) != 0) {
                strBuffer.append(new String(buf, 0, len));

                Thread.sleep(750L);

                if (client.getInputStream().available() == 0) {
                    break;
                }
            }

            return strBuffer.toString();

        } catch (Exception ignored) {
        }
        return null;
    }
}
