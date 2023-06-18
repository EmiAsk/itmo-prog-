package se.ifmo.lab08.client.network;

import se.ifmo.lab08.client.Configuration;
import se.ifmo.lab08.client.controller.MainFormController;
import se.ifmo.lab08.common.dto.Credentials;
import se.ifmo.lab08.common.dto.request.Request;
import se.ifmo.lab08.common.dto.response.*;

import javax.naming.TimeLimitExceededException;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Client implements AutoCloseable {
    private static final int BATCH = Configuration.BATCH;

    private static final long MAX_DELAY_SECONDS = Configuration.MAX_DELAY_SECONDS;

    private final DatagramChannel connection;

    private final InetSocketAddress address;

    private Credentials credentials;

    private static Client client;

    private final BlockingQueue<Response> responses = new LinkedBlockingQueue<>();

    public static Client getInstance() {
        return client;
    }

    private Client(DatagramChannel connection, InetSocketAddress address) {
        this.connection = connection;
        this.address = address;
    }

    public static Client connect(String host, int port) throws IOException {
        if (client == null) {
            var dc = DatagramChannel.open();
            dc.configureBlocking(false);
            client = new Client(dc, new InetSocketAddress(host, port));
        }
        return client;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Credentials credentials() {
        return credentials;
    }

    public boolean isAuthorized() {
        return credentials != null;
    }

    public void send(byte[] data) throws IOException {
        int n = (int) Math.ceil((double) data.length / (BATCH - 1));
        for (int i = 0; i < n; i++) {
            byte[] batch = new byte[BATCH];
            System.arraycopy(data, i * (BATCH - 1), batch, 0, Math.min(data.length - i * (BATCH - 1), BATCH - 1));
            batch[BATCH - 1] = (byte) ((i + 1 == n) ? 1 : 0);
            connection.send(ByteBuffer.wrap(batch), address);
        }
    }

    public void send(Request request) throws IOException {
        request.setCredentials(credentials);
        var byteStream = new ByteArrayOutputStream();
        var objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(request);
        send(byteStream.toByteArray());
    }

    public Response receiveResponse() throws IOException, ClassNotFoundException {
        byte[] data = receive();
        var byteStream = new ByteArrayInputStream(data);
        var objectStream = new ObjectInputStream(byteStream);

        return (Response) objectStream.readObject();
    }

    public byte[] receive() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BATCH);
        SocketAddress address = null;
        var data = new ByteArrayOutputStream();

        while (true) {
            while (address == null) {
                address = connection.receive(buffer);
            }
            data.write(Arrays.copyOf(buffer.array(), BATCH - 1));
            if (buffer.array()[BATCH - 1] == (byte) 1) {
                return data.toByteArray();
            }
            address = null;
            buffer.clear();
        }
    }

    public Response sendAndReceive(Request request) throws IOException, ClassNotFoundException, TimeLimitExceededException {
        send(request);
        return receiveResponse();
    }

    public Response getResponse() throws TimeLimitExceededException {
        try {
            var response = responses.poll(MAX_DELAY_SECONDS, TimeUnit.SECONDS);
            if (response == null) {
                throw new TimeLimitExceededException("Server not available. Try later");
            }
            return response;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void startListening() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                var response = receiveResponse();
                if (response instanceof BroadcastResponse<?> r) {
                    handleBroadcastResponse(r);
                } else if (response instanceof CollectionResponse r) {
                    handleCollectionResponse(r);
                } else {
                    responses.put(response);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleCollectionResponse(CollectionResponse response) {
        MainFormController.updateCollectionList(response.flats());
    }

    private void handleBroadcastResponse(BroadcastResponse<?> response) {
        var table = MainFormController.getMainFormController().getTableViewHandler();
        if (response instanceof AddModelResponse addResponse) {
            table.addElement(addResponse.getData());
        } else if (response instanceof UpdateModelResponse updateResponse) {
            table.updateElement(updateResponse.getData());
        } else if (response instanceof RemoveModelResponse removeResponse) {
            table.removeElement(removeResponse.getData());
        }
    }

    public void close() throws IOException {
        connection.close();
    }

}