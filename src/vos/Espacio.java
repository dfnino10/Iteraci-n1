package vos;

import java.sql.Date;

import rest.JsonProperty;

import vos.JsonProperty;

/**
 * @author Nicol�s Mateo Hern�ndez Rojas 	- 	nm.hernandez10@uniandes.edu.co
 * @author David Felipe Ni�o Romero		-	df.nino10@uniandes.edu.co
 * Clase que representa a los Espacios del modelo AlohAndes
 */

public class Espacio 
{
	@JsonProperty(value="id")
	private long id;
	
	@JsonProperty(value="registro")
	private long registro;
	
	@JsonProperty(value="capacidad")
	private int capacidad;
	
	@JsonProperty(value="tama�o")
	private double tama�o;
	
	@JsonProperty(value="ubicaci�n")
	private String ubicacion;
	
	@JsonProperty(value="precio")
	private double precio;
	
	@JsonProperty(value="fechaRetiro")
	private Date fechaRetiro;
	
	
}
