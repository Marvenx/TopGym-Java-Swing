package RMI;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class MainServer {
    public static void main(String[] args) {
        System.out.println("Server is running...");
        String url = "rmi://localhost:9001/chat";
        try {
            ChatRemote chat = new ChatImplmentation();
            LocateRegistry.createRegistry(9001);
            Naming.rebind(url, chat);
            System.out.println("Server is RUNNING...");
        } catch (Exception e) {
            System.out.println("Server is not connected: " + e);
        }
    }
}
