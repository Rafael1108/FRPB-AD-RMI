package chatRMI.GrupoA.Server;

import chatRMI.GrupoA.Client.ChatClientInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/*
* Interfase para implementar el servicio de Server
*/
public interface ChatServerInterface extends Remote {

    /**
     * Verifica si el nombre de usuario ya existe en el servidor o no, El nombre
     * de usuario es el id para el chat.
     *
     * @param userName Nombre del usuario.
     * @return
     * @throws java.rmi.RemoteException
     */
    boolean checkUsername(String userName) throws RemoteException;

    /**
     * Recupera el nombre de los clientes conectados en el chat.     *
     *
     * @param userName Nombre del usuario.
     * @return una lista de los clientes conectados.
     * @throws java.rmi.RemoteException
     */
    List<String> getListClientByName(String userName) throws RemoteException;

    /**
     * Agrega un cliente conectado a la lista de clientes conectado "On-Line".
     *
     * @param client instancia del cliente que se desea agregar.
     * @throws java.rmi.RemoteException
     */
    void addClient(ChatClientInterface client) throws RemoteException;

    /**
     * Impide que un cliente envíe mensajes pero este recibe si puede recibir
     * mensajes "block".
     *
     * @param clients lista de los clientes bloqueados.
     * @throws RemoteException
     */
    void blockClient(List<String> clients) throws RemoteException;

    /**
     * Elimina los clientes de la lista del chat (Log-out).
     *
     * @param clients lista de los clientes a elminar.
     * @throws RemoteException
     */
    void removeClient(List<String> clients) throws RemoteException;
 
    /**
     * Elimina el cliente de la lista del chat (Log-out).
     *
     * @param clients cliente a elminar.
     * @throws RemoteException
     */
    void removeClient(String clients) throws RemoteException;

    /**
     * Activa al cliente de la lista de bloqueo.
     * @param clients lista de los clientes a desbloquear.
     * @throws RemoteException 
     */ 
    void activeClient(List<String> clients) throws RemoteException;

    /**
     * Difunde el mensaje a todos los clientes conectados del chat;
     * @param mssg mensaje a difudir
     * @param list Lista de clientes que deben recibir el mensaje
     * @throws RemoteException 
     */
    //
    void broadcastMssg(String mssg, List<String> list) throws RemoteException;
}
