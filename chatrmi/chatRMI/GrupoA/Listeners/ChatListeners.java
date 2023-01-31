package chatRMI.GrupoA.Listeners;

import chatRMI.GrupoA.Client.ChatClientForm;
import chatRMI.GrupoA.Client.ChatClientInterface;
import chatRMI.GrupoA.Client.Chatter;
import chatRMI.GrupoA.Server.ChatServerInterface;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent; 
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask; 
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Edgar Basurto
 */
public class ChatListeners extends UnicastRemoteObject implements  ActionListener, KeyListener, ChatClientInterface {
    
    private List<String> lstClientes;
    private final ChatServerInterface server;
    // private final String name;
    private final Chatter user;
    private final ChatClientForm form;

    /**
     * CONSTRUCTOR QUE RECIBE COMO PARAMETROS
     *
     * @param _name
     * @param _server
     * @param _form OBJETO DE TIPO ChatClientForm QUE HACE REFERENCIA AL
     * FORMULARIO QUE ESTA EJECUTANDOSE
     */
    public ChatListeners(String _name, ChatServerInterface _server, ChatClientForm _form) throws RemoteException {
        this.form = _form;
        this.user = new Chatter(_name);
        this.server = _server;
        this.lstClientes = new ArrayList();
        
        
        //Timer para verificar conexiones
        Timer minuteur = new Timer();
        TimerTask tache = new TimerTask() {
            @Override
            public void run() {
                try {
                    
                    List<String> lstTmp = server.getListClientByName(user.getName());                    
                    for (String client : lstClientes) {
                        if (!client.equals(user.getName())) {
                            lstClientes.add(lstTmp.get(0));
                        }
                    }
                } catch (RemoteException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        };
        minuteur.schedule(tache, 0, 20000);
        
       
    }

    /**
     * SOBREESCRITURA DEL MEDOTO ACTIONPERFORMED DONDE SE INDICAN LAS ACCIONES
     * AL MOMENTO DE DAR CLICK EN LOS BOTONES ENVIAR, BORRAR, LOGOUT
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(form.getBtnEnviar())) {
            EnviarMensaje();
            LimpiarTxt();
            return;
        }
        if (e.getSource().equals(form.getBtnLogout())) {
            try {
                this.closeChat("Estas saliendo");
                form.dispose();
            } catch (RemoteException e1) {
                System.out.println(e1.getMessage());
            }
            return;
        }
    }

    /**
     * Método para confirmar el cierre del JFrame
     */
    public void cerrar() {
        try {
            form.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            form.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    confirmarSalida();
                }
            });
            form.setVisible(true);
        } catch (Exception e) {
        }
    }

    /**
     * Confirmar salida
     */
    public void confirmarSalida() {
        int valor = JOptionPane.showConfirmDialog(
                form,
                "¿Está seguro de cerrar la aplicación y salir del chat?",
                "Advertencia",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (valor == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(
                    form,
                    "Gracias por su visita, Hasta Pronto",
                    "Gracias",
                    JOptionPane.INFORMATION_MESSAGE);
            try {
                server.removeClient(user.getName());
                form.setVisible(false);
                form.dispose();
                System.exit(0);
            } catch (RemoteException e1) {
                System.out.println(e1.getMessage());
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * SOBREESCRITURA DEL MEDOTO keyPressed DONDE SE INDICAN LAS ACCIONES AL
     * MOMENTO DE PRESIONAR LA TECLA ENTER
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            EnviarMensaje();
        }
    }

    /**
     * SOBREESCRITURA DEL MEDOTO keyReleased DONDE SE INDICAN LAS ACCIONES AL
     * MOMENTO DE LEVANTAR LA TECLA ENTER LUEGO DE PRESIONARLA
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            LimpiarTxt();
        }
    }

    /**
     * MÉTODO QUE RECOGE LOS DATOS DEL FORMULARIO Y LOS ENVIA AL SERVIDOR
     */
    private void EnviarMensaje() {
        try {
            String txtChat = form.getTxtEnviar().getText();
            if (!txtChat.isEmpty()) {
                this.sendMessage(lstClientes, txtChat);
            }
            
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }

    /**
     * MÉTODO QUE LIMPIA EL TEXTO DEL FORMULARIO
     */
    private void LimpiarTxt() {
        form.getTxtEnviar().setText("");
    }
    
    @Override
    public void retrieveMessage(String message) throws RemoteException {
        String response = message;
        String separador = Pattern.quote("|");
        String[] resp = response.split(separador);
        if (resp.length == 2) {
            form.notificarHistorico(resp[0], Color.decode(resp[1]));
        } else {
            form.notificarHistorico(response, Color.DARK_GRAY);
        }        
    }
    
    @Override
    public void sendMessage(List<String> list, String mssg) {
        try {
            server.broadcastMssg(user.getName() + " : " + mssg + "|" + user.getColor(), list);
        } catch (RemoteException ex) {
            System.out.println("Error Client " + user.getName() + " Broadcast: " + ex.getMessage());
        }
    }
    
    @Override
    public String getClientName() {
        return user.getName();
    }
    
    @Override
    public void closeChat(String message) throws RemoteException {
        form.getTxtEnviar().setEnabled(false);
        JOptionPane.showMessageDialog(new JFrame(), message, "Alert", JOptionPane.WARNING_MESSAGE);
    }
    
    @Override
    public void openChat() throws RemoteException {
        form.getTxtEnviar().setEnabled(true);
    }
    
}
