import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    List<ClientWorker> workerList;
    ServerSocket serverSocket;
    int port;

    public Server(int threadCount, int port1) throws Exception {
        port = port1;
        serverSocket = new ServerSocket(port);
        workerList = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            workerList.add(new ClientWorker());
        }
    }

    public void start() {
        System.out.println("Started server on port " + port);
        for (ClientWorker worker: workerList) {
            Thread childTread = new Thread(worker);
            childTread.start();
        }
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Socket connected: " + clientSocket);
                int it = 0;
                for (int i = 1; i < workerList.size(); i++) {
                    if (workerList.get(it).amountOfClients() > workerList.get(i).amountOfClients())
                        it = i;
                }
                workerList.get(it).addClient(clientSocket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
