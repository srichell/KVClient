package com.srichell.tsc.commands;

import com.srichell.tsc.main.KVClient;

import java.io.*;
import java.net.Socket;

/**
 * Created by sridhar on 6/8/17.
 */
public abstract class Command {
    private KVClient client;
    private static final String END_OF_COMMAND = "EOC";
    private static final String END_OF_RESPONSE = "EOR";

    protected abstract String getServerCommandLine();

    public void handle() throws IOException {

        Socket clientSocket = new Socket(getClient().getServerHost(), getClient().getListenPort());
        DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());

        String serverCommandLine = getServerCommandLine();

        os.writeBytes(serverCommandLine);
        os.flush();
        // Signal End of Command
        os.writeBytes("EOC");
        os.flush();

        DataInputStream is = new DataInputStream(clientSocket.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String responseLine = null;

        while ((responseLine = reader.readLine()) != null) {
            if(responseLine.indexOf(END_OF_RESPONSE) != -1) {
                // Server indicated end of response
                break;
            }
            System.out.println("\n" + responseLine);
        }
        is.close();
        os.close();
        clientSocket.close();
    }

    public Command(KVClient client) {
        // Client Command will always be in the format (space seperated)
        // Command key <optional value>
        setClient(client);
    }


    public void setClient(KVClient client) {
        this.client = client;
    }

    public KVClient getClient() {
        return client;
    }
}
