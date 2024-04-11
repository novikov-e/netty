package ru.novikov.client;

import java.nio.channels.SocketChannel;

public class Client {
    private static Network network;
    private static String data = "";

    public static void main(String[] args) {
        network = new Network((callbackArgs) -> {
            data = data + "\n" + (String) callbackArgs[0];
        });
    }
}
