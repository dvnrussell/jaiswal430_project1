package app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class HACServer {
    private DatagramSocket socket;
    private final NodeListDatagram nodeNetwork;

    public HACServer() {
        socket = null;
        nodeNetwork = new NodeListDatagram();
    }

    public void createAndListenSocket() {
        try {
            socket = new DatagramSocket(9876);
            final byte[] incomingData = new byte[1024];

            while (true) {

                final DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);

                socket.receive(incomingPacket);

                final String message = new String(incomingPacket.getData());
                final InetAddress address = incomingPacket.getAddress();
                final int port = incomingPacket.getPort();

                printRequestToScreen(message, address, port);

                sendResponseToClient(address, port);

            }
        } catch (final SocketException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void printRequestToScreen(final String message, final InetAddress address, final int port) {
        System.out.println("Received message from client: " + message);
        System.out.println("Client IP:" + address.getHostAddress());
        System.out.println("Client port:" + port);
    }

    private void sendResponseToClient(final InetAddress address, final int port)
            throws IOException {

        updateNodeNetwork(address);

        final byte[] data = serializeNodeList(nodeNetwork);

        final DatagramPacket replyPacket = new DatagramPacket(data, data.length, address, port);

        socket.send(replyPacket);
    }

    private byte[] serializeNodeList(final NodeListDatagram nodeList) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(nodeList);
        return baos.toByteArray();
    }

    private void updateNodeNetwork(final InetAddress address) {
        for (final Node n : nodeNetwork.getNodeList()) {
            if (address.getHostAddress().equals(n.getAddress().getHostAddress())) {
                n.setTimestamp();
                return;
            }
        }
        nodeNetwork.addNode(new Node(address));
    }

    public void monitorNodeAvailability() {

    }
}