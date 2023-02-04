package chatRMI.GrupoA.Client;

import chatRMI.GrupoA.Server.ChatServerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class ChatClientService extends UnicastRemoteObject implements ChatClientInterface {

    private final ChatServerInterface server;
    private final String name;
    private final JTextArea input;
    private final JTextPane output;
    private final JTextPane contacts;

    public ChatClientService(String name, ChatServerInterface server, JTextArea jtext1, JTextPane jtext2, JTextPane jtext3) throws RemoteException {
        this.name = name;
        this.server = server;
        this.input = jtext1;
        this.output = jtext2;
        this.contacts = jtext3;
        server.addClient(this);
    }

    @Override
    public void retrieveMessage(String message) throws RemoteException {
        output.setText(output.getText() + "\n" + message);
    }

    @Override
    public void sendMessage(List<String> list, String mss) {
        try {
            server.broadcastMssg(name + " : " + input.getText(), list);
        } catch (RemoteException ex) {
            System.out.println("Error Client " + name + " Broadcast: " + ex.getMessage());
        }
    }

    @Override
    public String getClientName() {
        return name;
    }

    @Override
    public void closeChat(String message) throws RemoteException {
        input.setEditable(false);
        input.setEnabled(false);
        JOptionPane.showMessageDialog(new JFrame(), message, "Alert", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void openChat() throws RemoteException {
        input.setEditable(true);
        input.setEnabled(true);
    }
    
    @Override
    public void updateContactList(List<String> users) throws RemoteException {
        contacts.setText("");
        for (String user : users) {
            contacts.setText(user + "\n");
        }
    }
}
