package br.com.upf.sd.types;

import javax.validation.constraints.NotNull;

import br.com.upf.sd.utils.JaxbValidator;
import br.com.upf.sd.utils.ValidationException;

public class QuantidadeUsuarioLoginRequest {

	@NotNull
	private Autenticacao autenticacao;
	@NotNull
	private DataInicioFim datas;
	
	public Autenticacao getAutenticacao() {
		return autenticacao;
	}
	public void setAutenticacao(Autenticacao autenticacao) {
		this.autenticacao = autenticacao;
	}
	public DataInicioFim getDatas() {
		return datas;
	}
	public void setDatas(DataInicioFim datas) {
		this.datas = datas;
	}
	
	public void validateFields() throws ValidationException {
		// Bloco generico que realiza a validação a partir das annotations
		JaxbValidator.validateNotNull(this, QuantidadeUsuarioLoginRequest.class);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autenticacao == null) ? 0 : autenticacao.hashCode());
		result = prime * result + ((datas == null) ? 0 : datas.hashCode());
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
		QuantidadeUsuarioLoginRequest other = (QuantidadeUsuarioLoginRequest) obj;
		if (autenticacao == null) {
			if (other.autenticacao != null)
				return false;
		} else if (!autenticacao.equals(other.autenticacao))
			return false;
		if (datas == null) {
			if (other.datas != null)
				return false;
		} else if (!datas.equals(other.datas))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "QuantidadeUsuarioLoginRequest [autenticacao=" + autenticacao + ", datas=" + datas + "]";
	}
	
}
