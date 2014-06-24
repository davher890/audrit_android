package com.clases;

public class Perfil {
	
	private String nombre = null;
	private String apellidos = null;
	private String bday = null;
	private String telefono = null;
	private String sexo = null;
	private String direccion = null;
	private String cpostal = null;
	private String ciudad = null;
	
	public Perfil(String nombre, String apellidos, String bday, String telefono, 
				   String sexo, String direccion, String cpostal, String ciudad){
		
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.bday = bday;
		this.telefono = telefono;
		this.sexo = sexo;
		this.direccion = direccion;
		this.cpostal = cpostal;
		this.ciudad = ciudad;
		
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return the bday
	 */
	public String getBday() {
		return bday;
	}

	/**
	 * @param bday the bday to set
	 */
	public void setBday(String bday) {
		this.bday = bday;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the cpostal
	 */
	public String getCpostal() {
		return cpostal;
	}

	/**
	 * @param cpostal the cpostal to set
	 */
	public void setCpostal(String cpostal) {
		this.cpostal = cpostal;
	}

	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

}
