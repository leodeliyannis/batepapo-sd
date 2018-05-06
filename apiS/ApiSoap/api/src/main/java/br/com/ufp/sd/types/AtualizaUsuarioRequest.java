package br.com.ufp.sd.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.ufp.sd.utils.JaxbValidator;
import br.com.ufp.sd.utils.ValidationException;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "atualizaUsuarioRequest", 
		 namespace = "http://br.com.upf.sd/ApiSoap")
public class AtualizaUsuarioRequest {
	
	@XmlElement(required = true)
	private Autenticacao autenticacao;
	@XmlElement(required = true)
	private UsuarioUpdate usuarioUpdate;
	
	public Autenticacao getAutenticacao() {
		return autenticacao;
	}
	public void setAutenticacao(Autenticacao autenticacao) {
		this.autenticacao = autenticacao;
	}
	public UsuarioUpdate getUsuarioUpdate() {
		return usuarioUpdate;
	}
	public void setUsuarioUpdate(UsuarioUpdate usuarioUpdate) {
		this.usuarioUpdate = usuarioUpdate;
	}
	
	public void validateFields() throws ValidationException {
		// Bloco generico que realiza a validação a partir das annotations
		JaxbValidator.validateRequired(this,
				AtualizaUsuarioRequest.class);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autenticacao == null) ? 0 : autenticacao.hashCode());
		result = prime * result + ((usuarioUpdate == null) ? 0 : usuarioUpdate.hashCode());
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
		AtualizaUsuarioRequest other = (AtualizaUsuarioRequest) obj;
		if (autenticacao == null) {
			if (other.autenticacao != null)
				return false;
		} else if (!autenticacao.equals(other.autenticacao))
			return false;
		if (usuarioUpdate == null) {
			if (other.usuarioUpdate != null)
				return false;
		} else if (!usuarioUpdate.equals(other.usuarioUpdate))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "UsuarioUpdateRequest [autenticacao=" + autenticacao + ", usuarioUpdate=" + usuarioUpdate + "]";
	}

}
