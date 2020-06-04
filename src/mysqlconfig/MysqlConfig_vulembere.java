/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wills
 */
public class MysqlConfig_vulembere {

    private Connection con = null;
    private String serveur = null;
    private String user = null;
    private String password = null;
    private String bd = null;
    private int port = 0;
    //a crréer
    private String newUser = null;
    private String newBd = null;
    private String newPassword = null;

    public String getServeur() {
        return serveur;
    }

    public void setServeur(String serveur) {
        this.serveur = serveur;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBd() {
        return bd;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getNewUser() {
        return newUser;
    }

    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }

    public String getNewBd() {
        return newBd;
    }

    public void setNewBd(String newBd) {
        this.newBd = newBd;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * *
     * Constructeur d'initialisation de la configuration du serveur
     *
     * @param Hot
     * @param Bd
     * @param User
     * @param Password
     * @param Port
     */
    public MysqlConfig_vulembere(String Hot, String Bd, String User, String Password, int Port) {
        this.serveur = Hot;
        this.password = Password;
        this.user = User;
        this.bd = Bd;
        this.port = Port;
        if (con == null) {
            this.con = connecter();
        } else {
            System.out.println("Connexion exist...");
        }

    }

    /**
     * *
     * Retourne la connexion disponible , elle est null si elle a échouée
     *
     * @return
     */
    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    /**
     * *
     * Initialise les coordonnées de la nouvelle base de données et du nouvel
     * utilisateur
     *
     * @param newBd
     * @param newUser
     * @param newPassword
     */
    public void newUser(String newBd, String newUser, String newPassword) {
        this.newBd = newBd;
        this.newPassword = newPassword;
        this.newUser = newUser;
    }

    /**
     * *
     * Méthode de connexion au serveur Mysql
     *
     * @return
     */
    public Connection connecter() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + this.serveur + ":" + this.port + "/" + this.bd, this.user, this.password);
            System.out.println("Connection reusse !");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MysqlConfig_vulembere.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    /**
     * *
     * Cette fonction crée l'utilisateur initialisé dans la fonction newUser
     *
     * @return
     */
    public boolean createUser() {
        try {
            Statement st = this.con.createStatement();
            st.execute("CREATE USER '" + this.newUser + "'@'%' IDENTIFIED BY '" + this.newPassword + "';");
            System.out.println("Creation user reusse !");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MysqlConfig_vulembere.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * *
     * Accorde des privilèges au nouveau l'utilisateur initialisé dans la
     * fonction newUser et retourne true si la creation a reussie
     *
     * @return
     */
    public boolean addAllPrivilege() {
        try {
            Statement st = this.con.createStatement();
            st.execute("GRANT ALL PRIVILEGES ON * . * TO '" + this.newUser + "'@'%';");
            System.out.println("Création privillege réussie !");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MysqlConfig_vulembere.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * *
     * cette fonction crée la base des donnees Entrée dans la fonction newUser
     * et retourne true Si le traitement réussie
     *
     * @return
     */
    public boolean createDatabase() {
        try {
            Statement st = this.con.createStatement();
            st.execute(" CREATE DATABASE IF NOT EXISTS " + this.newBd + ";");
            System.out.println("Creation base des donnees reusse !");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MysqlConfig_vulembere.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean Autoriser() {
        try {
            Statement st = this.con.createStatement();
            st.execute("FLUSH PRIVILEGES;");
            System.out.println("Activation des privilege reussie  !");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MysqlConfig_vulembere.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
