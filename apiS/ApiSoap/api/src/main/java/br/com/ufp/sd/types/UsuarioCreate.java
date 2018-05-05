package br.com.ufp.sd.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UsuarioCreateServiceResponse", 
		 namespace = "http://br.com.upf.sd/ApiSoap")
public class UsuarioCreate {
	
	@XmlElement(required = true)
	String Nome;
	@XmlElement(required = true)
	String IPaddres;
	
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

		return true;
	}
	
	@Override
	public String toString() {
		return "UsuarioCreate [Nome=" + Nome + ", IPaddres=" + IPaddres + "]";
	}
	
}
