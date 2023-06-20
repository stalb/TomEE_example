package fr.usmb.m2isc.mesure.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Representation d'une mesure.
 * 
 * Il s'agit d'une mesure de temperature dans une piece donnee a une date donnee.
 * 
 * @author Stephane
 *
 */
@Entity
public class Mesure implements Serializable {
	/**
	 * Identifiant de la mesure (unique).
	 * 
	 * Il s'agit de la clef primaire associee a l'objet persistant.
	 * S'il est nul, il sera genere lors de l'ajout de la mesure dans la base de donnees.
	 */
	@Id @GeneratedValue
	private long id;
	/**
	 * Piece dans laquelle la mesure a ete faite.
	 */
	private String piece;
	/**
	 * Temperature mesuree (en degres celsius)
	 */
	private double temp;
	/**
	 * Date a laquelle la mesure a ete faite.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateMesure;
	

	/**
	 * Constructeur sans parametre obligatoire.
	 */
	public Mesure() {
	}
	
	/**
	 * Contructeur permetant de creer une nouvelle mesure.
	 * 
	 * @param piece piece ou a ete faite la mesure
	 * @param temp temperature
	 */
	public Mesure(String piece, double temp) {
		this.piece = piece;
		this.temp = temp;
		this.dateMesure =new Date();
	}

	public long getId() {
		return id;
	}
	public String getPiece() {
		return piece;
	}
	public void setPiece(String piece) {
		this.piece = piece;
	}
	public double getTemp() {
		return temp;
	}
	public void setTemp(double temp) {
		this.temp = temp;
	}
	public Date getDateMesure() {
		return dateMesure;
	}
	public void setDateMesure(Date dateMesure) {
		this.dateMesure = dateMesure;
	}
}
