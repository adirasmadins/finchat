import javafx.application.Platform;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client{

    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    private ObjectInputStream csInput;
    private ObjectOutputStream csOutput;
    private Socket socket;
    private Socket csocket;
    private String server;
    private int port;
    private int cport;
    private User current;
    //private Clientchatlistener ccl;
    private ClientGUI cgui;
    //ArrayList<Session> sessionsapartof = new ArrayList<>();
    private Session session;

    public ClientGUI getCgui() {
        return cgui;
    }

    public void setCgui(ClientGUI cgui) {
        this.cgui = cgui;
    }

    public ObjectInputStream getsInput() {
        return sInput;
    }

    public void setsInput(ObjectInputStream sInput) {
        this.sInput = sInput;
    }

    public ObjectOutputStream getsOutput() {
        return sOutput;
    }

    public void setsOutput(ObjectOutputStream sOutput) {
        this.sOutput = sOutput;
    }

    public ObjectInputStream getCsInput() {
        return csInput;
    }

    public void setCsInput(ObjectInputStream csInput) {
        this.csInput = csInput;
    }

    public ObjectOutputStream getCsOutput() {
        return csOutput;
    }

    public void setCsOutput(ObjectOutputStream csOutput) {
        this.csOutput = csOutput;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getCsocket() {
        return csocket;
    }

    public void setCsocket(Socket csocket) {
        this.csocket = csocket;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getCport() {
        return cport;
    }

    public void setCport(int cport) {
        this.cport = cport;
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

   /* public Clientchatlistener getCcl() {
        return ccl;
    }

    public void setCcl(Clientchatlistener ccl) {
        this.ccl = ccl;
    }*/

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    Client(){

        int portNumber = 1500;
        int cportNumber = 1501;
        String serverAddress = "localhost";

        this.server = serverAddress;
        this.port = portNumber;
        this.cport = cportNumber;

        try {
            socket = new Socket(server, port);
            csocket = new Socket(server, cport);

            System.out.println("Connected to server " + socket.getInetAddress() + ":" + socket.getPort());
            System.out.println("Connected to chat server " + csocket.getInetAddress() + ":" + csocket.getPort());
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Created output/input streams with server");
            csInput = new ObjectInputStream(csocket.getInputStream());
            csOutput = new ObjectOutputStream(csocket.getOutputStream());
            System.out.println("Created chat output/input streams with server\n");
            //ccl = new Clientchatlistener();
            //ccl.start();
        } catch (UnknownHostException e) {
            System.out.println("Exception trying to identify host: " + e);
        } catch (IOException e2) {
            System.out.println("Exception connecting to host: " + e2);
        }

    }

    public void login(String username, String password) {
        try {

            String up;
            up = username + " " + password;

            sOutput.writeObject(new Communication(Communication.LOGIN, up));

            current = (User) sInput.readObject();

        } catch (IOException e) {
                System.out.println("Exception sending input credentials: " + e);
        } catch (ClassNotFoundException e2) {
                System.out.println("Exception with object conversion of credential confirmation: " + e2);
        }

    }

    public void logout(){
        try {
            sOutput.writeObject(new Communication(Communication.LOGOUT));
            current = null;
        } catch (IOException e) {
            System.out.println("Exception sending logoff request to: " + e);
        }
    }

    public ArrayList<Transaction> getbought(){
        ArrayList<Transaction> bought_items = new ArrayList<>();
        try {
            sOutput.writeObject(new Communication(Communication.VIEWBOUGHT));
            bought_items = (ArrayList<Transaction>) sInput.readObject();

        } catch (IOException e) {
            System.out.println("Exception sending get bought items request: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion of bought items: " + e2);
        }

        return bought_items;
    }

    public ArrayList<Transaction> getsold(){
        ArrayList<Transaction> sold_items = new ArrayList<>();
        try {
            sOutput.writeObject(new Communication(Communication.VIEWSOLD));
            sold_items = (ArrayList<Transaction>) sInput.readObject();
        } catch (IOException e) {
            System.out.println("Exception sending get bought items request: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion of bought items: " + e2);
        }

        return sold_items;
    }

    public boolean addcredit(Double amount){
        boolean creditadded = false;
        String stramount = Double.toString(amount);
        try {
            sOutput.writeObject(new Communication(Communication.ADDCREDIT, stramount));
            creditadded = (boolean) sInput.readObject();
        } catch (IOException e) {
            System.out.println("Exception sending add ccredit request: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion of add credit confirmation: " + e2);
        }

        return creditadded;
    }

    public boolean additem(Item item){
        boolean itemadded = false;

        try {
            sOutput.writeObject(new Communication(Communication.ADDITEM, item));
            itemadded = (boolean) sInput.readObject();

        } catch (IOException e) {
            System.out.println("Exception sending add item request: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion of add item confirmation: " + e2);
        }

        return itemadded;
    }

    public ArrayList<Item> getsellitems(){

        ArrayList<Item> sellingitems = new ArrayList<>();

        try {

            sOutput.writeObject(new Communication(Communication.RETURNSELLINGITEMS));

            sellingitems = (ArrayList<Item>) sInput.readObject();

        } catch (IOException e) {
            System.out.println("Exception sending get seller items request: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion of seller's item: " + e2);
        }

        return sellingitems;

    }

    public boolean deleteitem(int itemid){
        boolean itemdeleted = false;

        try {
            sOutput.writeObject(new Communication(Communication.DELETESELLITEM, Integer.toString(itemid)));
            itemdeleted = (boolean) sInput.readObject();
        } catch (IOException e) {
            System.out.println("Exception sending delete item request: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion of delete response: " + e2);
        }

        return itemdeleted;
    }

    public ArrayList<String> getcurrent(){
        ArrayList<String> info = new ArrayList<>();
        try {
            sOutput.writeObject(new Communication(Communication.RETURNUSERINFO));
            info = (ArrayList<String>) sInput.readObject();


        } catch (IOException e) {
            System.out.println("Exception sending get user info request: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion of user info: " + e2);
        }

        return info;
    }

    public boolean updatefn(String fn){
        boolean updated = false;
        try {
            sOutput.writeObject(new Communication(Communication.EDITFN, fn));
            updated = (boolean) sInput.readObject();

        } catch (IOException e) {
            System.out.println("Exception sending update fn request: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion update fn confirmation: " + e2);
        }

        return updated;
    }

    public boolean updateln(String ln){
        boolean updated = false;
        try {
            sOutput.writeObject(new Communication(Communication.EDITLN, ln));
            updated = (boolean) sInput.readObject();

        } catch (IOException e) {
            System.out.println("Exception sending update ln request: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion update ln confirmation: " + e2);
        }

        return updated;

    }

    public boolean updatepw(String pw){
        boolean updated = false;
        try {
            sOutput.writeObject(new Communication(Communication.EDITPW, pw));
            updated = (boolean) sInput.readObject();

        } catch (IOException e) {
            System.out.println("Exception sending update pw request: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion update pw confirmation: " + e2);
        }

        return updated;

    }

    public boolean updateaddress(Address address){

        boolean updated = false;
        try {
            sOutput.writeObject(new Communication(Communication.EDITADDRESS, address));
            updated = (boolean) sInput.readObject();

        } catch (IOException e) {
            System.out.println("Exception sending update address request: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion update address confirmation: " + e2);
        }

        return updated;

    }

    public boolean updatesellitems(Item item){

        boolean updated = false;
        try {
            sOutput.writeObject(new Communication(Communication.EDITSELLITEMS, item));
            updated = (boolean) sInput.readObject();

        } catch (IOException e) {
            System.out.println("Exception sending update item request: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion update item confirmation: " + e2);
        }

        return updated;

    }

    public Item getitem(Item item){
        ArrayList<Item> sellingitems = new ArrayList<>();
        Item temp = null;
        try {

            sOutput.writeObject(new Communication(Communication.RETURNSELLINGITEMS));

            sellingitems = (ArrayList<Item>) sInput.readObject();

            for(int i = 0; i<sellingitems.size(); i++){
                if(item.getItem_code() == sellingitems.get(i).getItem_code()){
                    temp = sellingitems.get(i);
                }
            }

        } catch (IOException e) {
            System.out.println("Exception sending get seller items request: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion of seller's item: " + e2);
        }

        return temp;

    }

    public ArrayList<Item> returnbuyitems(){
        ArrayList<Item> buyitems = new ArrayList<>();

        try {

            sOutput.writeObject(new Communication(Communication.RETURNITEMS));

            buyitems = (ArrayList<Item>) sInput.readObject();

        } catch (IOException e) {
            System.out.println("Exception sending get items to buy request: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion of items to buy: " + e2);
        }

        return buyitems;
    }

    public Double getusercredit(){
        String credit = "";

        try {
            sOutput.writeObject(new Communication(Communication.RETURNCREDIT));
            credit = (String) sInput.readObject();


        } catch (IOException e) {
            System.out.println("Exception sending get user credit request: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion acquired user credit: " + e2);
        }

        return Double.parseDouble(credit);

    }

    public boolean buy(Item item){

        boolean updated = false;
        try {
            sOutput.writeObject(new Communication(Communication.ADDTRANSACTION, Integer.toString(item.getItem_code())));
            updated = (boolean) sInput.readObject();


        } catch (IOException e) {
            System.out.println("Exception sending buy request: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion buy confirmation: " + e2);
        }

        return updated;

    }

    public boolean check_seller_online(int itemid){
        boolean online = false;
        try {
            sOutput.writeObject(new Communication(Communication.CHECKSELLERONLINE, Integer.toString(itemid)));
            online = (boolean) sInput.readObject();


        } catch (IOException e) {
            System.out.println("Exception checking if seller is online: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("Exception with object conversion of status of seller: " + e2);
        }

        return online;

    }






////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /*  public class Clientchatlistener extends Thread{


        private Chatmessage msg = null;

        public Chatmessage getMsg() {
            return msg;
        }

        public void setMsg(Chatmessage msg) {
            this.msg = msg;
        }

        public void run(){

            listen();

        }

        public void listen(){



            while(true){
                try {
                    msg = (Chatmessage) csInput.readObject();

                    if(msg.getType() == Chatmessage.NEWSESSION){

                        seller_start_session(msg.getSession());

                    }else if(msg.getType() == Chatmessage.MESSAGE){



                    }
                } catch (IOException e) {
                    System.out.println("Exception getting message: " + e);
                } catch (ClassNotFoundException e2) {
                    System.out.println("Exception casting message: " + e2);
                }

            }

        }

        public void startsession(Item item){
            Session newsession = null;
            try {
                sOutput.writeObject(new Communication(Communication.GETNEWSESSION, item, current));
                newsession = (Session) sInput.readObject();
                session = newsession;
                Platform.runLater(() -> cgui.chatsession());

            } catch (IOException e) {
                System.out.println("Exception sending new session request: " + e);
            } catch (ClassNotFoundException e2) {
                System.out.println("Exception with object conversion of session: " + e2);
            }



        }

        public void seller_start_session(Session newsession){
            session = msg.getSession();

            Platform.runLater(() -> cgui.chatsession());



        }

        public void send_message_to_server(Chatmessage chatmessage){
            try {
                csOutput.writeObject(chatmessage);
            } catch (IOException e) {
                System.out.println("Exception forwarding message to server " + e);
            }

        }





    }*/
}
