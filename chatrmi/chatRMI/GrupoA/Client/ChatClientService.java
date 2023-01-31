 
package chatRMI.GrupoA.Client;
 

import chatRMI.GrupoA.Server.ChatServerInterface;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;


public class ChatClientService extends UnicastRemoteObject implements ChatClientInterface {
    
    private final ChatServerInterface server;
    private final String name;
    private final JTextArea input;
    private final JTextPane output; 
    
   
    public ChatClientService(String name , ChatServerInterface server,JTextArea jtext1,JTextPane jtext2 ) throws RemoteException{
        this.name = name;
        this.server = server;
        this.input = jtext1;
        this.output = jtext2; 
        server.addClient(this);
    }
    
    
    @Override
    public void retrieveMessage(String message) throws RemoteException {
        output.setText(output.getText() + "\n" + message);
    }
    
    
    @Override
    public void sendMessage(List<String> list,String mss) {
        try {
            server.broadcastMssg(name + " : " + input.getText(),list);
        } catch (RemoteException ex) {
            System.out.println("Error Client "+ name +" Broadcast: " + ex.getMessage());
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
        JOptionPane.showMessageDialog(new JFrame(),message,"Alert",JOptionPane.WARNING_MESSAGE); 
    }
 
    @Override
    public void openChat() throws RemoteException {
        input.setEditable(true);
        input.setEnabled(true);    
    }
}
