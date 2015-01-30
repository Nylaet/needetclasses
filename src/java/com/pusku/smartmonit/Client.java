package com.pusku.smartmonit;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.*;
import java.util.Calendar;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Client implements Serializable {
    private final int id;
    //Фамилия Имя Отчество
    public ClientFIO cFIO;
    
    //Данные о подключении
    public ClientConnection cCon;
    //Географические данные
    public ClientGeo cGeo;
    //Финансовая информация
    public ClientFinance cFin;
    
    private String log="";

    public Client(){
    id=ClientDataBase.clientsCount++;
    }
    
    
    public synchronized void isConnect() throws IOException{
    Socket socket = new Socket ();
    try {
        socket.connect(new InetSocketAddress(cCon.getHost(), cCon.getPort()), 30);
        cCon.setConnect(true);
    }
    catch (SocketTimeoutException | ConnectException ex) {
        cCon.setConnect(false);
    }
    finally {
    socket.close();
    }
        
    }
    //Включение и отключение LAN0 интерфейса абонентской станции
    synchronized void offEth() throws JSchException, InterruptedException, MessagingException{
        if(cCon.isClientType()&&cCon.isLan0()){
            JSch jsch=new JSch();
            Session session=jsch.getSession("smarftsys", cCon.getHost().toString(), cCon.getPort());
            session.setPassword("smartsys");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand("ifconfig eth0 down");
            channel.connect();
            this.wait(3000);
            channel.disconnect();
            session.disconnect();
            cCon.setLan0(false);
            sendNotification(toString()+"Выключен порт");
            log+=toString()+"Выключен порт /r/n";
        }
    }
    //...
    synchronized void onEth() throws JSchException, InterruptedException, MessagingException{
        if(cCon.isClientType()&&!cCon.isLan0()){
            JSch jsch=new JSch();
            Session session=jsch.getSession("smarftsys", cCon.getHost().toString(), cCon.getPort());
            session.setPassword("smartsys");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand("ifconfig eth0 up");
            channel.connect();
            this.wait(3000);
            channel.disconnect();
            cCon.setLan0(true);
            sendNotification(toString()+"Включен порт");
            log+=toString()+"Включен порт"+"/r/n";
        }
    }
    //Перезагрузка станции абонента(базовой)
    synchronized void reboot() throws JSchException, InterruptedException, MessagingException{
        JSch jsch=new JSch();
        Session session=jsch.getSession("smarftsys", cCon.getHost().toString(), cCon.getPort());
        session.setPassword("smartsys");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        Channel channel=session.openChannel("exec");
        ((ChannelExec)channel).setCommand("reboot");
        channel.connect();
        this.wait(3000);
        channel.disconnect();
        sendNotification(toString()+"Перезагружена станция");
        log+=toString()+"Перезагружена станция"+"/r/n";
    }
    //Отправка служебного сообщения администраторам
    synchronized void sendNotification(String mess) throws MessagingException{
        String host="mx1.hostinger.com.ua";
        int port=2525;
        String[] to={"nylaet@gmail.com","admin@smartsys.com.ua","lomko@smartsys.com.ua"};
        String subject="Notification";
        String content;
        String from="admin";
        String user="admin@smartsys.esy.es",
               pass="156q456851";
        
        content=this.toString()+mess;
        for (String to1 : to) {
            Properties props=new Properties();
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.host",host);
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            javax.mail.Session ses=javax.mail.Session.getDefaultInstance(props,createAuth(user,pass));
            Message msg=new MimeMessage(ses);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to1));
            msg.setSubject(subject);
            msg.setText(content);
            Transport.send(msg);
        }
    }
    //Отправка сообщения пользователю
    void sendNotification(String mess,String to) throws MessagingException{
        String host="mx1.hostinger.com.ua";
        int port=2525;
        String subject="Notification";
        String content;
        String from="noreply";
        String user="noreply@smartsys.esy.es",
               pass="156q456851";
        
        content=toString()+mess;
        Properties props=new Properties();
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javax.mail.Session ses=javax.mail.Session.getDefaultInstance(props,createAuth(user,pass));
        
        Message msg=new MimeMessage(ses);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject(subject);
        msg.setText(content);
        Transport.send(msg);
    }
    //Проверка даты платежа
    void chekDate() throws MessagingException{
        if(!cCon.isClientType()||!cCon.isFreeze()||!cCon.isLan0())return;
        
        Calendar nowIs=Calendar.getInstance();
        if(nowIs.after(cFin.getNextPayDay())){
            cFin.setMoney(cFin.getMoney()-getTarifMoney(cFin.getTarifType()));
            sendNotification(toString()+"Снято со счета"+getTarifMoney(cFin.getTarifType())+"/r/n");
            log+=toString()+"Снято со счета"+getTarifMoney(cFin.getTarifType())+"/r/n";
        }
        Calendar alertDay=cFin.getNextPayDay();
        alertDay.set(Calendar.DAY_OF_MONTH, -5);
        if(nowIs.after(alertDay)&&cCon.isLan0()!=true&&((cFin.getMoney()-getTarifMoney(cFin.getTarifType()))<0)){
            String msg="Уважаемый абонент. Пополните Ваш счет.";//Текст поправить. Возможно собрать с pdfом
            //sendNotification(msg,cFIO.getEmail());
        }
    }
    //Проверка соостояния подключения детальное
    void getConnectStatus () throws JSchException, IOException {
        JSch jsch=new JSch();
        Session session=jsch.getSession("smarftsys", cCon.getHost().toString(), cCon.getPort());
        session.setPassword("smartsys");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        Channel channel=session.openChannel("exec");
        StringBuilder result=new StringBuilder();
        byte buffer[]=new byte[8192];
        InputStream s=channel.getInputStream();
        int size;
        ((ChannelExec)channel).setCommand("iwconfig");
        channel.connect(3000);
        do{
            size=s.read(buffer);
            if(size!=-1)
                result.append(new String(buffer,0,size));
        }while(size!=-1);
        //System.out.println(result.toString());
        parseData (result.toString()) ;
        session.disconnect();
        channel.disconnect();
    }
    //HTML код ссылки на станцию
    public String openStationSettings(){
        String s;
        s="<a href=\""+cCon.getHost().toString()+":"+cCon.getPort()+"\" target=_blank>Перейти</a>";
        return s;
    }
    @Override
    public String toString(){
        String x=cFIO.getLastName()+" "+cFIO.getFirstName()+"/r/n"+cGeo.getSite()+" "+cGeo.getStreet()+" "+cGeo.getHouse()+"/r/n ";
        return x;
    }
    
    private Authenticator createAuth(String user, String pass) {
        return new Authenticator(){
        @Override
        protected PasswordAuthentication getPasswordAuthentication(){
            return new PasswordAuthentication(user,pass);
        }
    };
    }
    
    private double getTarifMoney(int tarif){
    double x;
        switch(tarif){
        case 1: x=150;break;
        case 2: x=300;break;
        case 3: x=500;break;
        default:return(0);
    }
        return x;
    }
    
    private void parseData (String input) {
        //input=input.trim();
        cCon.setBitRate(Integer.parseInt(getTempString(input,"Bit Rate:"," Mb/s")));
        input=input.substring(input.indexOf("Bit Rate:")+"Bit Rate:".length());
        cCon.setTxPower(Integer.parseInt(getTempString (input,"Tx-Power="," dBm")));
        input=input.substring(input.indexOf ("Link Quality="));
        //System.out.println(input); 
        int linkQualityA=Integer.parseInt(getTempString (input,"Link Quality=","/"));
        int linkQualityB=Integer.parseInt(getTempString (input,"/","  Signal"));
        cCon.setLinkQuality((int)Math.round((double)linkQualityA/(double)linkQualityB*100));
        input=input.substring(input.indexOf(" Signal"));
        cCon.setSignalLevel(Integer.parseInt(getTempString (input,"level="," dBm")));
    }
    
    private String getTempString (String str1,String str2,String str3) {
        String temp=str1.substring(str1.indexOf(str2)+str2.length(),str1.indexOf(str3));
        //System.out.println(temp);
        return temp;
    }
    public String getLowForm(String color){
        String s;
        if(color.equals(""))color="339966";
        s="<form method=\"POST\" action=\"//*Перенаправление всегда на одну и ту же страницу*//\">\n" +
"	<p><button name=\""+id+"\" style=\"width:250;heidth:50;background-color: #"+color+"\">\n" +
"	<table border=\"0\" width=\"100%\">\n" +
"		<tr>\n" +
"			<td><span lang=\"ru\">"+cFIO.getLastName()+" "+cFIO.getFirstName()+" "+cFIO.getSurrName()+"</td>\n" +
"		</tr>\n" +
"		<tr>\n" +
"			<td><span lang=\"ru\">"+cGeo.getSite()+" "+cGeo.getStreet()+" "+cGeo.getHouse()+"</span></td>\n" +
"		</tr>\n" +
"               <tr>\n" +
"			<td><span lang=\"ru\">"+"т."+cGeo.getPhone()+"</span></td>\n" +
"		</tr>\n" +
"	</table>\n" +
"	</button></p>\n" +
"</form>";
        return s;
    }    
    
    public String getFullForm(){
        return null;
    }

    public int getId() {
        return id;
    }

    public String getLog() {
        return log;
    }

    public static class ClientFIO {
        private String firstName;
        private String lastName;
        private String surrName;
        private String email;
        
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getSurrName() {
            return surrName;
        }

        public void setSurrName(String surrName) {
            this.surrName = surrName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        
    }

    public static class ClientConnection {
        private InetAddress host;
        private int port;
        private String stationName;
        private String stationType;
        private boolean lan0;
        private boolean lan1;
        private boolean wlan;
        private boolean freeze;
        private boolean deleted=false;
        private int bitRate;
        private int txPower; 
        private int signalLevel;
        private int linkQuality;
        private boolean clientType;//true- Клиент false-Узел связи
        private boolean isConnect;

        public boolean isConnect() {
            return isConnect;
        }

        public void setConnect(boolean isConnect) {
            this.isConnect = isConnect;
        }
        public InetAddress getHost() {
            return host;
        }

        public void setHost(InetAddress host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getStationType() {
            return stationType;
        }

        public void setStationType(String stationType) {
            this.stationType = stationType;
        }

        public boolean isLan0() {
            return lan0;
        }

        public void setLan0(boolean lan0) {
            this.lan0 = lan0;
        }

        public boolean isLan1() {
            return lan1;
        }

        public void setLan1(boolean lan1) {
            this.lan1 = lan1;
        }

        public boolean isWlan() {
            return wlan;
        }

        public void setWlan(boolean wlan) {
            this.wlan = wlan;
        }

        public boolean isFreeze() {
            return freeze;
        }

        public void setFreeze(boolean freeze) {
            this.freeze = freeze;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public int getBitRate() {
            return bitRate;
        }

        public void setBitRate(int bitRate) {
            this.bitRate = bitRate;
        }

        public int getTxPower() {
            return txPower;
        }

        public void setTxPower(int txPower) {
            this.txPower = txPower;
        }

        public int getSignalLevel() {
            return signalLevel;
        }

        public void setSignalLevel(int signalLevel) {
            this.signalLevel = signalLevel;
        }

        public int getLinkQuality() {
            return linkQuality;
        }

        public void setLinkQuality(int linkQuality) {
            this.linkQuality = linkQuality;
        }

        public boolean isClientType() {
            return clientType;
        }

        public void setClientType(boolean clientType) {
            this.clientType = clientType;
        }
        
    }

    public static class ClientGeo {
        private String site;
        private String street;
        private String house;
        private String phone;

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getHouse() {
            return house;
        }

        public void setHouse(String house) {
            this.house = house;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
        
    }

    public static class ClientFinance {
        int tarifType;
        double money;
        Calendar lastPayDay;
        Calendar nextPayDay;
        boolean isUntcClient=false;

        public int getTarifType() {
            return tarifType;
        }

        public void setTarifType(int tarifType) {
            this.tarifType = tarifType;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public Calendar getLastPayDay() {
            return lastPayDay;
        }

        public void setLastPayDay(Calendar lastPayDay) {
            this.lastPayDay = lastPayDay;
        }

        public Calendar getNextPayDay() {
            return nextPayDay;
        }

        public void setNextPayDay(Calendar nextPayDay) {
            this.nextPayDay = nextPayDay;
        }

        public boolean isIsUntcClient() {
            return isUntcClient;
        }

        public void setIsUntcClient(boolean isUntcClient) {
            this.isUntcClient = isUntcClient;
        }
        
    }
}
    