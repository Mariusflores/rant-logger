package io.rantlogger;

import io.babyredis.client.BabyRedisClient;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

public class RantLogger {
    public static final String RANT_SET = "rants";

    public static void help() {
        System.out.println("=== Rant Logger ===");
        System.out.println("Usage: rant <message>        Log a rant");
        System.out.println("       rant list             List all rants");
        System.out.println("       rant find <id>        Find a specific rant");
        System.out.println("       rant delete <id>      Delete a rant");
        System.out.println("====================");
        System.out.println("\r");
        System.out.println("\r");
    }

    public static void main(String[] args) {
        if(args.length == 0){
            help();
            return;
        }

        try (BabyRedisClient client = new BabyRedisClient("localhost", 6379)) {

            String command = args[0].toUpperCase();

            switch (command) {
                case "LIST" -> {
                    String[] rants = client.sMembers(RANT_SET);
                    for(String key: rants){
                        Timestamp ts = new Timestamp(Long.parseLong(key.split(":")[1]));
                        System.out.println(key + " → " + client.get(key) + " - Recorded : " + new Date(ts.getTime()) );
                    }
                }
                case "FIND" -> {
                    if(args.length < 2){
                        System.out.println("Usage: rant find <key>");
                        return;
                    }
                    String key = args[1];
                    String response = client.get(key);

                    System.out.println(response);

                }
                case "DELETE" -> {
                    if(args.length < 2){
                        System.out.println("Usage: rant find <key>");
                        return;
                    }
                    String key = args[1];
                    client.sRem(RANT_SET, key);
                    client.delete(key);

                    System.out.println("Deleted.");

                }
                case "CLEAR" -> {
                    String[] rants = client.sMembers(RANT_SET);
                    for (String key : rants){
                        client.delete(key);
                    }
                    client.sRem(RANT_SET, rants);
                    System.out.println("Cleared successfully.");
                }
                case "COUNT" -> {
                    String[] rants = client.sMembers(RANT_SET);

                    System.out.println("Number of rants: " + rants.length);
                }
                default -> {
                    // everything else is a rant message
                    String message = String.join(" ", args);

                    String key = String.format("rant:%d", System.currentTimeMillis());
                    // Add key to a set
                    client.sAdd(RANT_SET, key);
                    client.set(key, message);
                    System.out.println("Rant logged: " + key);
                }
            }
        }
    }
}
