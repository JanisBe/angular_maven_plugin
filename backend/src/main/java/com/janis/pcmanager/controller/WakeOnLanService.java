package com.janis.pcmanager.controller;

import org.springframework.stereotype.Service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Service
public class WakeOnLanService {

    public static final int PORT = 9;
    public static final String IP_STR = "192.168.10.255";
    public static final String MAC_STR = "2C:FD:A1:C1:04:44";

    public void wake() {

        try {
            byte[] macBytes = getMacBytes();
            byte[] bytes = new byte[6 + 16 * macBytes.length];
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) 0xff;
            }
            for (int i = 6; i < bytes.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
            }

            InetAddress address = InetAddress.getByName(IP_STR);
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, PORT);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();

            System.out.println("Wake-on-LAN packet sent.");
        } catch (Exception e) {
            System.out.println("Failed to send Wake-on-LAN packet: + e");
        }

    }

    private static byte[] getMacBytes() throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = WakeOnLanService.MAC_STR.split("(\\:|\\-)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }
        return bytes;
    }
}
