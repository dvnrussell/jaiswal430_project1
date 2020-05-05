package app;

import java.util.ArrayList;
import java.util.TimerTask;
import java.sql.Timestamp;
import java.util.Date;

public class NodeMonitor extends TimerTask {

    private final ArrayList<Node> nodeList;
    private final int msTolerance;
    private final static int defaultTolerance = 30000;

    public NodeMonitor(final ArrayList<Node> nodeList) {
        this(nodeList, defaultTolerance);
    }

    public NodeMonitor(final ArrayList<Node> nodeList, final int msTolerance) {
        this.nodeList = nodeList;
        this.msTolerance = msTolerance;
    }

    public void run() {
        final Date d = new Date();
        final Timestamp ts = new Timestamp(d.getTime());

        for (final Node n : nodeList) {
            if (ts.getTime() - msTolerance > n.getTimestamp().getTime()) {
                n.setStatus(false);
            }
        }
    }
}