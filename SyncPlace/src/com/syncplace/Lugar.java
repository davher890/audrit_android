package com.syncplace;

public class Lugar {
	
	private String nombre;
	private String descripcion;
	private double latitud;
	private double longitud;
	private String tipo;
	
	public Lugar(String nombre, String descripcion, Double latitud, Double longitud, String tipo){
		this.longitud = longitud;
		this.latitud = latitud;
		this.descripcion = descripcion;
		this.nombre = nombre;
		this.tipo = tipo;
	}
	
	public String getNombre(){
		return nombre;		
	}

	public double getLatitud(){
		return latitud;		
	}
	
	public double getLongitud(){
		return longitud;
	}
	
	public String getDescripcion(){
		return descripcion;
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public void setNombre(String nom){
		nombre = nom;
	}
	
	public void setDescripcion(String desc){
		descripcion = desc;
	}
	
	public void setLatitud(Double lat){
		latitud = lat;		
	}	
	
	public void setLongitud(Double lon){
		longitud = lon;
	}
}
