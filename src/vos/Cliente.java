package vos;

import org.codehaus.jackson.annotate.*;


/**
 * @author Nicolás Mateo Hernández Rojas 	- 	nm.hernandez10@uniandes.edu.co
 * @author David Felipe Niño Romero		-	df.nino10@uniandes.edu.co
 * Clase que representa a los Clientes del modelo AlohAndes
 */

public class Cliente 
{	
	@JsonProperty(value="id")
	private long id;
	
	@JsonProperty(value="identificacion")
	private long identificacion;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="edad")
	private int edad;
	
	@JsonProperty(value="direccion")
	private String direccion;
	
	public enum Vinculo
	{
		ESTUDIANTE,
		PROFESOR,
		PROFESOR_INVITADO,
		PADRE_DE_ESTUDIANTE,
		EMPLEADO,
		EGRESADO,
		INVITADO_A_EVENTO
	}
	
	/**
	 * Vínculo del cliente
	 */
	@JsonProperty(value="vinculo")
	private Vinculo vinculo;
	
	public Cliente(@JsonProperty(value="id") long id, @JsonProperty(value="identificacion") long identificacion, @JsonProperty(value="nombre")String nombre, @JsonProperty(value="edad")int edad,@JsonProperty(value="direccion")String direccion, @JsonProperty(value="vinculo")Vinculo vinculo)	
	{
		this.id = id;
		this.identificacion = identificacion;
		this.nombre = nombre;
		this.edad = edad;
		this.direccion = direccion;
		this.vinculo = vinculo;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getIdentificacion()
	{
		return identificacion;
	}

	public void setIdentificacion(long identificacion) 
	{
		this.identificacion = identificacion;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public int getEdad() 
	{
		return edad;
	}

	public void setEdad(int edad) 
	{
		this.edad = edad;
	}

	public String getDireccion() 
	{
		return direccion;
	}

	public void setDireccion(String direccion) 
	{
		this.direccion = direccion;
	}

	public Vinculo getVinculo()
	{
		return vinculo;
	}

	public void setVinculo(Vinculo vinculo)
	{
		this.vinculo = vinculo;
	}	
}
