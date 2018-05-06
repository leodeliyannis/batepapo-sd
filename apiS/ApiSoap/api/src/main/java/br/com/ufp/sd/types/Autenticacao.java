package br.com.ufp.sd.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.ufp.sd.utils.JaxbValidator;
import br.com.ufp.sd.utils.ValidationException;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autenticacaoServiceRequest", 
		 namespace = "http://br.com.upf.sd/ApiSoap")
public class Autenticacao {
	
	@XmlElement(required = true)
	private String Token;
	
	public String getToken() {
		return Token;
	}
	public void setToken(String token) {
		Token = token;
	}

	public void validateFields() throws ValidationException {
		// Bloco generico que realiza a validação a partir das annotations
		JaxbValidator.validateRequired(this,
				Autenticacao.class);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Token == null) ? 0 : Token.hashCode());
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
		Autenticacao other = (Autenticacao) obj;
		if (Token == null) {
			if (other.Token != null)
				return false;
		} else if (!Token.equals(other.Token))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Autenticacao [Token=" + Token + "]";
	}
}
