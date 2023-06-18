package se.ifmo.lab08.server.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.ifmo.lab08.common.dto.request.Request;
import se.ifmo.lab08.server.Configuration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.Entry;

public class ReceivingManager {
    private static final Logger logger = LoggerFactory.getLogger(SendingManager.class);
    private static final int BATCH = Configuration.BATCH;
    private static final int END_SIZE = Configuration.END_SIZE;

    private final DatagramSocket connection;
    private final Map<SocketAddress, byte[]> clientData;

    public ReceivingManager(DatagramSocket connection) {
        this.connection = connection;
        this.clientData = new HashMap<>();
    }

    public Entry<SocketAddress, byte[]> receive() throws IOException {
        var batch = new byte[BATCH];
        var dp = new DatagramPacket(batch, batch.length);

        while (true) {
            connection.receive(dp);
            var address = dp.getSocketAddress();
            var value = clientData.getOrDefault(address, new byte[]{});
            var data = new ByteArrayOutputStream();
            data.write(value);
            data.write(Arrays.copyOf(batch, BATCH - END_SIZE));
            clientData.put(address, data.toByteArray());
            if (dp.getData()[BATCH - END_SIZE] == (byte) 1) {
                var objBytes = clientData.remove(address);
                clientData.put(address, new byte[]{});
                return Map.entry(address, objBytes);
            }
        }
    }

    public Entry<SocketAddress, Request> receiveRequest(Entry<SocketAddress, byte[]> pair) throws IOException, ClassNotFoundException {
        var byteStream = new ByteArrayInputStream(pair.getValue());
        var objectStream = new ObjectInputStream(byteStream);
        var request = Map.entry(pair.getKey(), (Request) objectStream.readObject());
        logger.info("Request <{}> received from {}", request.getValue().getClass().getSimpleName(), request.getKey());
        return request;
    }

    public Map<SocketAddress, byte[]> getClientData() {
        return clientData;
    }
}
