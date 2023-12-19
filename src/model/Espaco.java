/*
 * UNIVERSIDADE FEDERAL DO MARANHÃO
 * DICENTES: PATRICK CAMARA ARAUJO e VITOR GABRIEL RODRIGUES SOUSA
 */

package model;

import java.io.Serializable;

public class Espaco implements Serializable {
    /* Classe responsavel por armazenar os espaços que vem do documento
    * :int capacidade: armazena a capacidade do espaço
    * :String nome: armazena o nome do local
    * :String localização: armazena a localização do local
    */
    private static final long serialVersionUID = 1L;
    protected int capacidade;
    protected String nome;
    protected String localizacao;

    public Espaco(int capacidade, String nome, String localizacao) {
        /* Construtor responsavel por inicializar a class Espaco
         * :param capacidade: recebe a capacidade  do local
         * :param nome: recebe o nome do local
         * :param localização: recebe a localização do local
         */
        this.capacidade = capacidade;
        this.nome = nome;
        this.localizacao = localizacao;
    }

    //Metodos get
    public int getCapacidade() {
        return capacidade;
    }

    public String getNome() {
        return nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    @Override
    public String toString(){
        /* Sobrescreve o endereço da classe
         * :return: retorna os elementos da classe separados por ";"
         */
        return nome+";"+localizacao+";"+capacidade;
    }
}
