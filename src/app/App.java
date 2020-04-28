package app;

public class App {
    public static void main(final String[] args) throws Exception {

        if (args.length < 1 || args.length > 2) {
            throw new IllegalArgumentException(
                "Only 1 or 2 arguments allowed"
            );
        } else if (args.length == 2 && args[0].equals("c")) {

            final String addr = IPFromFileFactory.convertFile(args[1]);
            final Thread clientThread = new Thread(() -> startClient(addr));
            clientThread.start();

        } else if (args.length == 2 && args[0].equals("p")) {

            final String addr = IPFromFileFactory.convertFile(args[1]);

            final Thread clientThread = new Thread(() -> startClient(addr));
            final Thread serverThread = new Thread(() -> startServer());
            serverThread.start();
            clientThread.start();

        } else if (args[0].equals("s")) {

            final Thread serverThread = new Thread(() -> startServer());
            serverThread.start();

        } else {
            throw new IllegalArgumentException("Argument not accepted");
        }
    }

    public static void startServer() {
        final HACServer server = new HACServer();
        server.createAndListenSocket();
    }

    public static void startClient(String addr) {
        final HACClient client = new HACClient();
        client.createAndListenSocket(addr);
    }
}