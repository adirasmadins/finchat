import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;
import java.util.Date;

public class Server {

    private ArrayList<ClientThread> allclients;
    private ArrayList<Session> allsessions;
    private int port;
    private int cport;
    private static int uniquesessionid = 0;

    Server(int port, int cport) {
        this.port = port;
        this.cport = cport;
        allclients = new ArrayList<>();
        //allsessions = new ArrayList<>();
    }

    public void start() {

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            ServerSocket cserverSocket = new ServerSocket(cport);

            while(true) {

                System.out.println("Waiting for clients on port " + port);
                System.out.println("Waiting for clients on chat port " + cport);

                Socket socket = serverSocket.accept();
                //Socket updatesocket = serverSocket.accept();
                System.out.println("Client connected on port " + port);
                Socket csocket = cserverSocket.accept();
                System.out.println("Client connected on chat port " + cport);

                ClientThread ct = new ClientThread(socket, csocket);
                allclients.add(ct);
                ct.start();
            }

        }
        catch (IOException e) {
            System.out.println("Exception accepting connection from client: " + e);
        }
    }

    public static void main(String[] args) {
        int portNumber = 1500;
        int cportNumber = 1501;

        Server server = new Server(portNumber, cportNumber);
        server.start();
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    class ClientThread extends Thread {
        private Socket socket;
        private Socket csocket;
        private ObjectInputStream sInput;
        private ObjectOutputStream sOutput;
        private ObjectInputStream csInput;
        private ObjectOutputStream csOutput;
        private Connection c = null;
        private Communication task;
        private User current = null;
        private Serverchatlistener scl;
        private boolean busy = false;

        ClientThread(Socket socket, Socket csocket) {

            this.socket = socket;

            try {

                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput  = new ObjectInputStream(socket.getInputStream());
                System.out.println("Created output/input streams with client");
                csOutput = new ObjectOutputStream(csocket.getOutputStream());
                csInput  = new ObjectInputStream(csocket.getInputStream());
                System.out.println("Created chat output/input streams with client");

                Class.forName("org.sqlite.JDBC");
                c=DriverManager.getConnection("jdbc:sqlite:./finchat.db");
                System.out.println("Client connected to database\n");
                scl = new Serverchatlistener();
                scl.start();

            }
            catch (IOException e) {
                System.out.println("Exception creating new input/output steams: " + e);
            }catch (SQLException e2) {
                System.out.println("Exception with connecting to database: " + e2);
            } catch (ClassNotFoundException e3) {
                System.out.println("Exception with object conversion of SQLite JDBC: " + e3);
            }

        }

        public User getCurrent() {
            return current;
        }

        public void setCurrent(User current) {
            this.current = current;
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

        public Connection getC() {
            return c;
        }

        public void setC(Connection c) {
            this.c = c;
        }

        public Communication getTask() {
            return task;
        }

        public void setTask(Communication task) {
            this.task = task;
        }

        public Serverchatlistener getScl() {
            return scl;
        }

        public void setScl(Serverchatlistener scl) {
            this.scl = scl;
        }

        public boolean isBusy() {
            return busy;
        }

        public void setBusy(boolean busy) {
            this.busy = busy;
        }

        public void run() {
            int temp_task;

            while(true) {

                try {
                    task = (Communication) sInput.readObject();
                    temp_task = task.getTask();

                    if(temp_task == Communication.LOGIN){
                        sOutput.reset();
                        sOutput.writeObject(login());
                    }else if(temp_task == Communication.RETURNITEMS){
                        sOutput.reset();
                        sOutput.writeObject(returnitems());
                    }else if(temp_task == Communication.ADDTRANSACTION){
                        sOutput.reset();
                        sOutput.writeObject(addtransaction());
                    }else if(temp_task == Communication.ADDITEM){
                        sOutput.reset();
                        sOutput.writeObject(additem());
                    }else if(temp_task == Communication.ADDCREDIT){
                        sOutput.reset();
                        sOutput.writeObject(addcredit());
                    }else if(temp_task == Communication.EDITFN){
                        sOutput.reset();
                        sOutput.writeObject(editfn());
                    }else if(temp_task == Communication.EDITLN){
                        sOutput.reset();
                        sOutput.writeObject(editln());
                    }else if(temp_task == Communication.EDITPW){
                        sOutput.reset();
                        sOutput.writeObject(editpw());
                    }else if(temp_task == Communication.EDITADDRESS){
                        sOutput.reset();
                        sOutput.writeObject(editaddress());
                    }else if(temp_task == Communication.EDITSELLITEMS){
                        sOutput.reset();
                        sOutput.writeObject(updatesellitems());
                    }else if(temp_task == Communication.DELETESELLITEM){
                        sOutput.reset();
                        sOutput.writeObject(deletesellitems());
                    }else if(temp_task == Communication.VIEWBOUGHT){
                        sOutput.reset();
                        sOutput.writeObject(returnbought());
                    } else if(temp_task == Communication.VIEWSOLD){
                        sOutput.reset();
                        sOutput.writeObject(returnsold());
                    }else if(temp_task == Communication.LOGOUT){
                        logout();
                    }else if(temp_task == Communication.RETURNSELLINGITEMS){
                        sOutput.reset();
                        sOutput.writeObject(returnselleritems());
                    }else if(temp_task == Communication.RETURNUSERINFO){
                        sOutput.reset();
                        sOutput.writeObject(returncurrentinfo());
                    }else if(temp_task == Communication.RETURNCREDIT){
                        sOutput.reset();
                        sOutput.writeObject(returncredit());
                    }else if(temp_task == Communication.CHECKSELLERONLINE){
                        sOutput.reset();
                        sOutput.writeObject(checkselleronline());
                    }else if(temp_task == Communication.GETNEWSESSION){
                        sOutput.reset();
                        sOutput.writeObject(returnnewsession());
                    }
                    sOutput.flush();
                } catch (IOException e) {
                        System.out.println("Exception reading request from client: " + e);
                        break;
                } catch (ClassNotFoundException e2) {
                        System.out.println("Exception with object conversion of request from client: " + e2);
                        break;
                }

            }

        }

        public User login(){
            String credentials = task.getFields();
            String[] str;
            str = credentials.split("\\s+");
            String un = str[0];
            String pw = str[1];
            PreparedStatement getuser;
            ResultSet rs;
            String username = "";
            User checked_user = null;

            try{

                getuser = c.prepareStatement("SELECT username FROM user WHERE username = ? AND password = ?");
                getuser.setString(1, un);
                getuser.setString(2, pw);
                rs = getuser.executeQuery();

                while (rs.next()) {
                    username = rs.getString("username");
                }

                rs.close();
                getuser.close();


                if(!(username.equals(""))){
                    checked_user = new User(username);
                    current = checked_user;
                }

            } catch (SQLException e) {
                System.out.println("Exception creating statement to authenticate with database: " + e);
            }

            return checked_user;


        }

        public ArrayList<Item> returnitems(){
            ArrayList<Item> items = new ArrayList<>();
            PreparedStatement getitems;
            ResultSet rs;
            try{

                getitems = c.prepareStatement("SELECT itemid, name, description, postedprice FROM item, user WHERE item.sellerid = user.userid AND sold = 0 AND username != ?");
                getitems.setString(1, current.getUsername());
                rs = getitems.executeQuery();

                while (rs.next()) {
                    items.add(new Item(rs.getString("name"), rs.getString("description"), rs.getDouble("postedprice"), rs.getInt("itemid")));
                }

                rs.close();
                getitems.close();


            } catch (SQLException e) {
                System.out.println("Exception creating statement to return all items in database: " + e);
            }
            return items;

        }

        public boolean addtransaction(){
            int itemid = Integer.parseInt(task.getFields());
            int addressid = 0, sellerid = 0, buyerid = 0;
            double pricesold = 0;
            double buyercredit = 0, sellercredit = 0;
            PreparedStatement getaddressidbuyerid, getselleridpricesold, newtransaction, updateitemstate, updatesellercredit, updatebuyercredit, getsellcredit;
            ResultSet addressidrs, selleridpricesoldrs, sellercreditrs;

            try{

                getaddressidbuyerid = c.prepareStatement("SELECT addressid, userid, credit FROM user WHERE username = ?");
                getaddressidbuyerid.setString(1, current.getUsername());
                addressidrs = getaddressidbuyerid.executeQuery();

                while (addressidrs.next()) {
                    addressid = addressidrs.getInt("addressid");
                    buyerid = addressidrs.getInt("userid");
                    buyercredit = addressidrs.getDouble("credit");
                }
                addressidrs.close();
                getaddressidbuyerid.close();




                getselleridpricesold = c.prepareStatement("SELECT postedprice, sellerid FROM item WHERE itemid = ?");
                getselleridpricesold.setInt(1, itemid);
                selleridpricesoldrs = getselleridpricesold.executeQuery();

                while (selleridpricesoldrs.next()) {
                    pricesold = selleridpricesoldrs.getDouble("postedprice");
                    sellerid = selleridpricesoldrs.getInt("sellerid");
                }
                selleridpricesoldrs.close();
                getselleridpricesold.close();





                getsellcredit = c.prepareStatement("SELECT credit FROM user WHERE userid = ?");
                getsellcredit.setInt(1, sellerid);
                sellercreditrs = getsellcredit.executeQuery();

                while (sellercreditrs.next()) { ;
                    sellercredit = sellercreditrs.getDouble("credit");
                }
                sellercreditrs.close();
                getsellcredit.close();



                newtransaction = c.prepareStatement("INSERT INTO trans (buyerid, sellerid, itemid, pricesold, shippingaddress, status, date_time) VALUES " +
                        "(?, ?, ?, ?, ?, ?, ?)");
                newtransaction.setInt(1, buyerid);
                newtransaction.setInt(2, sellerid);
                newtransaction.setInt(3, itemid);
                newtransaction.setDouble(4, pricesold);
                newtransaction.setInt(5, addressid);
                newtransaction.setString(6, "Waiting for seller to ship.");
                newtransaction.setDate(7, new java.sql.Date(new java.util.Date().getTime()));
                newtransaction.executeUpdate();
                newtransaction.close();

                updateitemstate = c.prepareStatement("UPDATE item SET sold = 1 WHERE itemid = ?");
                updateitemstate.setInt(1, itemid);
                updateitemstate.executeUpdate();
                updateitemstate.close();

                buyercredit = buyercredit - pricesold;
                sellercredit = sellercredit + pricesold;

                updatebuyercredit = c.prepareStatement("UPDATE user SET credit = ? WHERE userid = ?");
                updatebuyercredit.setDouble(1, buyercredit);
                updatebuyercredit.setInt(2, buyerid);
                updatebuyercredit.executeUpdate();
                updatebuyercredit.close();

                updatesellercredit = c.prepareStatement("UPDATE user SET credit = ? WHERE userid = ?");
                updatesellercredit.setDouble(1, sellercredit);
                updatesellercredit.setInt(2, sellerid);
                updatesellercredit.executeUpdate();
                updatesellercredit.close();


                return true;

            } catch (SQLException e) {
                System.out.println("Exception trying to process transaction: " + e);
                return false;
            }

        }

        public boolean additem(){
            Item item = task.getItem();
            String item_name = item.getName();
            String item_description = item.getDescription();
            Double posted_price = item.getPrice_posted();
            int sellerid = 0;
            PreparedStatement getsellerid, newitem;
            ResultSet sellrs;

            try{

                getsellerid = c.prepareStatement("SELECT userid FROM user WHERE username = ?");
                getsellerid.setString(1, current.getUsername());
                sellrs = getsellerid.executeQuery();

                while (sellrs.next()) {
                    sellerid = sellrs.getInt("userid");
                }
                sellrs.close();
                getsellerid.close();

                newitem = c.prepareStatement("INSERT INTO item (name, description, postedprice, sellerid) VALUES " +
                        "(?, ?, ?, ?)");
                newitem.setString(1, item_name);
                newitem.setString(2, item_description);
                newitem.setDouble(3, posted_price);
                newitem.setInt(4, sellerid);
                newitem.executeUpdate();
                newitem.close();

                return true;

            } catch (SQLException e) {
                System.out.println("Exception trying to add new item: " + e);
                return false;
            }


        }

        public boolean addcredit(){
            double credit_amount = Double.parseDouble(task.getFields());
            PreparedStatement getcurrentusercredit, updatecredit;
            ResultSet creditrs;
            double usercredit = 0;

            try{

                getcurrentusercredit = c.prepareStatement("SELECT credit FROM user WHERE username = ?");
                getcurrentusercredit.setString(1, current.getUsername());
                creditrs = getcurrentusercredit.executeQuery();

                while (creditrs.next()) {
                    usercredit = creditrs.getDouble("credit");
                }
                creditrs.close();
                getcurrentusercredit.close();

                updatecredit = c.prepareStatement("UPDATE user SET credit = ? WHERE username = ?");
                updatecredit.setDouble(1, usercredit + credit_amount);
                updatecredit.setString(2, current.getUsername());
                updatecredit.executeUpdate();
                updatecredit.close();

                return true;

            } catch (SQLException e) {
                System.out.println("Exception trying to add credit: " + e);
                return false;
            }
        }

        public boolean editfn(){
            String fn = task.getFields();
            PreparedStatement updatefirstname;
            try{
                updatefirstname = c.prepareStatement("UPDATE user SET firstname = ? WHERE username = ?");
                updatefirstname.setString(1, fn);
                updatefirstname.setString(2, current.getUsername());
                updatefirstname.executeUpdate();
                updatefirstname.close();

                return true;

            } catch (SQLException e) {
                System.out.println("Exception trying to update firstname: " + e);
                return false;
            }

        }

        public boolean editln(){

            String ln = task.getFields();
            PreparedStatement updatelastname;
            try{
                updatelastname = c.prepareStatement("UPDATE user SET lastname = ? WHERE username = ?");
                updatelastname.setString(1, ln);
                updatelastname.setString(2, current.getUsername());
                updatelastname.executeUpdate();
                updatelastname.close();

                return true;

            } catch (SQLException e) {
                System.out.println("Exception trying to update lastname: " + e);
                return false;
            }

        }

        public boolean editpw(){

            String pw = task.getFields();
            PreparedStatement updatepw;
            try{
                updatepw = c.prepareStatement("UPDATE user SET password = ? WHERE username = ?");
                updatepw.setString(1, pw);
                updatepw.setString(2, current.getUsername());
                updatepw.executeUpdate();
                updatepw.close();

                return true;

            } catch (SQLException e) {
                System.out.println("Exception trying to update password: " + e);
                return false;
            }

        }

        public boolean editaddress(){

            Address address = task.getAddress();
            String street = address.getStreet();
            String city = address.getCity();
            String province = address.getProvince();
            String postalcode = address.getPostalcode();
            String country = address.getCountry();
            PreparedStatement updateaddress;

            try{
                updateaddress = c.prepareStatement("UPDATE address SET street = ?, " +
                        "city = ?, province = ?, postalcode = ?, country = ? WHERE addressid IN (SELECT address.addressid FROM user, " +
                        "address WHERE user.addressid = address.addressid AND username = ?)");
                updateaddress.setString(1, street);
                updateaddress.setString(2, city);
                updateaddress.setString(3, province);
                updateaddress.setString(4, postalcode);
                updateaddress.setString(5, country);
                updateaddress.setString(6, current.getUsername());
                updateaddress.executeUpdate();
                updateaddress.close();

                return true;

            } catch (SQLException e) {
                System.out.println("Exception trying to update address: " + e);
                return false;
            }

        }

        public boolean updatesellitems(){
            Item item  = task.getItem();
            double price = item.getPrice_posted();
            String name = item.getName();
            int itemcode = item.getItem_code();
            String description = item.getDescription();
            PreparedStatement updatesellitems;

            try{
                updatesellitems = c.prepareStatement("UPDATE item SET postedprice = ?, " +
                        "name = ?, description = ? WHERE itemid = ?");
                updatesellitems.setDouble(1, price);
                updatesellitems.setString(2, name);
                updatesellitems.setString(3, description);
                updatesellitems.setInt(4, itemcode);
                updatesellitems.executeUpdate();
                updatesellitems.close();

                return true;

            } catch (SQLException e) {
                System.out.println("Exception trying to update sell items: " + e);
                return false;
            }


        }

        public boolean deletesellitems(){
            int itemid = Integer.parseInt(task.getFields());
            PreparedStatement deleteitem;

            try{

                deleteitem = c.prepareStatement("DELETE FROM item WHERE itemid = ?");
                deleteitem.setInt(1, itemid);
                deleteitem.executeUpdate();
                deleteitem.close();

                return true;

            } catch (SQLException e) {
                System.out.println("Exception trying to delete item: " + e);
                return false;
            }

        }

        public ArrayList<Transaction> returnbought(){
            ArrayList<Transaction> boughtitems = new ArrayList<>();
            PreparedStatement getbought;
            ResultSet boughtrs;
            try{
                getbought = c.prepareStatement("SELECT name, description, pricesold, date_time, street, city, province, " +
                        "postalcode, country, status FROM item, trans, address, user WHERE trans.itemid = item.itemid AND " +
                        "trans.shippingaddress=address.addressid AND trans.buyerid = user.userid AND username = ?");
                getbought.setString(1, current.getUsername());
                boughtrs = getbought.executeQuery();

                while (boughtrs.next()){
                    String name = boughtrs.getString("name");
                    String description = boughtrs.getString("description");
                    Double price = boughtrs.getDouble("pricesold");
                    Date date = boughtrs.getDate("date_time");
                    String street = boughtrs.getString("street");
                    String city = boughtrs.getString("city");
                    String province = boughtrs.getString("province");
                    String postalcode = boughtrs.getString("postalcode");
                    String country = boughtrs.getString("country");
                    String status = boughtrs.getString("status");
                    Address address = new Address(street, city, province, country, postalcode);
                    Transaction transaction = new Transaction(name, description, price, date, address, status);
                    boughtitems.add(transaction);
                }

                boughtrs.close();
                getbought.close();

            } catch (SQLException e) {
                System.out.println("Exception trying to get sold items: " + e);

            }

            return boughtitems;
        }

        public ArrayList<Transaction> returnsold(){
            ArrayList<Transaction> solditems = new ArrayList<>();
            PreparedStatement getsold;
            ResultSet soldrs;
            try{
                getsold = c.prepareStatement("SELECT name, description, pricesold, date_time, street, city, province, " +
                        "postalcode, country, status FROM item, trans, address, user WHERE trans.itemid = item.itemid AND " +
                        "address.addressid = trans.shippingaddress AND trans.sellerid = user.userid AND username = ?");
                getsold.setString(1, current.getUsername());
                soldrs = getsold.executeQuery();

                while (soldrs.next()){
                    String name = soldrs.getString("name");
                    String description = soldrs.getString("description");
                    Double price = soldrs.getDouble("pricesold");
                    Date date = soldrs.getDate("date_time");
                    String street = soldrs.getString("street");
                    String city = soldrs.getString("city");
                    String province = soldrs.getString("province");
                    String postalcode = soldrs.getString("postalcode");
                    String country = soldrs.getString("country");
                    String status = soldrs.getString("status");
                    Address address = new Address(street, city, province, country, postalcode);
                    Transaction transaction = new Transaction(name, description, price, date, address, status);
                    solditems.add(transaction);
                }

                soldrs.close();
                getsold.close();

            } catch (SQLException e) {
                System.out.println("Exception trying to get sold items: " + e);

            }

            return solditems;
        }

        public ArrayList<Item> returnselleritems(){
            ArrayList<Item> sellingitems = new ArrayList<>();
            PreparedStatement getselling;
            ResultSet sellingrs;
            try{
                getselling = c.prepareStatement("SELECT name, description, postedprice, itemid FROM item, user WHERE user.userid = item.sellerid AND sold = 0 AND user.username = ?");
                getselling.setString(1, current.getUsername());
                sellingrs = getselling.executeQuery();

                while (sellingrs.next()){
                    String name = sellingrs.getString("name");
                    String description = sellingrs.getString("description");
                    Double price = sellingrs.getDouble("postedprice");
                    int itemid = sellingrs.getInt("itemid");
                    sellingitems.add(new Item(name, description, price, itemid));
                }

                sellingrs.close();
                getselling.close();

            } catch (SQLException e) {
                System.out.println("Exception trying to get currently selling items by user: " + e);

            }

            return sellingitems;
        }

        public ArrayList<String> returncurrentinfo(){
            PreparedStatement getcurrent;
            ResultSet currentrs;
            ArrayList<String> info = new ArrayList<>();
            try{
                getcurrent = c.prepareStatement("SELECT firstname, lastname, password, street, city, province, postalcode, country  FROM user, address WHERE user.addressid = address.addressid AND username = ?");
                getcurrent.setString(1, current.getUsername());
                currentrs = getcurrent.executeQuery();

                while (currentrs.next()){
                    String firstname = currentrs.getString("firstname");
                    String lastname = currentrs.getString("lastname");
                    String password = currentrs.getString("password");
                    String street = currentrs.getString("street");
                    String city = currentrs.getString("city");
                    String province = currentrs.getString("province");
                    String postalcode = currentrs.getString("postalcode");
                    String country = currentrs.getString("country");
                    info.add(firstname);
                    info.add(lastname);
                    info.add(password);
                    info.add(street);
                    info.add(city);
                    info.add(province);
                    info.add(postalcode);
                    info.add(country);
                }

                currentrs.close();
                getcurrent.close();

            } catch (SQLException e) {
                System.out.println("Exception trying to get currently selling items by user: " + e);

            }




            return info;
        }

        public String returncredit(){

            PreparedStatement getcredit;
            ResultSet creditrs;
            String credit = "";
            try{
                getcredit = c.prepareStatement("SELECT credit  FROM user WHERE username = ?");
                getcredit.setString(1, current.getUsername());
                creditrs = getcredit.executeQuery();

                while (creditrs.next()){
                    credit = Double.toString(creditrs.getDouble("credit"));


                }

                creditrs.close();
                getcredit.close();

            } catch (SQLException e) {
                System.out.println("Exception trying to get current user credit: " + e);

            }

            return credit;


        }

        public boolean checkselleronline(){
            int itemid = Integer.parseInt(task.getFields());
            boolean online = false;
            String un = "";
            try{

                PreparedStatement getseller;
                ResultSet getsellerrs;

                getseller = c.prepareStatement("SELECT username FROM user, item WHERE user.userid = item.sellerid AND itemid = ?");
                getseller.setInt(1, itemid);
                getsellerrs = getseller.executeQuery();
                while(getsellerrs.next()){
                    un = getsellerrs.getString("username");
                }

                for(int i = 0; i<allclients.size(); i++){
                    if(allclients.get(i).getCurrent() == null){
                     continue;
                    }else if(allclients.get(i).getCurrent().getUsername().equals(un)){
                        if(!(allclients.get(i).isBusy())) {
                            online = true;
                            break;
                        }
                    }
                }


            }catch (SQLException e){
                System.out.println("Exception finding the seller of item");
            }

            return online;
        }

        public Session returnnewsession(){
            Item item = task.getItem();
            User buyer = task.getUser();
            PreparedStatement getiteminfo;
            ResultSet iteminfors;
            Double price = 0.0;
            User seller = null;
            Session newsession = null;

            try{
                this.busy = true;
                getiteminfo = c.prepareStatement("SELECT username, postedprice FROM item, user WHERE user.userid = item.sellerid AND itemid = ?");
                getiteminfo.setInt(1, item.getItem_code());
                iteminfors = getiteminfo.executeQuery();

                while(iteminfors.next()){
                    price = iteminfors.getDouble("postedprice");
                    seller = new User(iteminfors.getString("username"));
                }

                iteminfors.close();
                getiteminfo.close();

                newsession = new Session(++uniquesessionid, buyer, seller, price, item);
                allsessions.add(newsession);

                for(int i = 0; i<allclients.size(); i++){
                    if(allclients.get(i).getCurrent().getUsername().equals(seller.getUsername())){
                        try {
                            allclients.get(i).getCsOutput().writeObject(new Chatmessage(newsession, Chatmessage.NEWSESSION, ""));
                            allclients.get(i).setBusy(true);
                        } catch (IOException e) {
                            System.out.println("Exception sending seller chat request " + e);
                        }
                    }
                }

            } catch (SQLException e) {
                System.out.println("Exception getting item info: " + e);
            }

            return newsession;

        }



        public void logout(){
            current = null;
        }

        public class Serverchatlistener extends Thread{


            public void run() {

                listen();
            }

            public void listen(){

                Chatmessage chatmessage;

                try {
                    chatmessage = (Chatmessage) csInput.readObject();

                    if(chatmessage.getType() == Chatmessage.MESSAGE){
                        broadcast_message(chatmessage);
                    }
                } catch (IOException e) {
                    System.out.println("Exception receiving chat messages " + e);
                } catch (ClassNotFoundException e2) {
                    System.out.println("Exception converting chat object " + e2);
                }

            }

            public void broadcast_message(Chatmessage chatmessage){
                Session session;
                session = chatmessage.getSession();

                for(int i = 0; i<allclients.size(); i++){
                    if(allclients.get(i).getCurrent().getUsername().equals(session.getBuyer().getUsername()) ||
                            allclients.get(i).getCurrent().getUsername().equals(session.getSeller().getUsername())){
                        try {
                            allclients.get(i).getCsOutput().writeObject(chatmessage);

                        } catch (IOException e) {
                            System.out.println("Exception broadcasting message " + e);
                        }
                    }
                }
            }

        }

    }
}

