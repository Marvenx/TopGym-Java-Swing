package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatRemote extends Remote {
    ArrayList<Message> getAllMsgs() throws RemoteException;
    void addMessage(Message ch) throws RemoteException;
}
