package vos;

import java.sql.Date;

import rest.JsonProperty;

import vos.JsonProperty;

/**
 * @author Nicolás Mateo Hernández Rojas 	- 	nm.hernandez10@uniandes.edu.co
 * @author David Felipe Niño Romero		-	df.nino10@uniandes.edu.co
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
	
	@JsonProperty(value="tamaño")
	private double tamaño;
	
	@JsonProperty(value="ubicación")
	private String ubicacion;
	
	@JsonProperty(value="precio")
	private double precio;
	
	@JsonProperty(value="fechaRetiro")
	private Date fechaRetiro;
	
	
}
