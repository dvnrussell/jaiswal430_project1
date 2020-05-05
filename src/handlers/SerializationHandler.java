package handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationHandler {

	public static Object deserializeObject(final byte[] data) 
	throws IOException, ClassNotFoundException {
	    final ByteArrayInputStream bais = new ByteArrayInputStream(data);
	    final ObjectInput in = new ObjectInputStream(bais);
	    return in.readObject(); 
	}

	public static byte[] serializeObject(final Object obj) throws IOException {
	    final ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
	    final ObjectOutputStream oos = new ObjectOutputStream(baos);
	    oos.writeObject(obj);
	    return baos.toByteArray();
	}
    
}