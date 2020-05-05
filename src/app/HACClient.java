package app;

import handlers.*;
import node.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

public class HACClient {
    private DatagramSocket socket;
    private final Random notificationTimer;
    private final int maxWaitInterval = 30000;

    public HACClient() {
        socket = null;
        notificationTimer = new Random();
    }

    public void createAndListenSocket(final String addr) {
        while (true) {
            try {
                socket = new DatagramSocket();

                final InetAddress address = InetAddress.getByName(addr);
                final String sentence = "Test sentence";
                final byte[] data = sentence.getBytes();
                
                socket.send(generatePacket(address, data));

                System.out.println("Message sent from client to " 
                    + address.getHostAddress());
                    
                final byte[] incomingData = new byte[1024];
                final DatagramPacket recvPacket = generatePacket(incomingData);

                socket.receive(recvPacket);
                final byte[] recvData = recvPacket.getData();

                final NodeListDatagram datagram = (NodeListDatagram) 
                        SerializationHandler.deserializeObject(recvData);
                FileHandler.writeBytesToFile(recvData, "node_list.bin");

                printServerResponse(datagram);

                socket.close();

                Thread.sleep(notificationTimer.nextInt(maxWaitInterval));

            } catch (final UnknownHostException e) {
                e.printStackTrace();
            } catch (final SocketException e) {
                e.printStackTrace();
            } catch (final IOException e) {
                e.printStackTrace();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            } catch (final ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void printServerResponse(final NodeListDatagram datagram) {
        System.out.println("Response from server: ");

        for (final Node n : datagram.getNodeList()) {
            System.out.println(n.getAddress().getHostAddress() + " --- "
                    + (n.getStatus() ? "active as of " : "dead: last seen ")
                    + n.getTimestamp());
        }
    }

    private DatagramPacket generatePacket(final byte[] data) {
        return new DatagramPacket(data, data.length);
    }

    private DatagramPacket generatePacket(final InetAddress address, final byte[] data) {
        final int portNumber = 9876;
        return new DatagramPacket(data, data.length, address, portNumber);
    }
}