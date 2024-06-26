/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql_classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
 import java.sql.Statement;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 * @author 
 */
public class SqlConnectionMethods {
    
    protected Connection conn;
    protected Statement statement;
    ResultSet result;
    
    
    
    
    
    
    
    public void alertERROR(String message){
        JOptionPane.showMessageDialog(null, message,"Error",0);
    }
    
    public void alertInfo(String message){
        JOptionPane.showMessageDialog(null, message,"Info",1);
    }
    
    public void alertWarning(String message){
        JOptionPane.showMessageDialog(null, message,"Warning",2);
    }
    
    public int showConfirmDialog(String title,String message){
        int result = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            // User clicked "Yes"
            return 0;
        } else if (result == JOptionPane.NO_OPTION) {
            // User clicked "No"
            return 1;
        } else if (result == JOptionPane.CANCEL_OPTION) {
            // User clicked "Cancel" or closed the dialog
            return 2;
        }
        return 2;
    }
    
    
    
    public ResultSet execSelect(String query,String logSuccss,String logError){
        try{
            result=this.openDatabaseConnect().executeQuery(query);
            System.out.println(logSuccss);
            return result;
        } catch(Exception e){
            System.err.println(logError+" "+e.getMessage());
            this.alertERROR(logError+" "+e.getMessage());
        }finally{
        }
        return null;
    }
  
    
    public int execDeleteOrUpdateOrInsert(String query,String logSuccss,String logError){
        try{
            int result = this.openDatabaseConnect().executeUpdate(query);
            System.out.println(logSuccss);
            return result;
        } catch(Exception e){
            System.err.println(logError+" "+e.getMessage());
            this.alertERROR(logError+" "+e.getMessage());
        }finally{
        }
        return 0;
    }
    
    public Statement openDatabaseConnect(){
        try {
            if(this.conn!=null){
                this.statement.close();
                this.conn.close();
            }
            this.conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/clinic_doctor_v01?zeroDateTimeBehavior=convertToNull","root",""); 
            this.statement = conn.createStatement();
            System.out.println("Database Connected");
            return statement;
        } catch (Exception e) {
            System.err.println("Database Not Connected because: "+e.getMessage());
            this.alertERROR("Database Not Connected because: "+e.getMessage());
        }
        return null;
    }
    
    public Statement closeDatabaseConnect(){
        try {
            this.statement.close();
            this.conn.close();
            System.out.println("Database closed");
            return statement;
        } catch (Exception e) {
            System.err.println("Database Not closed because: "+e.getMessage());
            this.alertERROR("Database Not closed because: "+e.getMessage());
        }
        return null;
    }
    
    
}
