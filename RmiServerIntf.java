/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerIntf extends Remote {
    public String getMessage() throws RemoteException;
    public String getMessage(String userQuery) throws RemoteException;
    public String addMessage(String[] userQuery) throws RemoteException;
    public String updateMessage(String[] userQuery)throws RemoteException;
    public String deleteMessage(String[] userQuery) throws RemoteException;
}
