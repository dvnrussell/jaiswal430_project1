package app;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

public class HACClient {
    private DatagramSocket socket;
    private Random notificationTimer;
    private final int maxWaitInterval = 30000;

    public HACClient() {
        socket = null;
        notificationTimer = new Random();
    }

    public void createAndListenSocket(String addr) {
        while(true) {
            try {
                socket = new DatagramSocket();
                final InetAddress address = InetAddress.getByName(addr);
                final byte[] incomingData = new byte[1024];
                final String sentence = "Test sentence";
                final byte[] data = sentence.getBytes();
                final DatagramPacket sendPacket = new DatagramPacket(data, data.length, address, 9876);
                socket.send(sendPacket);

                System.out.println("Message sent from client");
                final DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);

                socket.receive(incomingPacket);

                final NodeListDatagram datagram = (NodeListDatagram) deserializeObject(incomingPacket.getData());

                System.out.println("Response from server: ");

                for(Node n : datagram.getNodeList()) {
                    System.out.println(n.getAddress().getHostAddress()
                            + " --- " + (n.getStatus() ? "active as of " : "dead: last seen ") + n.getTimestamp() );
                }

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

    private Object deserializeObject(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInput in = new ObjectInputStream(bais);
        return in.readObject(); 
    }
}