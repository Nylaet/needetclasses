/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pusku.smartmonit;

import java.io.*;
import java.util.*;


/**
 *
 * @author Panker_Work
 */
public class UserDB implements Serializable{
    ArrayList<Users> users=new ArrayList<Users>();
    Users userNow;
    
    public UserDB() throws IOException{
        if(!load()){
            Users admin=new Users();
            admin.name="panker";
            admin.password="156456851";
            admin.group="admin";
            admin.lastvisit=Calendar.getInstance();
            users.add(admin);
            save();
        }
    }

    private boolean load() {
        FileInputStream fin;
        ObjectInputStream ois;
        try {
            fin=new FileInputStream( "userdb.lib");
            ois=new ObjectInputStream(fin);
            users=(ArrayList<Users>)ois.readObject();
            ois.close();
            System.out.println ("Load is succesfully") ;
        } catch (FileNotFoundException ex) {
            System.out.println ("Cant not found file") ;
            return(false);
        } catch (IOException ex) {
            System.out.println ("Cant not read from file") ;
            return(false);
        } catch (ClassNotFoundException ex) {
            System.out.println ("Wrong object") ;
             return(false);
        } 
        return(true);
    }

    private void save()  {
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos=new FileOutputStream("userdb.lib");
            oos=new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.flush();
            oos.close () ;
        } catch (FileNotFoundException ex) {
            System.out.println("Не могу сохранить файл userdb.lib");
        } catch (IOException ex) {
            System.out.println("Не могу сохранить в файл userdb.lib");
        }
           
    }

    public boolean checkUser(String name,String password) throws IOException{
        for (Users user : users) {
            if(user.name.equalsIgnoreCase(name)){
                if(user.password.equals(password)){
                    user.lastvisit=Calendar.getInstance();
                    save();
                    userNow=user;
                    return(true);
                }
            }
        }
        return(false);
    }
    
    public boolean addNewUser(String name,String password,String group) throws IOException{
        if(userNow.group.equals("admin")){
            Users user=new Users();
            user.name=name;
            user.password=password;
            user.group=group;
            users.add(user);
            save();
        }
        return(false);
    }
}

class Users  implements Serializable{
    String name,password,group;
    Calendar lastvisit;
}
