package br.com.upf.sd.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.upf.sd.utils.JaxbValidator;
import br.com.upf.sd.utils.ValidationException;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "loginRequest", 
		 namespace = "http://br.com.upf.sd/ApiSoap")
public class LoginRequest {
	
	@XmlElement(required = true)
	private String Nome;
	@XmlElement(required = true)
	private String Senha;
	@XmlElement(required = true)
	private String IPaddres;
	
	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public String getSenha() {
		return Senha;
	}

	public void setSenha(String senha) {
		Senha = senha;
	}

	public String getIPaddres() {
		return IPaddres;
	}

	public void setIPaddres(String iPaddres) {
		IPaddres = iPaddres;
	}

	public void validateFields() throws ValidationException {
		// Bloco generico que realiza a validação a partir das annotations
		JaxbValidator.validateRequired(this,
				LoginRequest.class);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((IPaddres == null) ? 0 : IPaddres.hashCode());
		result = prime * result + ((Nome == null) ? 0 : Nome.hashCode());
		result = prime * result + ((Senha == null) ? 0 : Senha.hashCode());
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
		LoginRequest other = (LoginRequest) obj;
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
		if (Senha == null) {
			if (other.Senha != null)
				return false;
		} else if (!Senha.equals(other.Senha))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LoginRequest [Nome=" + Nome + ", Senha=" + Senha + ", IPaddres=" + IPaddres + "]";
	}
	
}
