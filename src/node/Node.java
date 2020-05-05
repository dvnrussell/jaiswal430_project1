package node;

import java.io.Serializable;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Date;

public class Node implements Serializable {

    private static final long serialVersionUID = 4804L;
    
    private final InetAddress address;
    private Boolean status;
    private Timestamp ts;

    public Node(final InetAddress address) {
        this(address, true);
    }

    public Node(final InetAddress address, final Boolean status) {
        this.address = address;

        this.status = status;

        final Date d = new Date();
        this.ts = new Timestamp(d.getTime());
    }

    public InetAddress getAddress() {
        return address;
    }

    public Boolean getStatus() {
        return status;
    }

    public Timestamp getTimestamp() {
        return ts;
    }

    public void setTimestamp() {
        final Date d = new Date();
        ts = new Timestamp(d.getTime());
        setStatus(true);
    }

    public void setStatus(final Boolean newStatus) {
        status = newStatus;
    }

}