package br.com.ufp.sd.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UsuarioUpdateServiceResponse", 
		 namespace = "http://br.com.upf.sd/ApiSoap")
public class UsuarioUpdate {
	
	@XmlElement(required = false)
	String id;
	@XmlElement(required = true)
	String Nome;
	@XmlElement(required = true)
	String IPaddres;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public String getIPaddres() {
		return IPaddres;
	}
	public void setIPaddres(String iPaddres) {
		IPaddres = iPaddres;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((IPaddres == null) ? 0 : IPaddres.hashCode());
		result = prime * result + ((Nome == null) ? 0 : Nome.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioUpdate other = (UsuarioUpdate) obj;
		if (IPaddres == null) {
			if (other.IPaddres != null)
				return false;
		} else if (!IPaddres.equals(other.IPaddres))
			return false;
		if (Nome == null) {
			if (other.Nome != null)
				return false;
		} else if (!Nome.equals(other.Nome))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", Nome=" + Nome + ", IPaddres=" + IPaddres + "]";
	}
}
