package br.com.upf.sd.types;

import javax.validation.constraints.NotNull;

import br.com.upf.sd.utils.JaxbValidator;
import br.com.upf.sd.utils.ValidationException;

public class LoginRequest {
	
	@NotNull
	private String nome;
	@NotNull
	private String senha;
	@NotNull
	private String ipAddres;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getIpAddres() {
		return ipAddres;
	}
	public void setIpAddres(String ipAddres) {
		this.ipAddres = ipAddres;
	}
	
	public void validateFields() throws ValidationException {
		// Bloco generico que realiza a validação a partir das annotations
		JaxbValidator.validateNotNull(this, LoginRequest.class);
	}
	
	@Override
	public String toString() {
		return "LoginRequest [nome=" + nome + ", senha=" + senha
				+ ", ipAddres=" + ipAddres + "]";
	}

}
