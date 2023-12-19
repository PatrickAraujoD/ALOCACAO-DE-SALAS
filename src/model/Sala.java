/*
 * UNIVERSIDADE FEDERAL DO MARANHÃO
 * DICENTES: PATRICK CAMARA ARAUJO e VITOR GABRIEL RODRIGUES SOUSA
 */

package model;

import java.io.Serializable;

public class Sala extends Espaco implements Serializable {
    /* Classe responsavel por armazenar as sala que vem do documento, essa classe herda a Classe Espaco*/

    private static final long serialVersionUID = 1L;
    public Sala(int capacidade, String nome, String localizacao) {
        /* Construtor responsavel por inicializar a class Sala
         * :param capacidade: recebe a capacidade  do local
         * :param nome: recebe o nome do local
         * :param localização: recebe a localização do local
         * :super: utilizado para atribuir elementos da classe pai para a classe filha
         */
        super(capacidade, nome, localizacao);
    }

    @Override
    public String toString(){
        /* Sobrescreve o endereço da classe
         * :return: retorna os elementos da classe separados por ";"
         */
        return nome+";"+localizacao+";"+capacidade;
    }
}
