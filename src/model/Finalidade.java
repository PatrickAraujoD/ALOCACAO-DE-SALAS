/*
 * UNIVERSIDADE FEDERAL DO MARANHÃO
 * DICENTES: PATRICK CAMARA ARAUJO e VITOR GABRIEL RODRIGUES SOUSA
 */
package model;
import java.io.ObjectStreamException;
import java.io.Serializable;


public class Finalidade extends Solicitacao implements Serializable {
    //Classe reposavel por armazenar os dados de solicitação feita pelo arquivo quando for eventual, ela herda a classe Solicitacao
    private static final long serialVersionUID = 1L; // Número de versão para fins de serialização

    private String finalidade;
    private String dataInicio;
    private String dataFim;

    public Finalidade(String tipoSolicitacao, int ano, float semestre, String curso, int vagas, Horario horario, String finalidade, String dataInicio, String dataFim) {
        /* Construtor responsavel por inicializar a class Espaco
         * :param tipoSolicitacao: recebe o tipo de solicitação (fixa ou eventual)
         * :param ano: recebe o ano da solicitação
         * :param semestre: recebe o semestre da solicitação
         * :param curso: recebe o curso que está fazendo a solicitação
         * :param vagas: recebe a quantidade de vagas para aquela solicição
         * :param horario: recebe o horario daquela solicitação
         * :param finalidade: recebe a finalidade da solicitação
         */
        super(tipoSolicitacao,ano, semestre, curso, vagas, horario); //pega os elementos da classe pai
        this.finalidade = finalidade;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    @Override
    public String toString() {
        String ret = "";
        ret += "Tipo de Solicitacao: " + tipoSolicitacao;
        ret += " ; Ano: " + ano;
        ret += " ; Semestre: " + semestre;
        ret += " ; Curso: " + curso;
        ret += " ; Finalidade: " + finalidade;
        ret += " ; Vagas: " + vagas;
        ret += " ; Horario: " + horario;
        ret += " ; Data Inicio: " + dataInicio;
        ret += " ; Data Fim: " + dataFim;
        return ret;
    }


}
