/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package chatRMI.GrupoA.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interfase para implementar el servicio de Server
 */
public interface ChatClientInterface extends Remote {

    /**
     * Activa la session y permite que un cliente envíe un mensaje
     *
     * @throws java.rmi.RemoteException
     */
    void openChat() throws RemoteException;

    /**
     * Cierra la session y no permite que un cliente pueda enviar un mensaje.
     *
     * @param mssg
     * @throws java.rmi.RemoteException
     */
    void closeChat(String mssg) throws RemoteException;

    /**
     * Recupera mensajes del server .
     *
     * @param mssg mensaje
     * @throws java.rmi.RemoteException
     */
    void retrieveMessage(String mssg) throws RemoteException;

    
    /**
     * Envía mensajes del cliente a el server.
     *      
     * @param lists
     * @param mssg
     * @throws java.rmi.RemoteException
     */
    void sendMessage(List<String> lists, String mssg) throws RemoteException;

    /*
    * Obtiene el nombre del cliente 
    */
    String getClientName() throws RemoteException;

    /**
     * Actualiza la lista de usuarios conectados
     * @param contacts
     * @throws java.rmi.RemoteException
     */
    void updateContactList(List<String> contacts) throws RemoteException;
}
