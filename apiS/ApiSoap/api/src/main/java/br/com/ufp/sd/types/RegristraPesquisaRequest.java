package br.com.ufp.sd.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.ufp.sd.utils.JaxbValidator;
import br.com.ufp.sd.utils.ValidationException;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "regristraPesquisaRequest", 
		 namespace = "http://br.com.upf.sd/ApiSoap")
public class RegristraPesquisaRequest {
	
	@XmlElement(required = true)
	private Autenticacao autenticacao;
	@XmlElement(required = true)
	private String id;
	@XmlElement(required = true)
	private String topico;
	
	public Autenticacao getAutenticacao() {
		return autenticacao;
	}
	public void setAutenticacao(Autenticacao autenticacao) {
		this.autenticacao = autenticacao;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTopico() {
		return topico;
	}
	public void setTopico(String topico) {
		this.topico = topico;
	}
	
	public void validateFields() throws ValidationException {
		// Bloco generico que realiza a validação a partir das annotations
		JaxbValidator.validateRequired(this,
				RegristraPesquisaRequest.class);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autenticacao == null) ? 0 : autenticacao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((topico == null) ? 0 : topico.hashCode());
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
		RegristraPesquisaRequest other = (RegristraPesquisaRequest) obj;
		if (autenticacao == null) {
			if (other.autenticacao != null)
				return false;
		} else if (!autenticacao.equals(other.autenticacao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (topico == null) {
			if (other.topico != null)
				return false;
		} else if (!topico.equals(other.topico))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "RegristraPesquisaRequest [autenticacao=" + autenticacao + ", id=" + id + ", topico=" + topico + "]";
	}

}
