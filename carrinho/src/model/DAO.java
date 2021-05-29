package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
  //parametros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://10.26.45.180:3306/dbcarrinho";
	private String user = "dba";
	private String password = "Senac@123"; 

	/**
	 * conexão com o banco de dados
	 * @return con 
	 */
	public Connection conectar() {
		//con = objeto
		Connection con = null;
		//tratamento de exceções
		try {
			//uso do driver
			Class.forName(driver);
			//estabelecendo a conexão --- con é o objeto que vai ser o conecta com o server
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
	}
	
}


