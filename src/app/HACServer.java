package app;

import handlers.SerializationHandler;
import node.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class HACServer {
    private DatagramSocket socket;
    public final NodeListDatagram nodeNetwork;

    public HACServer() {
        socket = null;
        nodeNetwork = new NodeListDatagram();
    }

    public void createAndListenSocket() {
        nodeNetwork.monitorNodeAvailability();
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
                updateNodeNetwork(address);
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

        final byte[] data = SerializationHandler.serializeObject(nodeNetwork);

        final DatagramPacket replyPacket = new DatagramPacket(data, data.length, address, port);

        socket.send(replyPacket);
    }

    private void updateNodeNetwork(final InetAddress address) {
        Boolean addNode = true;
        for (final Node n : nodeNetwork.getNodeList()) {
            if (address.getHostAddress().equals(n.getAddress().getHostAddress())) {
                n.setTimestamp();
                addNode = false;
            }
        }
        if (addNode) 
            nodeNetwork.addNode(new Node(address));

        for(final Node n : nodeNetwork.getNodeList()) {
            System.out.println(n.getAddress().getHostAddress() + " --- "
                    + (n.getStatus() ? "active as of " : "dead: last seen ")
                    + n.getTimestamp());
        }
    }
}