/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlconfig;

/**
 *
 * @author wills
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MysqlConfig_vulembere config = new MysqlConfig_vulembere("localhost", "mysql", "root", "", 3306);
        config.newUser("BD1", "BD1", "passe");
        if (config.getCon() != null) {
            config.createDatabase();
            config.createUser();
            config.addAllPrivilege();
            config.Autoriser();
            System.out.println(" traitement succed ");
        } else {
            System.out.println("Erreur de connexion");
        }
    }

}
