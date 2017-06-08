# KVClient
Client API to test the Server. Please use as follows :

1. Compile
    mvn clean install
2. Send in the Commands

    java -jar target/KVClient-1.1-SNAPSHOT.jar -c PUT -k USA -v WASHINGTON_DC (Client sends a PUT Command with a Key and a value)
    SUCCESS PUT (USA, WASHINGTON_DC) (Server Responds with Success)


    java -jar target/KVClient-1.1-SNAPSHOT.jar -c PUT -k JAPAN -v TOKYO
    SUCCESS PUT (JAPAN, TOKYO)

    java -jar target/KVClient-1.1-SNAPSHOT.jar -c GET -k USA (Client sends a GET command)
    SUCCESS GET (USA) = WASHINGTON_DC (Server Responds with SUCCESS)


    java -jar target/KVClient-1.1-SNAPSHOT.jar -c PUT -k ENGLAND -v LONDON
    SUCCESS PUT (ENGLAND, LONDON)


    java -jar target/KVClient-1.1-SNAPSHOT.jar -c GET -k USA
    SUCCESS GET (USA) = WASHINGTON_DC


    java -jar target/KVClient-1.1-SNAPSHOT.jar -c PUT -k INDIA -v NEW_DELHI (Client Sends one more PUT. Now eviction of USA should happen)
    SUCCESS PUT (INDIA, NEW_DELHI)


    java -jar target/KVClient-1.1-SNAPSHOT.jar -c GET -k USA (Verify whether Eviction has happenned)
    FAILED. Key (USA) not found (Yes, it has)


