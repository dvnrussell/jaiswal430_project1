package node;

import java.io.Serializable;
import java.util.ArrayList;

public class NodeListDatagram implements Serializable{

    private static final long serialVersionUID = 4803L;
    private static final String version = "0.1.0";
    private final ArrayList<Node> nodeList;

    public NodeListDatagram() {
        nodeList = new ArrayList<Node>();
    }

    public NodeListDatagram(final ArrayList<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public void addNode(final Node nodeToAdd) {
        nodeList.add(nodeToAdd);
    }

    public static String getVersion() {
        return version;
    }

    public String toString() {
        String objectString = "";

        for (Node n : nodeList) {
            objectString += n.getAddress().getHostAddress();
            objectString += '\n';
        }

        return objectString;
    }

}