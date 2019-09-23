package curso;

// Importação do Pacote java.sql para acesso a bancos de dados relacionais
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Application {
	
	public static void main(String[] args) {
					
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<< CONEXÃO COM O BANCO DE DADOS  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"); 
				
		/* As classes Connection, Statement, PreparedStatement e ResultSet
		 * fazem parte do pacote java.sql e servem para acessar
		 * a persistencia de dados (banco de dados)
		*/						
				
		// Dados necessários para CONECTAR AO BANCO DE DADOS
		String driver = "jdbc:mysql:";		
		String servidor = "remotemysql.com";  // "localhost";
	    String banco = "bCXG8pOtiK";	    
	    String urlConexao = driver + "//" + servidor +  "/" + banco;
	    String usuario = "bCXG8pOtiK";
	    String senha = "I26ruqiw2E";
	    		
	    
        // <<<<<< CONEXÃO COM O BD >>>>>>
     	Connection conexao = null;             
		try {        
	        conexao = DriverManager.getConnection(urlConexao,usuario, senha);
	        conexao.setAutoCommit(true);
	        
	        System.out.println(urlConexao);
            System.out.println("Conexão Realizada com sucesso !"); 
	        
	    }		
        catch(SQLException e ) {
        	//e.printStackTrace();
        	System.out.println("Erro de Conexão:" + e.getErrorCode() + " " + e.getMessage().toString());
        	return;
        }	  
		// <<<<<< FIM DA CONEXÃO  COM O BD >>>>>>> 

		
		
		
		
		// <<<<<<<<<<<<<<<<<<<<<<<< INSTRUÇÕES DML  >>>>>>>>>>>>>>>>>>>>>>>>>> 
		

		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<< execução de código SQL >>>>>>>>>>>>>>>>>>>>>>>>>>>>>"); 
        
		
		// executa um comando DML (update ou insert)
		try {	
			// instrução SQL a ser enviada ao BD
			Statement statement = conexao.createStatement();
			
			statement.execute("insert into ALUNO (nome,cpf,matricula,idade,sexo) values ('João da Silva','00100003004','0000006',18,'M')");
						
		} catch (SQLException e) {
			System.out.println("Erro de insert:" + e.getMessage().toString());
		}
				
		
		
		// executa um comando DML (update ou insert)
		/* Mais otimizado (rapida) para execuções repetidas 
		 * e prática com métodos SET 
		 */
		try {
			String sql = "";		
			sql = "insert into ALUNO (nome,cpf,matricula,idade,sexo)";
			sql += " values (?,?,?,?,?)";					
			PreparedStatement prepStatement = conexao.prepareStatement(sql);	
			
			prepStatement.setString(1, "Zezinho");
			prepStatement.setString(2, "1267");
			prepStatement.setString(3, "0000007");
			prepStatement.setInt(4, 15);
			prepStatement.setString(5, "M");
			
			prepStatement.execute(); 
			
		} catch (SQLException e) {
			System.out.println("Erro de insert Prepared:" + e.getMessage().toString());
		}
			
		
		
		
		// <<<<<<<<<<<<<<<<<<<<<<<<<<< INSTRUÇÕES SELECT  >>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
				
		
		
		System.out.println("");			
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<< execução de CONSULTA SQL (com retorno de dados)  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>") ;

		
		System.out.println("Consultando disciplinas...");		
		
		try {						
			Statement statement = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			/* a classe ResultSet respresenta uma matriz 
               contendo as colunas e linhas resultantes 
               da consulta select */
			ResultSet dbResultado = statement.executeQuery("Select id, titulo from DISCIPLINA order by titulo");
			
			// metodo que posiciona no primeiro registro da consulta
			dbResultado.first();
			System.out.println("Primeira Disciplina: " + dbResultado.getString("id") + " " + dbResultado.getString("titulo"));
			
			// metodo que posiciona no ultimo registro da consulta
			dbResultado.last();
			System.out.println("Ultima   Disciplina: " + dbResultado.getString("id") + " " + dbResultado.getString("titulo"));
			
			
		} catch (SQLException e) {

			System.out.println("Erro :" + e.getMessage().toString());
		}
		
		
		
		System.out.println("");
		
		
		
		try {
			
			Statement statement = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet dbResultado = statement.executeQuery("Select nome, cpf from ALUNO");
			
			
			System.out.println("Lista de Alunos");			
			
		    /*  o metodo first() posiciona no primeiro registro da consulta
			 *  e retorna false, se o resultado for vazio 
			 */			
			if (dbResultado.first()) { 
				
				do {					
					System.out.println("Nome: " + dbResultado.getString("nome") + " CPF: " + dbResultado.getString("cpf"));					
				}
				while (dbResultado.next()); 
				
			    /*  o metodo next() posiciona no próximo registro
			     *  da consulta.  Retorna false quando não houver
			     *  mais registros.
			     */				
			}
				
			
		} catch (SQLException e) {

			System.out.println("Erro :" + e.getMessage().toString());
		}
    		
		
	}
	  
			
		
}
