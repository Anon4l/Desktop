import java.sql.*;

public class Conectando {
  public Connection connection = null;

private final String DRIVER ="";
private final String URL = "";
private final String LOGIN = "";
private final String SENHA = "";


public boolean getConnection(){
    try{
        Class.forName(DRIVER);
        connection = DriverManager.getConnection(URL, LOGIN, SENHA);
        System.out.println("Conectou");
        return true;
    }catch (ClassNotFoundException erro){
       System.out.println("Driver não encontrado" + erro.toString());
       return false;
    }catch (SQLException erro){
        System.out.println("falha ao conectar" + erro.toString());
       return false; 
    }
}
public void close(){
    try{
        connection.close();
         System.out.println("Desconectou");
         
       
    }catch (SQLException erro){
        
    }
}
}