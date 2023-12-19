/*
 * UNIVERSIDADE FEDERAL DO MARANHÃO
 * DICENTES: PATRICK CAMARA ARAUJO e VITOR GABRIEL RODRIGUES SOUSA
 */

package model;
import java.io.ObjectStreamException;
import java.io.Serializable;


public class Disciplina extends Solicitacao implements Serializable {

    //Classe reposavel por armazenar os dados de solicitação feita pelo arquivo quando for fixa, ela herda a classe Solicitacao

    private static final long serialVersionUID = 1L; // Número de versão para fins de serialização
    private String disciplina;

    public Disciplina(String tipoSolicitacao, int ano, float semestre, String curso, int vagas, Horario horario, String disciplina) {
        /* Construtor responsavel por inicializar a class Espaco
         * :param tipoSolicitacao: recebe o tipo de solicitação (fixa ou eventual)
         * :param ano: recebe o ano da solicitação
         * :param semestre: recebe o semestre da solicitação
         * :param curso: recebe o curso que está fazendo a solicitação
         * :param vagas: recebe a quantidade de vagas para aquela solicição
         * :param horario: recebe o horario daquela solicitação
         * :param Disciplina: recebe a disciplina que está fazendo a solicitação
         */
        super(tipoSolicitacao, ano, semestre, curso, vagas, horario); // pega os elementos da classe pai
        this.disciplina = disciplina;
    }

    //Metodos get
    public String getDisciplina() {
        return disciplina;
    }

    @Override
    public String toString(){
        String ret = "";
        ret += "Tipo de Solicitacao: " + tipoSolicitacao;
        ret += " ; Ano: " + ano;
        ret += " ; Semestre: " + semestre;
        ret += " ; Curso: " + curso;
        ret += " ; Disciplina: " + disciplina;
        ret += " ; Vagas: " + vagas;
        ret += " ; Horario: " + horario;
        return ret;
    }

}