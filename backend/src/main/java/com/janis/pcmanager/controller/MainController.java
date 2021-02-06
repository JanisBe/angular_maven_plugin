package com.janis.pcmanager.controller;

import com.janis.pcmanager.service.OnlineCheckService;
import com.janis.pcmanager.service.SSHService;
import com.janis.pcmanager.service.TelnetService;
import java.io.IOException;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Log
@RestController
@CrossOrigin
@RequestMapping("/")
public class MainController {

	private final OnlineCheckService onlineCheckService;
	private final TelnetService telnetService;
	private final SSHService sshService;

	public MainController(OnlineCheckService onlineCheckService, TelnetService telnetService,
		SSHService sshService) {
		this.onlineCheckService = onlineCheckService;
		this.telnetService = telnetService;
		this.sshService = sshService;
	}

	@CrossOrigin(origins = "http://localhost:998")
	@GetMapping("isOnline")
	public String isOnline() {
		log.info("isOnline");
		try {
			return onlineCheckService.checkOnline("188.121.23.28", "999");
		} catch (IOException e) {
			return "false";
		}
	}
	@GetMapping("healthcheck")
	public String healthcheck(){
		return "true";
	}

	@GetMapping("shutDown")
	public String shutDown() {
		log.info("shutDown");
		try {
			return onlineCheckService.shutDown("188.121.23.28", "999");
		} catch (IOException e) {
			return "false";
		}
	}

	@PostMapping("wakeUp")
	public String wakeUp() throws Exception {
		telnetService.connect("188.121.23.28", "2323");
//		sshService.connect("root","HasloRouter12#","192.168.10.1",2222);
		return "Komp włączony";
	}

	@GetMapping("winamp/{command}")
	public void runWinampCommands(@PathVariable String command) throws IOException {
		onlineCheckService.runWinamp("188.121.23.28", "999", command);
	}

	@GetMapping("runPlex")
	public void runPlex(String command) throws IOException {
		onlineCheckService.runPlex("188.121.23.28", "999");
	}

	@GetMapping("isPlexRunning")
	public void isPlexOnline(String command) throws IOException {
		onlineCheckService.isPlexRunning("188.121.23.28", "999");
	}
}
