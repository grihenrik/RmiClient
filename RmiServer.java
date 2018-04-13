
package rmiserver;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class RmiServer extends UnicastRemoteObject implements RmiServerIntf {
    public static final String MESSAGE = "Hello World";
    
    protected Connection conn = null;

    public RmiServer() throws RemoteException {
        super(0);    // required to avoid the 'rmic' step, see below
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Got mysql driver");
            conn = DriverManager.getConnection("jdbc:mysql://niisku.lamk.fi:3306/user_henrik18001","henrik18001", "Koodaus1");
        }
        catch (Exception ex)
        {
            System.out.println("Error: "+ex.getMessage());
        }
    }
    
public void finalize()
{
    if (conn != null)
        try
        {
            conn.close();            
        }
    catch (Exception ex)
    {
        System.out.println("Error: "+ex.getMessage());
    }
}
public String getMessage() {
        String response = "";
        String query = "select * from ds_phonenumbers";
        Statement st = null;

        System.out.println("Got a message request without parameters");

        try
        {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next())
            {
                response += rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4)+rs.getString(5)+rs.getString(6)+"\r\n";
            }
        }
        catch (Exception ex)
        {
            response = ex.getMessage();
            System.out.println("Error: "+ex.getMessage());
        }


        return response;
//        return MESSAGE;
}
    public String getMessage(String userQuery) {
        String response = "";
        String query = "select * from ds_phonenumbers";
        Statement st = null;
        
        System.out.println("Got a message request");
  
        try
        {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next())
            {
                response = rs.getString(1);
            }
        }
        catch (Exception ex)
        {
            response = ex.getMessage();
            System.out.println("Error: "+ex.getMessage());
        }
        
        
        return response;
//        return MESSAGE;
    }
    public String addMessage(String[] userQuery) {
        String response = "";
        String query = "insert into ds_phonenumbers (lastname,firstname,address,phonenumber,other) values(?,?,?,?,?)";
        PreparedStatement pst = null;
        
        System.out.println("Got a add message request");
        //System.out.println(query);
        try
        {
            pst = conn.prepareStatement(query);
            for (int i = 0; i < userQuery.length; i++) {
                pst.setString(i+1,userQuery[i]);
            }
            System.out.println(pst);
            if (pst.executeUpdate()==1){
                response = getMessage();
            }
                
        }
        catch (Exception ex)
        {
            response = ex.getMessage();
            System.out.println("Error: "+ex.getMessage());
        }
        
        
        return response;
//        return MESSAGE;
    }
    public String updateMessage(String[] userQuery) {
        /// Needs seven parameters
        String response = "";
        String query = "update ds_phonenumbers set lastname = ?,firstname=?,address=?,phonenumber=?,other=? where lastname=? and firstname=?";
        PreparedStatement pst = null;
        
        System.out.println("Got a update message request");
  
        try
        {
            pst = conn.prepareStatement(query);
            for (int i = 0; i < userQuery.length; i++) {
                pst.setString(i+1,userQuery[i]);
            }
            System.out.println(pst);
            if (pst.executeUpdate()==1){
                response = getMessage();
            }
            
        }
        catch (Exception ex)
        {
            response = ex.getMessage();
            System.out.println("Error: "+ex.getMessage());
        }
        
        
        return response;
//        return MESSAGE;
    }
    public String deleteMessage(String[] userQuery) {
        String response = "";
        String query = "delete from ds_phonenumbers where lastname=? and firstname=?";
        PreparedStatement pst = null;
        
        System.out.println("Got a delete message request");
  
        try
        {
            pst = conn.prepareStatement(query);
            for (int i = 0; i < userQuery.length; i++) {
                pst.setString(i+1,userQuery[i]);
            }
            System.out.println(pst);
            if (pst.executeUpdate()==1){
                response = getMessage();
            }
        }
        catch (Exception ex)
        {
            response = ex.getMessage();
            System.out.println("Error: "+ex.getMessage());
        }
        
        
        return response;
//        return MESSAGE;
    }

    public static void main(String args[]) throws Exception {
        System.out.println("RMI server started");

        try { //special exception handler for registry creation
            LocateRegistry.createRegistry(1099); 
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }
           
        //Instantiate RmiServer

        RmiServer obj = new RmiServer();

        // Bind this object instance to the name "RmiServer"
        Naming.rebind("//localhost/RmiServer", obj);
        System.out.println("PeerServer bound in registry");
    }
}
