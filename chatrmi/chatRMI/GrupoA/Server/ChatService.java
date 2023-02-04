package chatRMI.GrupoA.Server;

import chatRMI.GrupoA.Client.ChatClientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Ejecuta los comandos de chat RMI.
 */
public class ChatService extends UnicastRemoteObject implements ChatServerInterface {

    /**
     * Construye un objeto del servicio para chat RMI.
     *
     * @throws java.rmi.RemoteException
     */
    public ChatService() throws RemoteException {
        super();
        this.clientes = new ArrayList<>();
        this.blockedClientes = new ArrayList<>();
        this.mssgLog = new ArrayList<>();
    }

     /**
     * Difunde el mensaje a todos los clientes conectados del chat;
     * @param mssg mensaje a difudir
     * @param list Lista de clientes que deben recibir el mensaje
     * @throws RemoteException 
     */
    @Override
    public synchronized void broadcastMssg(String mssg, List<String> list) throws RemoteException {
        if (list.isEmpty()) {
            int i = 0;
            while (i < clientes.size()) {
                clientes.get(i++).retrieveMessage(mssg); 
            }
        } else {
            for (ChatClientInterface client : clientes) {
                for (int i = 0; i < list.size(); i++) {
                    if (client.getClientName().equals(list.get(i))) {
                        client.retrieveMessage(mssg); 
                    }
                }
            }
        }
        
        //REGISTRAR LOG DE MENSAJES PARA SERVIDOR
        this.mssgLog.add(dtf.format(LocalDateTime.now()) +"=> "+mssg);
    }

    /**
     *
     * @param client
     * @throws RemoteException
     */
    @Override
    public synchronized void addClient(ChatClientInterface client) throws RemoteException {
        this.clientes.add(client);
        this.mssgLog.add("INF.: ***"+ client.getClientName() + "*** ha ingresado a la sala.");
    }
    
    /**
     * Recupera el nombre de los clientes conectados en el chat.     *
     *
     * @param userName Nombre del usuario.
     * @return una lista de los clientes conectados.
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized List<String> getListClientByName(String userName) throws RemoteException {
        List<String> list =  new ArrayList<>();
        for (ChatClientInterface client : clientes) {
            if(!client.getClientName().equals(userName)){
                list.add(client.getClientName());
            }
        }
        return list;
    }

    /**
     * Impide que un cliente envíe mensajes pero este recibe si puede recibir
     * mensajes "block".
     *
     * @param clients lista de los clientes bloqueados.
     * @throws RemoteException
     */
     @Override
    public synchronized void blockClient(List<String> clients)throws RemoteException{
        for(int j=0;j<this.clientes.size();j++){
            for(int i=0;i<clientes.size();i++){
                try {
                    if(this.clientes.get(j).getClientName().equals(clients.get(i))){
                        this.clientes.get(j).closeChat("ALERTA.: ***"+ clients + "*** Fuiste bloqueado por el Administrador");
                        blockedClientes.add(this.clientes.get(j));
                    }
                } catch (RemoteException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        }
    }
    
    /**
     * Elimina los clientes de la lista del chat (Log-out).
     *
     * @param clients lista de los clientes a elminar.
     * @throws RemoteException
     */
     @Override
    public synchronized void removeClient(List<String> clients)throws RemoteException{
        for(int j=0;j<this.clientes.size();j++){
            for(int i=0;i<clients.size();i++){
                try {
                    if(this.clientes.get(j).getClientName().equals(clients.get(i))){
                        this.clientes.get(j).closeChat( "ALERTA!!=> ***"+ clients.get(i) + "*** fue eliminado por el Administrador");
                        this.clientes.remove(j);
                            //REGISTRAR LOG DE MENSAJES PARA SERVIDOR
                            this.mssgLog.add(dtf.format(LocalDateTime.now()) +" .: "+ "ALERTA=> ***"+ clients.get(i) + "*** acaba de salir de la sala");
                    }
                } catch (RemoteException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        }
    }
    
    
    /**
     * Activa al cliente de la lista de bloqueo.
     * @param clients lista de los clientes a desbloquear.
     * @throws RemoteException 
     */ 
    @Override
    public synchronized void activeClient(List<String> clients) throws RemoteException {
        for(int j=0;j<this.blockedClientes.size();j++){
            for(int i=0;i<clients.size();i++){
                try {
                    if(this.blockedClientes.get(j).getClientName().equals(clients.get(i))){
                        this.blockedClientes.get(j).openChat();
                        this.blockedClientes.remove(j);
                    }
                } catch (RemoteException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        }
    }
   
    
    /**
     * Verifica si el nombre de usuario ya existe en el servidor o no, El nombre
     * de usuario es el id para el chat.
     *
     * @param userName Nombre del usuario.
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public boolean checkUsername(String userName) throws RemoteException {
        boolean exist = false;
        for(int i=0;i<clientes.size();i++){
            if(clientes.get(i).getClientName().equals(userName)){
                exist = true;
            }
        }
        for(int i=0;i<blockedClientes.size();i++){
            if(blockedClientes.get(i).getClientName().equals(userName)){
                exist = true;
            }
        }
        return exist;
    }
    
    /**
     * Elimina el cliente de la lista del chat (Log-out).
     *
     * @param clients cliente a elminar.
     * @throws RemoteException
     */
     @Override
    public synchronized void removeClient(String clients)throws RemoteException{
        for(int j=0;j<this.clientes.size();j++){
            try {
                if(this.clientes.get(j).getClientName().equals(clients)){
                    this.clientes.remove(j);
                }
            } catch (RemoteException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
    
    
    public synchronized void removeMessage(int j)throws RemoteException{        
        this.mssgLog.remove(j);        
    }
    
    public synchronized ArrayList<String> getMessageLog()throws RemoteException{        
        return mssgLog;        
    }
    
    private final ArrayList<String> mssgLog;

    // Lista de clientes acctivos
    private final ArrayList<ChatClientInterface> clientes;

    // Lista de Clientes bloequeados
    private final ArrayList<ChatClientInterface> blockedClientes;

    //formato de fecha a mostrar
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
}
