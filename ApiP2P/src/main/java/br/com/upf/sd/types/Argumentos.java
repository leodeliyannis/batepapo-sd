package br.com.upf.sd.types;

public class Argumentos {
	
	private String modo;  //(Obrigatorio) Recebe ou conecta a alguem(r = recebe, c = conecta)
	private String ip;    //(Obrigatorio/Opcional) ip do cliente, opcional em caso 'modo = r'
	private int porta;    //(Obrigatorio) porta para conexao
	private boolean debug;//(Opcional) modo debug (true ou false), caso nao informado padrao e 'false'
	private Integer dh;   //(Opcional) tamanho de bits para criptografia DiffieHellman(1024 ou 2048), caso nao informado padrao e '1024', ara maior que o padrao usar Java 8
	private Integer aes;  //(Opcional) tamanho de bits para criptografia AES(128, 192 ou 256), caso nao informado padrao e '128', para valores maior que padrao precisa substituir o arquivo de autorizacao da JDK
	private String conexao;//(Obrigatorio) tipo da conexão, UDP ou TCP
	private UsuarioInput usuarioInput;
	private Token token;
	
	public String getModo() {
		return modo;
	}
	public void setModo(String modo) {
		this.modo = modo;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPorta() {
		return porta;
	}
	public void setPorta(int porta) {
		this.porta = porta;
	}
	public boolean getDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	public Integer getDh() {
		return dh;
	}
	public void setDh(Integer dh) {
		this.dh = dh;
	}
	public Integer getAes() {
		return aes;
	}
	public void setAes(Integer aes) {
		this.aes = aes;
	}
	public String getConexao() {
		return conexao;
	}
	public void setConexao(String conexao) {
		this.conexao = conexao;
	}
	public UsuarioInput getUsuarioInput() {
		return usuarioInput;
	}
	public void setUsuarioInput(UsuarioInput usuarioInput) {
		this.usuarioInput = usuarioInput;
	}
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aes == null) ? 0 : aes.hashCode());
		result = prime * result + ((conexao == null) ? 0 : conexao.hashCode());
		result = prime * result + (debug ? 1231 : 1237);
		result = prime * result + ((dh == null) ? 0 : dh.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((modo == null) ? 0 : modo.hashCode());
		result = prime * result + porta;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((usuarioInput == null) ? 0 : usuarioInput.hashCode());
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
		Argumentos other = (Argumentos) obj;
		if (aes == null) {
			if (other.aes != null)
				return false;
		} else if (!aes.equals(other.aes))
			return false;
		if (conexao == null) {
			if (other.conexao != null)
				return false;
		} else if (!conexao.equals(other.conexao))
			return false;
		if (debug != other.debug)
			return false;
		if (dh == null) {
			if (other.dh != null)
				return false;
		} else if (!dh.equals(other.dh))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (modo == null) {
			if (other.modo != null)
				return false;
		} else if (!modo.equals(other.modo))
			return false;
		if (porta != other.porta)
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (usuarioInput == null) {
			if (other.usuarioInput != null)
				return false;
		} else if (!usuarioInput.equals(other.usuarioInput))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Argumentos [modo=" + modo + ", ip=" + ip + ", porta=" + porta + ", debug=" + debug + ", dh=" + dh
				+ ", aes=" + aes + ", conexao=" + conexao + ", usuarioInput=" + usuarioInput + ", token=" + token + "]";
	}

}
