package org.is.webApp;

import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class ListaUsuarios {
	
	private ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
	
	//private String rutaArchivo = "../workspace/PrimerWebProject/WebContent/datos/usuarios.txt";
	private String rutaArchivo = "PrimerWebProjectSQLite/WebContent/datos/usuarios.db";
	
	/* TIP: Edit tomcat defaul directory:
	*  and I edit the tomcat argument in Eclipse IDE. 
	*  (Run -> Run Config... -> Apache Tomcat -> [Click] Tomcat vX Server -> 
	*  at the right screen, click "Argument" -> Working directory section -> 
	*  I change to Other and specify my actual working directory.)
	*  Variables : ${workspace_loc}
	*/
	
	/**
	 * @return the listaUsuarios
	 */
	public ArrayList<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	/**
	 * @param listaUsuarios the listaUsuarios to set
	 */
	public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public ListaUsuarios () throws Exception{
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:PrimerWebProjectSQLite/WebContent/datos/usuarios.db");
		
	       	Statement stat = conn.createStatement();
	       	// Borrado de la tabla - comentar cuando funcione - descomentar para limpiar tabla
	       	//stat.executeUpdate("drop table if exists usuarios;");
			
	       	// Creamos la tabla si no existe
	       	DatabaseMetaData dbm = conn.getMetaData();
			// check if "usuarios" table is there
			ResultSet rsTables = dbm.getTables(null, null, "usuarios", null);
			if (rsTables.next()) {
			  // Table exists
				
				ResultSet rs = stat.executeQuery("select * from usuarios;");
/*			    while (rs.next()) {
			    	System.out.print("[ListaUsuarios] username = " + rs.getString("username"));
			    	System.out.print(" | password = " + rs.getString("password"));
			        System.out.println(" | fechaCreacion = " + rs.getString("fechaCreacion"));
			    }
*/			    rs.close();
			}
			else {
			  // Table does not exist
				stat.executeUpdate("create table usuarios(username, password, fechaCreacion);");
			}
			rsTables.close();
	        conn.close();
		}
		catch (Exception e) {
        	e.printStackTrace();
		}
        
        
		// cargamos la lista de usuarios 
		this.listaUsuarios = cargarUsuariosDeSQLite();
		
		for (int i=0; i<getListaUsuarios().size(); i++){
			Usuario u = getListaUsuarios().get(i);
			System.out.println(u.toString());
		}

	}
	
	public ArrayList<Usuario> cargarUsuariosDeSQLite(){
		System.out.println("Cargando Usuarios de SQLite: "+ rutaArchivo);
		ArrayList<Usuario> usuariosLeidos = new ArrayList<Usuario>();

		String username = null, password = null; 
		String fecha=null;
		int i=0;

		try {

			Class.forName("org.sqlite.JDBC");
	        Connection conn = DriverManager.getConnection("jdbc:sqlite:PrimerWebProjectSQLite/WebContent/datos/usuarios.db");
	        Statement stat = conn.createStatement();
	        
	        ResultSet rs = stat.executeQuery("select * from usuarios;");
		    while (rs.next()) {
		    	username = rs.getString("username");
		    	password = rs.getString("password");
		    	fecha = rs.getString("fechaCreacion");

		    	System.out.print("[cargarUsuariosDeSQLite] username = " + rs.getString("username"));
		    	System.out.print(" | password = " + rs.getString("password"));
		        System.out.println(" | fechaCreacion = " + rs.getString("fechaCreacion"));
		        
		        Usuario usuario= new Usuario(username, password, fecha);
				// Añadir usuario a la lista
				usuariosLeidos.add(usuario);
				i++;
		    }
		    rs.close();
		    conn.close();
			System.out.println("Numero Usuarios leidos:" + i );

		} catch (Exception e) {
			System.out.println("Error en cargarUsuariosDeSQLite:" + e.getMessage());
		}

		return usuariosLeidos; // ArrayList<Usuario> 
	}
	
	
	public void guardarUsuariosEnSQLite (){
		//this.rutaArchivo.concat("salida.txt");
		System.out.println("Guardando Usuarios en SQLite: "+ this.rutaArchivo);

		try {
			Class.forName("org.sqlite.JDBC");
	        Connection conn = DriverManager.getConnection("jdbc:sqlite:PrimerWebProjectSQLite/WebContent/datos/usuarios.db");
	        Statement stat = conn.createStatement();

	        PreparedStatement prep = conn.prepareStatement(
		            "insert into usuarios values (?, ?, ?);");
	        
			for (int i = 0; i < this.listaUsuarios.size(); i++) {

		        prep.setString(1, this.listaUsuarios.get(i).getUsername());
		        prep.setString(2, this.listaUsuarios.get(i).getPassword());
		        prep.setString(3, this.listaUsuarios.get(i).getStringFechaCreacion());
		        prep.addBatch();
			        
				String linea = 
						this.listaUsuarios.get(i).getUsername() 
						+ "|"
						+ this.listaUsuarios.get(i).getPassword()
						+ "|"
						+ this.listaUsuarios.get(i).getStringFechaCreacion();
				System.out.println(linea);
			}

	        conn.setAutoCommit(false);
	        prep.executeBatch();
	        conn.setAutoCommit(true);
		
			conn.close();
		} catch (Exception e) {
			System.out.println("Excepción al guardar usuarios en SQLite");
		}

	}
	
	public int insertarUsuario(Usuario u){
		// Devuelve 1 si el usuario se inserta correctamente
		// Devuelve 0 en caso contrario
		
		/*
		 * @pre El nuevo nombre de usuario no debe existir en la lista de usuarios
		 */
		int encontrado = 0;
		for (int i=0; i<listaUsuarios.size() && encontrado==0; i++ ){
			if (listaUsuarios.get(i).getUsername().equals(u.getUsername()))
				encontrado = 1;
		}
		
		if (encontrado==0) {
			listaUsuarios.add(u);
			guardarUsuarioEnSQLite(u);
			return 1;
		}
		else return 0;
	}
	
	public void guardarUsuarioEnSQLite (Usuario u){
		//this.rutaArchivo.concat("salida.txt");
		System.out.println("Guardando Usuario en SQLite: "+ this.rutaArchivo);

		try {
			Class.forName("org.sqlite.JDBC");
	        Connection conn = DriverManager.getConnection("jdbc:sqlite:PrimerWebProjectSQLite/WebContent/datos/usuarios.db");
	        Statement stat = conn.createStatement();

	        PreparedStatement prep = conn.prepareStatement(
		            "insert into usuarios values (?, ?, ?);");
	        
	        prep.setString(1, u.getUsername());
	        prep.setString(2, u.getPassword());
	        prep.setString(3, u.getStringFechaCreacion());
	        prep.addBatch();
			        
				String linea = 
						u.getUsername() 
						+ "|"
						+ u.getPassword()
						+ "|"
						+ u.getStringFechaCreacion();
				System.out.println(linea);

	        conn.setAutoCommit(false);
	        prep.executeBatch();
	        conn.setAutoCommit(true);
		
			conn.close();
		} catch (Exception e) {
			System.out.println("Excepción al guardar usuario en SQLite");
		}

	}

	
}
	
