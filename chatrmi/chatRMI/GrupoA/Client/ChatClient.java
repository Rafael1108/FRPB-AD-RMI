package chatRMI.GrupoA.Client;

import chatRMI.GrupoA.Listeners.ChatListeners;
import chatRMI.GrupoA.Server.ChatConfig;
import chatRMI.GrupoA.Server.ChatServerInterface;
import java.awt.Color;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Programa cliente del chat.
 */
public class ChatClient implements Runnable {

    private ChatServerInterface chatServer;

    /**
     *
     * @param args
     * @throws java.rmi.RemoteException
     */
    public static void main(String[] args) throws RemoteException {
        ChatClient chat = new ChatClient();
        chat.run();
    }

    @Override
    public void run() {
        try {
            Registry registry = LocateRegistry.getRegistry(ChatConfig.HOST, ChatConfig.PORT);
            chatServer = (ChatServerInterface) registry.lookup(ChatConfig.ID);
            ChatClientForm frm = new ChatClientForm();

            frm.setVisible(true);
            frm.notificarHistorico("*****Bienvenido al chat room!*****\n", Color.DARK_GRAY);

            ChatListeners listener = new ChatListeners(frm.user, chatServer, frm);

            chatServer.addClient((ChatClientInterface) listener);

            frm.getTxtEnviar().addKeyListener(listener);
            frm.getBtnEnviar().addActionListener(listener);
            frm.getBtnLogout().addActionListener(listener);
            listener.cerrar();
        } catch (NotBoundException | RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

}
