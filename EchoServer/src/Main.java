
public class Main {
    public static void main(String[] args) {
        int DEFAULT_THREADS_COUNT = 3;

        if (args.length != 1) {
            System.out.println("Wrong amount of args. Please, enter your port");
            System.exit(-1);
        }

        try {
            Server server = new Server(DEFAULT_THREADS_COUNT, Integer.parseInt(args[0]));
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
