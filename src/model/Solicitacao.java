/*
 * UNIVERSIDADE FEDERAL DO MARANHÃO
 * DICENTES: PATRICK CAMARA ARAUJO e VITOR GABRIEL RODRIGUES SOUSA
 */

package model;

import java.io.Serializable;

public class Solicitacao implements Serializable {
    //Classe reposavel por armazenar os dados de solicitação feita pelo arquivo
    private static final long serialVersionUID = 1L;
    protected String tipoSolicitacao;
    protected int ano;
    protected float semestre;
    protected String curso;
    protected int vagas;
    protected Horario horario;

    public Solicitacao(String tipoSolicitacao, int ano, float semestre, String curso, int vagas, Horario horario) {
        /* Construtor responsavel por inicializar a class Espaco
         * :param tipoSolicitacao: recebe o tipo de solicitação (fixa ou eventual)
         * :param ano: recebe o ano da solicitação
         * :param semestre: recebe o semestre da solicitação
         * :param curso: recebe o curso que está fazendo a solicitação
         * :param vagas: recebe a quantidade de vagas para aquela solicição
         * :param horario: recebe o horario daquela solicitação
         */
        this.tipoSolicitacao = tipoSolicitacao;
        this.ano = ano;
        this.semestre = semestre;
        this.curso = curso;
        this.vagas = vagas;
        this.horario = horario;
    }

    //Metodos get, serve para obter os dados da solicitação desejada
    public int getAno() {
        return ano;
    }

    public float getSemestre() {
        return semestre;
    }

    public String getCurso() {
        return curso;
    }

    public int getVagas() {
        return vagas;
    }

    public Horario getHorario() {
        return horario;
    }

    public String getTipoSolicitacao() {
        return tipoSolicitacao;
    }

    @Override
    public String toString(){
        String ret = "";
        ret += "Tipo de Solicitacao: " + tipoSolicitacao;
        ret += "\nAno: " + ano;
        ret += "\nSemestre: " + semestre;
        ret += "\nCurso: " + curso;
        ret += "\nVagas: " + vagas;
        ret += "\nHorario" + horario;
        return ret;
    }
}
