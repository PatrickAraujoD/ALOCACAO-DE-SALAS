/*
 * UNIVERSIDADE FEDERAL DO MARANHÃO
 * DICENTES: PATRICK CAMARA ARAUJO e VITOR GABRIEL RODRIGUES SOUSA
 */

package model;


import java.io.Serializable;

public class Horario implements Serializable {

    // Classe responsavel por armazenar o horario que vem do arquivo
    private static final long serialVersionUID = 1L;
    private String dias; //armazena os dias (formato SIGAA)
    private String turno; //armazena os turnos (formato SIGAA)
    private String horarios; //armazena os horarios (formato SIGAA)


    public Horario(String dias, String turno, String horarios) {
        /* Construtor responsavel por inicializar a class horario
        * :param dias: recebe os dias em que irão fazer o pedido de sala
        * :param turno: recebe os turnos em que irão fazer o pedido de sala
        * :param horarios: recebe os horarios que irão fazer o pedido de sala
        */
        this.dias = dias;
        this.turno = turno;
        this.horarios = horarios;
    }

    //Metodos get
    public String getDias() {
        return dias;
    }

    public String getTurno() {
        return turno;
    }

    public String getHorarios() {
        return horarios;
    }

    @Override
    public String toString() {
        /* Sobrescreve o endereço da classe
        * :return: retorna uma string de acordo com o horario que foi fornecido
        * :rtype: String ret
        */

        String ret = "";
        if (turno.length() == 2) {
            //23T43 2N34
            ret += dias.substring(0, 2) + turno.charAt(0) + horarios.substring(0, 2) +
                    " " + dias.charAt(3) + turno.charAt(1) + horarios.substring(0, 2);
        } else if (turno.length() == 3 && horarios.length() >= 6) {
            //23T43 24N34
            ret += dias.substring(0, 2) + turno.substring(0, 2) + horarios.substring(0, 2) +
                    " " + dias.charAt(2) + turno.charAt(2) + horarios.substring(3, 5) +
                    " " + dias.charAt(3) + turno.charAt(1) + horarios.substring(4, 6);
        } else if (turno.length() == 4 && horarios.length() >= 6) {
            //2T43 2N34
            ret += dias.substring(0, 2) + turno.substring(0, 2) + horarios.substring(0, 2) +
                    " " + dias.charAt(3) + turno.charAt(2) + horarios.substring(3, 5) +
                    " " + dias.charAt(3) + turno.substring(3) + horarios.substring(5);
        } else {
            ret += dias + turno + horarios;
        }
        return ret;
    }

}
