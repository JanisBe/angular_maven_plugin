package com.janis.pcmanager.service;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.ByteArrayOutputStream;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
@Log4j2
@Service
public class SSHService {

	public  void connect(String username, String password,
		String host, int port) throws Exception {

		Session session = null;
		ChannelExec channel = null;

		try {
			session = new JSch().getSession(username, host, port);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			log.info("SSH connected");
			channel = (ChannelExec) session.openChannel("exec");
			channel.setCommand("sh run.sh");
			channel.setCommand("command");
			ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
			channel.setOutputStream(responseStream);
			channel.connect();

			while (channel.isConnected()) {
				Thread.sleep(100);
			}

			String responseString = new String(responseStream.toByteArray());
			System.out.println(responseString);
		} finally {
			if (session != null) {
				session.disconnect();
			}
			if (channel != null) {
				channel.disconnect();
			}
		}
	}
}
