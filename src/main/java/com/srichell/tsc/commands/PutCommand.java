package com.srichell.tsc.commands;

import com.srichell.tsc.main.KVClient;

import java.io.IOException;

/**
 * Created by sridhar on 6/8/17.
 */
public class PutCommand extends Command {
    @Override
    protected String getServerCommandLine() {
        return String.format("%s %s %s\n", getClient().getCommand(), getClient().getKey(), getClient().getValue());
    }

    @Override
    public void handle() throws IOException {
        if(getClient().getKey() == null) {
            System.out.println("Please provide a key for PUT command");
            getClient().printUsage();
        }

        if(getClient().getValue() == null) {
            System.out.println("Please provide a value for PUT command");
            getClient().printUsage();
        }

        super.handle();
    }
    public PutCommand(KVClient client) {
        super(client);
    }
}
