package com.srichell.tsc.commands;

import com.srichell.tsc.main.KVClient;

import java.io.IOException;

/**
 * Created by sridhar on 6/8/17.
 */
public class GetCommand extends Command {
    @Override
    protected String getServerCommandLine() {
        return String.format("%s %s\n", getClient().getCommand(), getClient().getKey());
    }

    @Override
    public void handle() throws IOException {
        if(getClient().getKey() == null) {
            System.out.println("Please provide a key for GET command");
            getClient().printUsage();
        }
        super.handle();
    }

    public GetCommand(KVClient client) {
        super(client);
    }
}
