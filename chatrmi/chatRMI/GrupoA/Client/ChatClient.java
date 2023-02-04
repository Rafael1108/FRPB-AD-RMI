package chatRMI.GrupoA.Client;

import chatRMI.GrupoA.Listeners.ChatListeners;
import chatRMI.GrupoA.Server.ChatConfig;
import chatRMI.GrupoA.Server.ChatServerInterface;
import java.awt.Color;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

            String nick_usuario = JOptionPane.showInputDialog(
                    null,
                    "NickName: ",
                    "Ingrese su NickName",
                    JOptionPane.INFORMATION_MESSAGE);
            if (nick_usuario != null && !nick_usuario.isEmpty()) {
                if (!chatServer.checkUsername(nick_usuario)) {
                    ChatClientForm frm = new ChatClientForm(nick_usuario);

                    frm.setVisible(true);
                    frm.notificarHistorico("*****Bienvenido al chat room!*****\n", Color.DARK_GRAY);

                    ChatListeners listener = new ChatListeners(nick_usuario, chatServer, frm);

                    chatServer.addClient((ChatClientInterface) listener);

                    frm.getTxtEnviar().addKeyListener(listener);
                    frm.getBtnEnviar().addActionListener(listener);
                    frm.getBtnLogout().addActionListener(listener);
                    listener.cerrar();
                } else {
                    JOptionPane.showMessageDialog(
                            new JFrame(),
                            "El usuario con ese NickName ya existe, prueba con otro.",
                            "Ya existe!!",
                            JOptionPane.WARNING_MESSAGE);

                    this.run();
                }

            } else {
                System.exit(0);
            }

        } catch (NotBoundException | RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

}
