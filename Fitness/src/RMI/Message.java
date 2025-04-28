package RMI;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private String pseudo;
    private String message;
    private Date date;

    public Message(String pseudo, String message) {
        this.pseudo = pseudo;
        this.message = message;
        this.date = new Date();
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "pseudo: " + pseudo + " : " + date.toString() + "\n" + "--> " + message + "\n";
    }
}
