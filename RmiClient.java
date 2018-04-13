
package rmiclient;

import rmiserver.RmiServerIntf;
import java.rmi.Naming;
import java.util.Scanner;


public class RmiClient { 
    public static void main(String args[]) throws Exception {
        
        System.out.println("Get message from server...");
        RmiServerIntf obj = (RmiServerIntf)Naming.lookup("//localhost/RmiServer");
        String[] quest= {"lastname","firstname","address","phonenumber","other"};
        String[] upd=new String[7];
        String[] addNew=new String[5];
        String[] dlt=new String[2];
        String cmd,inp;
        int i;
        boolean quit=false;
        Scanner s = new Scanner(System.in);
        System.out.println(obj.getMessage()); 
        while (!quit){
            System.out.println("Use help for help");
            System.out.print("Command> ");
            cmd = s.nextLine();
            if (cmd.equals("add")){
                System.out.println("What do you want to add?");
                i=0;
                for(String item: quest){
                    inp="";
                    System.out.format("Give %s: ",item);
                    inp = s.nextLine();
                    addNew[i] = inp;
                    System.out.println("Input: "+addNew[i]);
                    i++;
                }
                System.out.println(addNew.length+" : "+addNew[0]);
                System.out.println(obj.addMessage(addNew));
            } else
            if (cmd.equals("update")){
                System.out.println("What do you want to update?"); 
                for(i=0;i<quest.length;i++){
                    System.out.println("Give "+quest[i]);
                    upd[i] = s.nextLine();
                }
                upd[5]=upd[0];
                upd[6]=upd[1];
                System.out.println(obj.updateMessage(upd));
            } else
                if(cmd.equals("delete")){
                    
                    System.out.println("What do you want to update?"); 
                    for(i=0;i<2;i++){
                        System.out.println("Give "+quest[i]);
                        dlt[i] = s.nextLine();
                    }
                    System.out.println(obj.deleteMessage(dlt));
            } else
                if(cmd.equals("search")){
                    System.out.println("What do you want to search for?");
                    System.out.println("Press enter for all");
                    inp = s.nextLine();
                    String[] newString = inp.split("[,]");
                    if(!newString[0].equals("")){
                        System.out.println(obj.getMessage(newString[0]));
                    } else {System.out.println(obj.getMessage());}
                    
            } else
                if(cmd.equals("quit")){
                    quit=true;
                }else
                if(cmd.equals("help")){
                    System.out.println("RMI Client 1.0");
                    System.out.println("With this client you can add, update,\ndelete, and search from the database");
                    System.out.println("just use [add,update,delete,search,quit,help]\nas a command.");
                }
            
            } 
        System.out.println("Bye");

    }
}
