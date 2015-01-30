package com.pusku.smartmonit;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientDataBase implements Serializable {
    private static ClientDataBase cdb;
    Timer timer=new Timer();
    public static int clientsCount;
    public ArrayList<Client> clients,clientsForView;
    Client client;
    public String outMessage;
    private ClientDataBase(){
        load () ;
        automat();
    }
    @Override
    protected void finalize(){
        timer.isInterrupt=false;
        
    }
    public ClientDataBase getClientDataBase(){
        if(cdb==null)cdb=new ClientDataBase();
        load();
        return(cdb);
    }
    
    private void automat(){
     Thread thread=new Thread(timer);
     thread.start();
    }
    
    private synchronized void load() {
        FileInputStream fin;
        ObjectInputStream ois;
        try {
            fin=new FileInputStream( "clientdb.lib");
            ois=new ObjectInputStream(fin);
            clients=(ArrayList<Client>)ois.readObject();
            ois.close();
            System.out.println ("Load is succesfully") ;
            clientsCount=clients.size();
        } catch (FileNotFoundException ex) {
            System.out.println ("Cant not found file") ;
        } catch (IOException ex) {
            System.out.println ("Cant not read from file") ;
        } catch (ClassNotFoundException ex) {
            System.out.println ("Wrong object") ;
        }
        
    }
    
    private synchronized void save () {
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos=new FileOutputStream("clientdb.lib");
            oos=new ObjectOutputStream(fos);
            oos.writeObject(clients);
            oos.flush();
            oos.close () ;
        } catch (FileNotFoundException ex) {
            System.out.println("Не могу сохранить файл userdb.lib");
        } catch (IOException ex) {
            System.out.println("Не могу сохранить в файл userdb.lib");
        }
    }
    
    public synchronized void addClient (String firstName, String lastName, String surrName,
                           String host, String email, int port, String stationName, 
                           String stationType, String site, String street, String house, 
                           String phone, int tarifType, double money, boolean clientType,boolean isUntcClient) throws UnknownHostException {
        Client newClient=new Client () ;
        newClient.cFIO.setFirstName(firstName);
        newClient.cFIO.setLastName(lastName);
        newClient.cFIO.setSurrName(surrName);
        newClient.cFIO.setEmail(email);
        newClient.cCon.setHost(InetAddress.getByName(host));
        newClient.cCon.setPort(port);
        newClient.cCon.setStationName(stationName);
        newClient.cCon.setStationType(stationType);
        newClient.cGeo.setSite(site);
        newClient.cGeo.setStreet (street);
        newClient.cGeo.setHouse(house);
        newClient.cGeo.setPhone(phone);
        newClient.cFin.setTarifType(tarifType);
        newClient.cFin.setMoney(money);
        newClient.cCon.setClientType(clientType);
        newClient.cFin.nextPayDay=Calendar.getInstance();
        newClient.cFin.nextPayDay.set(Calendar.MONTH,+1);
        newClient.cFin.setIsUntcClient(isUntcClient);
        clients.add(newClient);
        
        save () ;
    }
    
    public String setClient (int id,String firstName, String lastName, String surrName,
                           String host, String email, int port, String stationName, 
                           String stationType, String site, String street, String house, 
                           String phone, int tarifType, double money) throws UnknownHostException{
        int count=0;
        
        if(findClient(id))count++;
        if(findClient(firstName))count++;
        if(findClient(lastName))count++;
        if(findClient(surrName))count++;
        if(findClient(host))count++;
        if(findClient(email))count++;
        if(findClient(stationName))count++;
        if(findClient(stationType))count++;
        if(findClient(site))count++;
        if(findClient(street))count++;
        if(findClient(house))count++;
        if(findClient(phone))count++;
        if(findClient(money))count++;
        if(port!=0){
           for(Client locClient:clients){
                if(port!=locClient.cCon.getPort()){
                        if(notDublicate(locClient)){
                            clientsForView.add(locClient);//Если клиентов по критерию больше одного
                            count++;
                        }
                        else {
                            this.client=locClient;
                            count++;
                        } 
                    }
                } 
        }
        if(tarifType!=0){
           for(Client locClient:clients){
                if(tarifType!=locClient.cFin.getTarifType()){
                        if(notDublicate(locClient)){
                            clientsForView.add(locClient);//Если клиентов по критерию больше одного
                            count++;
                        }
                        else {
                            this.client=locClient;
                            count++;
                        } 
                    }
                } 
        }
        
        if(count==1){
            client.cFIO.setEmail(email);
            client.cFIO.setFirstName(firstName);
            client.cCon.setHost(InetAddress.getByName(host));
            client.cGeo.setHouse(house);
            client.cFIO.setLastName(lastName);
            client.cFin.setMoney(money);
            client.cGeo.setPhone(phone);
            client.cCon.setPort(port);
            client.cGeo.setSite(site);
            client.cCon.setStationName(stationName);
            client.cCon.setStationType(stationType);
            client.cGeo.setStreet(street);
            client.cFIO.setSurrName(surrName);
            client.cFin.setTarifType(tarifType);
            return(client.getFullForm());
        }
        if(count>1){
           return(createBackForm());
        }
    return("Error");
    
    }
    
    
    
    private boolean findClient(String param){
        if(param!=null){
            for (Client findClient : clients) {
                if(param.equals(findClient.cFIO.getFirstName())
                        ||param.equals(findClient.cFIO.getEmail())
                        ||param.equals(findClient.cGeo.getHouse())
                        ||param.equals(findClient.cFIO.getLastName())
                        ||param.equals(findClient.cGeo.getPhone())
                        ||param.equals(findClient.cGeo.getSite())
                        ||param.equals(findClient.cCon.getStationName())
                        ||param.equals(findClient.cCon.getStationType())
                        ||param.equals(findClient.cGeo.getStreet())
                        ||param.equals(findClient.cFIO.getSurrName())){
                    if(notDublicate(findClient)){
                        clientsForView.add(findClient);//Если клиентов по критерию больше одного
                        return true; 
                    }
                    else { 
                        this.client=findClient;
                        return true;
                    }
                }
            }
        }
    return false;
    }
    
    private boolean findClient(int param){
        
            for (Client findClient : clients) {
                if(param==findClient.getId()||
                        param==findClient.cCon.getPort()
                        ||param==findClient.cFin.getTarifType()){
                    if(notDublicate(findClient)){
                        clientsForView.add(findClient);//Если клиентов по критерию больше одного
                        return true; 
                    }
                    else { 
                        this.client=findClient;
                        return true;
                    }
                }
            }
        
    return false;
    }
    
    private boolean findClient(double param){
        
            for (Client findClient : clients) {
                if(param==findClient.cFin.getMoney()){
                    if(notDublicate(findClient)){
                        clientsForView.add(findClient);//Если клиентов по критерию больше одного
                        return true; 
                    }
                    else { 
                        this.client=findClient;
                        return true;
                    }
                }
            }
        
    return false;
    }
    
    
     
    /*
        Добавить:
            2. Удаление абонента
            3. Форма вывода данных о всех абонентах (хтмл)
            4. Поиск и присвоение свободного апишника  и порта
    */

    private boolean notDublicate(Client client) {
        for(Client have:clientsForView){
            if(have.getId()==client.getId())return(false);
        }
    return true;
    }

    private String createBackForm() {
        String s="";
        
        return s;
    }
    
    private class Timer implements Runnable{
        public boolean isInterrupt=false;
        @Override
        public void run() {
            while(!isInterrupt){
                try {
                    wait(300000);
                    save();
                    for (Client client1 : clients) {
                        client1.isConnect();
                    }
                } catch (InterruptedException ex) {
                    save();
                } catch (IOException ex) {
                    System.out.println("хз... Бывает");
                }
            }
        }

        

        
        
    }
    
}

