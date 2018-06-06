import java.io.*;

public class User implements Serializable{

    protected static final long serialVersionUID = 1112122200L;
    private String username;

    User(String username) {
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}



