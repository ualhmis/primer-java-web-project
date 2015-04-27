package org.is.webApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Usuario {
	private String username;
	private String password;
	private Date fechaCreacion;
	
	public Usuario(String username, String password){
		this.username = username;
		this.password = password;
		this.fechaCreacion = new Date(); // La fecha se asigna en el momento de la creación
	}

	public Usuario(String username, String password, String fechaCreacion){
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		this.username = username;
		this.password = password;
		try {
			this.fechaCreacion = formatoDelTexto.parse(fechaCreacion);
		} catch (ParseException ex) { ex.printStackTrace();}
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	
	public String getStringFechaCreacion(){
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatoDeFecha.format(getFechaCreacion());
	}
	
	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	@Override
	public String toString() {
		return "[Usuario.toString] Username: "+ getUsername() + " | Password: " 
			+ getPassword() + " | FechaCreacion: " + getStringFechaCreacion();
	}
}
