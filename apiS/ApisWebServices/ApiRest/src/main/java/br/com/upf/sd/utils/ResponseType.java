package br.com.upf.sd.utils;

public class ResponseType {
    
	public final static Integer STATUS_COD_OK = 0;
    public final static Integer STATUS_COD_VALIDATION = -1;
    public final static Integer STATUS_COD_EXPTION = -2;
    public final static String STATUS_MSG_OK = "Execução realizada com Sucesso!";
    public final static String STATUS_MSG_NOT_FOUND = "Nenhum Registro localizado!";
    
	private Integer cdRetorno;
    private String dsRetorno;

    public Integer getCdRetorno() {
        return cdRetorno;
    }

    public void setCdRetorno(Integer cdRetorno) {
        this.cdRetorno = cdRetorno;
    }

    public String getDsRetorno() {
        return dsRetorno;
    }

    public void setDsRetorno(String dsRetorno) {
        this.dsRetorno = dsRetorno;
    }
    
    public String toString(){
        return String.format("[cdRetorno=%s," +
            " dsRetorno=%s]", getCdRetorno(),
                              getDsRetorno());
    }
}
