package com.clases;

public class Usuario {
	
	private static String id = null;
	private static String token = null;
	private static Perfil usuario= null;
	private static String email = null;
	private static String pwd = null;

	public static String getToken() {
		return token;
	}
	public static void setToken(String token) {
		Usuario.token = token;
	}
	public static Perfil getUsuario() {
		return usuario;
	}
	public static void setUsuario(Perfil usuario) {
		Usuario.usuario = usuario;
	}
	public static String getId() {
		return id;
	}
	public static void setId(String id) {
		Usuario.id = id;
	}
	public static String getEmail() {
		return email;
	}
	public static void setEmail(String email) {
		Usuario.email = email;
	}
	public static String getPwd() {
		return pwd;
	}
	public static void setPwd(String pwd) {
		Usuario.pwd = pwd;
	}	
}
