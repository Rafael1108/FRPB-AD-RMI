package chatRMI.GrupoA.Server;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * Un servidor que ejecuta el ChatService.
 *
 */
public class ChatServer implements Runnable {

    private final ChatService chatService;
    private ChatServerForm frm;
    
    public ChatServer(ChatServerForm _form) throws RemoteException, AlreadyBoundException {
        frm=_form;
        chatService = new ChatService();
//        LocateRegistry.createRegistry(ChatConfig.PORT);
//        Naming.rebind(ChatConfig.ID, chatService);
        Registry registry = LocateRegistry.createRegistry(ChatConfig.PORT);
        registry.bind(ChatConfig.ID, chatService);
        frm.notificarBitacora("Servidor Listo ...");
    }

    public static void main(String[] args) throws AlreadyBoundException  {
        ChatServerForm formulario=new ChatServerForm();
        try {            
            formulario.setVisible(true);
            ChatServer chatS= new ChatServer(formulario);         
            chatS.run();
                        
        } catch ( RemoteException ex) {
            formulario.notificarBitacora("Error main Server: " + ex.getMessage());
        }
    }

    @Override
    public void run() {
       while(true){
           try {
               ArrayList<String> log = chatService.getMessageLog();
               for(int j=0;j< log.size();j++){
                   try
                   {
                       frm.notificarBitacora(log.get(j));
                       chatService.removeMessage(j);
                   } catch (RemoteException ex) {
                       frm.notificarBitacora("Error Notificar Log: " + ex.getMessage());
                   }
               }  } catch (RemoteException ex) {
               //Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
           }
       }        
    }
}
