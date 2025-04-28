package RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ChatImplmentation extends UnicastRemoteObject implements ChatRemote{
    ArrayList<Message> discussion;

    public ChatImplmentation() throws RemoteException {
        discussion = new ArrayList<Message>();
    }
    @Override
    public ArrayList<Message> getAllMsgs() throws RemoteException {
        return discussion;
    }

    @Override
    public void addMessage(Message ch) throws RemoteException {
        discussion.add(ch);
    }
}