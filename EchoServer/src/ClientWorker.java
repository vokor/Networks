import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class ClientWorker implements Runnable {
    private Queue<Socket> queue = new ConcurrentLinkedQueue<>();
    private Map<Socket, Pair<PrintWriter, BufferedReader>> socketStreams = new HashMap<>();
    private volatile boolean stopFlag = false;

    @Override
    public void run() {
        while (!stopFlag) {
            for (Socket socket : queue) {
                Pair<PrintWriter, BufferedReader> message = socketStreams.get(socket);
                try {
                    if (message.getValue().ready()) {
                        String msg = message.getValue().readLine();
                        System.out.println("Received message: " +
                                msg + " from " + socket);
                        message.getKey().println(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addClient(Socket socket) {
        try {
            socketStreams.put(socket, new Pair<>(new PrintWriter(socket.getOutputStream(), true),
                    new BufferedReader(new InputStreamReader(socket.getInputStream()))));
            queue.add(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int amountOfClients() {
        return queue.size();
    }
}