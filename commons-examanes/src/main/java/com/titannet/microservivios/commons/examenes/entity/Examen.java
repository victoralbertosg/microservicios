package com.titannet.microservivios.commons.examenes.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table (name="examenes")
public class Examen {

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;

private String nombre;

@Temporal(TemporalType.TIMESTAMP)
@Column(name="create_at")
private Date createAt;

@JsonIgnoreProperties(value= {"examen"},allowSetters=true)
@OneToMany(mappedBy="examen", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
private List<Pregunta> preguntas;

@PrePersist
public void prePersist() {
	this.createAt= new Date();
}


public Examen() {	
	this.preguntas =new ArrayList<> ();
}



public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public Date getCreateAt() {
	return createAt;
}

public void setCreateAt(Date createAt) {
	this.createAt = createAt;
}


public List<Pregunta> getPreguntas() {
	return preguntas;
}


public void setPreguntas(List<Pregunta> preguntas) {
	this.preguntas.clear();
	preguntas.forEach(this::addPregunta);
}	

public void addPregunta(Pregunta pregunta) {
	this.preguntas.add(pregunta);
	pregunta.setExamen(this);
}

public void removePregunta (Pregunta pregunta) {
	this.preguntas.remove(pregunta);
	pregunta.setExamen(null);	
}


@Override
public boolean equals(Object obj) {
	if(this==obj) {
		return true;
	}
	if (!(obj instanceof Examen)) {
		return false;
	}
	Examen a=(Examen)obj;
	
	
	return this.id !=null && this.id.equals(a.getId());
}



}
