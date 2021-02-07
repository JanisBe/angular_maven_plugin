package com.janis.pcmanager.controller;

import com.janis.pcmanager.service.OnlineCheckService;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Log
@RestController
@CrossOrigin
@RequestMapping("/")
public class MainController {

    private final OnlineCheckService onlineCheckService;
    private final WakeOnLanService wolService;

    public MainController(OnlineCheckService onlineCheckService, WakeOnLanService wolService) {
        this.onlineCheckService = onlineCheckService;
        this.wolService = wolService;
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
    public String healthcheck() {
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
    public String wakeUp() {
        wolService.wake();
        return "Komp włączony";
    }

    @GetMapping("winamp/{command}")
    public void runWinampCommands(@PathVariable String command) throws IOException {
        onlineCheckService.runWinamp("188.121.23.28", "999", command);
    }

    @GetMapping("runPlex")
    public void runPlex() throws IOException {
        onlineCheckService.runPlex("188.121.23.28", "999");
    }

    @GetMapping("isPlexRunning")
    public void isPlexOnline() throws IOException {
        onlineCheckService.isPlexRunning("188.121.23.28", "999");
    }
}
