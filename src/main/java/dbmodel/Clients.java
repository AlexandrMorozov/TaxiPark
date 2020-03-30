package dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
public class Clients
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @NotNull
    private int clientID;
    @NotNull
    @Column(name="clientname")
    private String clientName;
    @NotNull
    @Column(name="clientsurname")
    private String clientSurname;
    @NotNull
    @Column(name="clientlogin")
    private String clientLogin;
    @NotNull
    @Column(name="clientpassword")
    private String clientPassword;
    @NotNull
    @Column(name="dateofbirth")
    private String dateOfBirth;
    @NotNull
    @Column(name="telephonenumber")
    private String telephoneNumber;

    public Clients()
    {


    }

    public Clients(String clientName,String clientSurname, String clientLogin, String clientPassword,
                   String dateOfBirth, String telephoneNumber)
    {
        this.clientName=clientName;
        this.clientSurname=clientSurname;
        this.clientLogin=clientLogin;
        this.clientPassword=clientPassword;
        this.dateOfBirth=dateOfBirth;
        this.telephoneNumber=telephoneNumber;
    }

    public int getClientID() {
        return clientID;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public String getClientLogin() {
        return clientLogin;
    }

    public String getClientPassword() {
        return clientPassword;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }

    public void setClientLogin(String clientLogin) {
        this.clientLogin = clientLogin;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
}
