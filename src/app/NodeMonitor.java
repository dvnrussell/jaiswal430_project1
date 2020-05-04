package app;

import java.util.ArrayList;
import java.util.TimerTask;
import java.sql.Timestamp;
import java.util.Date;

public class NodeMonitor extends TimerTask {

    private ArrayList<Node> nodeList;
    private int msTolerance;
    private final static int defaultTolerance = 30000;

    public NodeMonitor(ArrayList<Node> nodeList) {
        this(nodeList, defaultTolerance);
    }

    public NodeMonitor(ArrayList<Node> nodeList, int msTolerance) {
        this.nodeList = nodeList;
        this.msTolerance = msTolerance;
    }

    public void run() {
        Date d = new Date();
        Timestamp ts = new Timestamp(d.getTime());

        for (Node n : nodeList) {
            if (ts.getTime() - msTolerance > n.getTimestamp().getTime()) {
                n.setStatus(false);
            }
        }
    }
}