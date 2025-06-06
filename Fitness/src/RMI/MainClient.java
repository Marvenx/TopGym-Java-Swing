package RMI;

import RMI.ChatRemote;
import RMI.ChatUI;
import RMI.Message;

import java.rmi.Naming;
import java.util.ArrayList;

public class MainClient {
    public static void main(String[] args) {
        System.out.println("Client is running...");
        String url = "rmi://127.0.0.1:9001/chat";
        try {
            ChatRemote chat = (ChatRemote) Naming.lookup(url);
            ArrayList<Message> messages = chat.getAllMsgs();
            ChatUI chatUI = new ChatUI(chat);
        } catch (Exception e) {
            System.out.println("Client is not connected: " + e);
        }
    }
}
