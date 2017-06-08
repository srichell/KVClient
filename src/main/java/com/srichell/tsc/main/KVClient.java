package com.srichell.tsc.main;

import java.io.IOException;

/**
 * Created by sridhar on 6/8/17.
 */
public class KVClient {
    private static final int DEFAULT_LISTEN_PORT = 7000;
    private static final String DEFAULT_SERVER_HOST = "localhost";

    private String serverHost;
    private int listenPort;
    private String command;
    private String key;
    private String value;

    public static void printUsage() {
        System.out.println("Usage :");
        System.out.println("\t KVClient [-h | --help]");
        System.out.println("\t \t \t  [-s | --server] <serverHost> [-p | --port] <serverPort> [[-c | --command] [GET|PUT] [-k | --key] <key> [-v | --value] <value>");
        System.exit(0);
    }

    public static void main(String[] args) {

        int listenPort = getDefaultListenPort();
        String serverHost = getDefaultServerHost();
        String command = null;
        String key = null;
        String value = null;

        for(int i = 0; i < args.length; i++) {
            if(args[i].equalsIgnoreCase("-h") || args[i].equalsIgnoreCase("--help")) {
                printUsage();
            }

            if(args[i].equalsIgnoreCase("-c") || args[i].equalsIgnoreCase("--command")) {
                command = args[i+1];
            }

            if(args[i].equalsIgnoreCase("-k") || args[i].equalsIgnoreCase("--key")) {
                key = args[i+1];
            }

            if(args[i].equalsIgnoreCase("-v") || args[i].equalsIgnoreCase("--value")) {
                value = args[i+1];
            }

            if(args[i].equalsIgnoreCase("-p") || args[i].equalsIgnoreCase("--port")) {
                listenPort = Integer.parseInt(args[i+1]);
            }

            if(args[i].equalsIgnoreCase("-s") || args[i].equalsIgnoreCase("--server")) {
                serverHost = args[i+1];
            }
        }

        if(command == null) {
            System.out.println("Please give a command to execute");
            printUsage();
        }

        KVClient client = new KVClient().
                setServerHost(serverHost).
                setListenPort(listenPort).
                setCommand(command).
                setKey(key).
                setValue(value);

        try {
            Commands.getByType(command).handle(client);

        } catch (IOException e) {
            String errorMessage = String.format("Command %s failed because of an exception %s", command, e);
            System.out.println(errorMessage);
        }
    }

    private static int getDefaultListenPort() {
        return DEFAULT_LISTEN_PORT;
    }

    private static String getDefaultServerHost() {
        return DEFAULT_SERVER_HOST;
    }

    public KVClient setListenPort(int listenPort) {
        this.listenPort = listenPort;
        return this;
    }

    public int getListenPort() {
        return listenPort;
    }

    public String getCommand() {
        return command;
    }

    public KVClient setCommand(String command) {
        this.command = command;
        return this;
    }

    public String getKey() {
        return key;
    }

    public KVClient setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public KVClient setValue(String value) {
        this.value = value;
        return this;
    }

    public String getServerHost() {
        return serverHost;
    }

    public KVClient setServerHost(String serverHost) {
        this.serverHost = serverHost;
        return this;
    }

    private enum Commands {
        GET("GET") {
            @Override
            public void handle(KVClient client) throws IOException {
                Command getCommand = new GetCommand(client);
                getCommand.handle();
            }
        },
        PUT("PUT") {
            @Override
            public void handle(KVClient client) throws IOException {
                Command putCommand = new PutCommand(client);
                putCommand.handle();
            }
        };

        private String commandType;

        Commands(String type) {
            this.commandType = type;
        }

        public String getCommandType() {
            return commandType;
        }

        public static Commands getByType(String type) {
            for (Commands command : Commands.values() ) {
                if(command.getCommandType().equalsIgnoreCase(type)) {return command;}
            }
            return null;
        }

        public abstract void handle(KVClient client) throws IOException;
    }
}
