/*
 * UNIVERSIDADE FEDERAL DO MARANHÃO
 * DICENTES: PATRICK CAMARA ARAUJO e VITOR GABRIEL RODRIGUES SOUSA
 */

package controller;

import java.io.*;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.*;


public class Departamento{
    // Clase responsavel por fazer o controle do sistema
    Hashtable<String, ArrayList<Solicitacao> > alocadosporespaco;
    Hashtable<String, ArrayList<Solicitacao> >alocadosporcurso;

    public Departamento(){
        /*
        *  Construtor responsavel por inicializar a classe departamento
        * */
        alocadosporespaco = new Hashtable<>();
        alocadosporcurso = new Hashtable<>();
    }

    // Metodos set
    public void setAlocadosporCurso(Hashtable<String, ArrayList<Solicitacao>> alocadosporcurso) {
        this.alocadosporcurso = alocadosporcurso;
    }
    public void setAlocadosporEspaco(Hashtable<String, ArrayList<Solicitacao>> alocadosporespaco){
        this.alocadosporespaco = alocadosporespaco;
    }

    // Metodos get
    public Hashtable<String, ArrayList<Solicitacao>> getAlocadosporcurso() {
        return alocadosporcurso;
    }
    public Hashtable<String, ArrayList<Solicitacao>> getAlocadosporespaco() {
        return alocadosporespaco;
    }

    public boolean adicionarSolicitacaoNoArquivo(String tiposSolicitacao,int ano, float semestre, String curso, int vagas, Horario horario, String escolha, String dataInicio, String dataFim){
        /*
        * Metodo responsavel por gravar o objeto no arquivo
        * :return: retorna true (verdadeiro) caso ele consiga executar o bloco do try ou false (falso) caso ele encontre alguma exeção
        * :rtype: boolean
        * */
        Solicitacao solicitacao = null;

        if (tiposSolicitacao.equals("EVENTUAL")) {
            solicitacao = new Finalidade(tiposSolicitacao.toUpperCase(), ano, semestre, curso, vagas, horario, escolha, dataInicio, dataFim);
        } else {
            solicitacao = new Disciplina(tiposSolicitacao.toUpperCase(),ano, semestre, curso, vagas, horario, escolha);
        }

        try{
            BufferedWriter escritor = new BufferedWriter (new FileWriter("solicitacao.txt", true));
            escritor.write(solicitacao.getTipoSolicitacao() + ";"+ solicitacao.getAno()+ ";" + solicitacao.getSemestre()+";"+ solicitacao.getCurso()+";");

            if(tiposSolicitacao.equals("EVENTUAL")){
                Finalidade finalidade = (Finalidade) solicitacao;
                escritor.write(finalidade.getFinalidade()+";"+solicitacao.getVagas()+";"+solicitacao.getHorario()+";"+finalidade.getDataInicio()+";"+finalidade.getDataFim());
            }else{
                Disciplina disciplina = (Disciplina) solicitacao;
                escritor.write(disciplina.getDisciplina()+";"+solicitacao.getVagas()+";"+solicitacao.getHorario());
            }

            // adiciona uma quebra de linha
            escritor.newLine();

            // fecha o arquivo
            escritor.close();
            return true;
        } catch (Exception e){
            return false;
        }
    }


    public boolean adicionarEspacoNoArquivo(String nome, int capacidade, String localizacao){
        /*
         * Metodo responsavel por gravar o espaco no arquivo
         * :return: retorna true (verdadeiro) caso ele consiga executar o bloco do try ou false (falso) caso ele encontre alguma exceção
         * :rtype: boolean
         */
        Espaco espaco;
        // Verifica se o espaco é uma sala ou auditorio, e inicializa o objeto de acordo com o nome
        if(nome.contains("AUDITÓRIO") || nome.contains("AUDITORIO")){
            espaco = new Auditorio(capacidade, nome, localizacao);
        } else{
            espaco = new Sala(capacidade, nome, localizacao);
        }

        try{
            BufferedWriter obj = new BufferedWriter(new FileWriter("Espacos.txt", true));
            obj.write(espaco.getNome() + ";" + espaco.getLocalizacao() + ";" + espaco.getCapacidade());

            // uma nova quebra de linha
            obj.newLine();

            // fecha o arquivo
            obj.close();

            return true;
        }catch(Exception e){
            return false;
        }
    }

    public ArrayList<Solicitacao> lerSolicitacao(){
        /*
         * Metodo responsavel por ler o arquivo e transformar em objetos
         * :return: retorna um ArrayList com todas as solicitações que estão no arquivo ou null caso exista alguma exceção
         * */

        ArrayList<Solicitacao> result = new ArrayList<>();
        try{
            BufferedReader leitor = new BufferedReader(new FileReader("solicitacao.txt"));
            String linha;
            while((linha = leitor.readLine()) != null) {
                Solicitacao solicitacao;
                // Dividindo a linha em valores usando ponto e vírgula como delimitador
                String[] valores = linha.split(";");
                String tipoSolicitacao = valores[0];
                int ano = Integer.parseInt(valores[1]);
                float semestre = Float.parseFloat(valores[2]);
                String curso = valores[3];
                int vagas = Integer.parseInt(valores[5]);
                // Extraindo informações de horário usando um método auxiliar
                Horario horario = dividirHorario(linha, 6);


                // Verificando o tipo de solicitação e criando o objeto correspondente
                if (tipoSolicitacao.equals("FIXA")) {
                    String disciplina = valores[4];
                    solicitacao = new Disciplina(tipoSolicitacao, ano, semestre, curso, vagas, horario, disciplina);
                } else {
                    String finalidade = valores[4];
                    String dataInicio = valores[7];
                    String dataFim = valores[8];
                    solicitacao = new Finalidade(tipoSolicitacao, ano, semestre, curso, vagas, horario, finalidade, dataInicio, dataFim);
                }

                result.add(solicitacao);

            }
        } catch (IOException e){
            return null;
        }
        return result;
    }

    public ArrayList<Espaco> lerEspaco(){
        /*
         * Metodo responsavel por ler o arquivo e transformar em objetos
         * :return: retorna um ArrayList com todos os espacos que estão no arquivo ou null caso exista alguma exceção
         * */

        // Método para ler dados de espaços de um arquivo e popular um ArrayList
        Espaco espaco;
        ArrayList<Espaco> resultEspa = new ArrayList<>();
        try{
            BufferedReader leitor = new BufferedReader(new FileReader("Espacos.txt"));
            String linha;
            while((linha = leitor.readLine()) != null){
                // Dividindo a linha em valores usando ponto e vírgula como delimitador
                String[] valores = linha.split(";");
                String nomeEspaco = valores[0];
                String localizacao = valores[1];
                int capacidade = Integer.parseInt(valores[2]);


                if(nomeEspaco.contains("AUDITORIO") || nomeEspaco.contains("AUDITÓRIO")){
                    espaco = new Auditorio(capacidade, nomeEspaco, localizacao);
                } else {
                    espaco = new Sala(capacidade, nomeEspaco, localizacao);
                }

                resultEspa.add(espaco);
            }
        } catch(IOException e){
            return null;
        }
        return resultEspa;
    }

    public Horario dividirHorario(String linha, int posicao) {
        /*
         * Metodo responsavel por dividir o horario que vem do arquivo e tranforma em um Objeto horario
         * :return: retorna o objeto Horario
         * */

        // Dividindo a linha em valores usando ponto e vírgula como delimitador
        String[] valores = linha.split(";");

        // Obtendo a parte correspondente ao horário a partir da posição especificada
        String valores1 = valores[posicao];

        // Dividindo a parte do horário em partes separadas por espaço
        String[] parte = valores1.split(" ");

        String dias = "";
        String horarios = "";

        // Iterando sobre as partes do horário
        for (int i = 0; i < parte.length; i++) {
            // Dividindo cada parte do horário em dias e horários usando [TNM] como delimitador
            String[] parteHorario = parte[i].split("[TNM]");
            dias += parteHorario[0];
            horarios += parteHorario[1];
        }

        String turnos = "";

        // Iterando sobre os caracteres da parte do horário
        for (int i = 0; i < valores1.length(); i++) {
            String s = valores1.substring(i, i + 1);
            // Verificando se o caractere representa um turno (T, M, N)
            if (s.equals("T") || s.equals("M") || s.equals("N")) {
                turnos += s;
            }
        }

        // Construindo e retornando o objeto Horario
        Horario h = new Horario(dias, turnos, horarios);
        return h;
    }

    public ArrayList<Espaco> listarSalaCompativel(Solicitacao solicitacao, ArrayList<Espaco> espacos){
        /*
         * Metodo responsavel listar as salas compativeis, ou seja, sem conflito e com requisitos passados no trabalho
         * :return: retorna um ArrayList com todos os espacos que são compativeis com a solicitação
         * */

        ArrayList<Espaco> resultados = new ArrayList<>();
        boolean eEventual = false;

        if(solicitacao.getTipoSolicitacao().equals("EVENTUAL")){
            eEventual = true;
        }

        for(Espaco e : espacos){
            if(!verificarConflito(solicitacao, e.getNome())){
                if(eEventual){
                    if (e.getNome().contains("AUDITORIO") || e.getNome().contains("AUDITÓRIO")){
                        resultados.add(e);
                    }
                } else {
                    resultados.add(e);
                }
            }
        }
        return resultados;
    }

    private boolean verificarConflito(Solicitacao s, String nomeEspaco){
        /*
        * Metodo responsavel por verificar o conflito de horarios
        * :return: Boolean
        * */
        ArrayList<Solicitacao> resultadoespaco = alocadosporespaco.get(nomeEspaco);
        if(resultadoespaco != null) {
            // Percorre o ArrayList passado no escopo do metodo
            for (Solicitacao soli : resultadoespaco) {
                //Verifica se tem conflito dos dias
                if (containsIn(soli.getHorario().getDias(), s.getHorario().getDias())) {
                    //Verifica se tem conflito dos turnos
                    if (containsIn(soli.getHorario().getTurno(), s.getHorario().getTurno())) {
                        //Verifica se tem conflito dos horarios
                        if (containsIn(soli.getHorario().getHorarios(), s.getHorario().getHorarios())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean containsIn(String diasSolicitacao, String diasS) {
        /*
        * Metodo que retorna verdadeiro quando pelo menos um charactere é igual
        * */
        for(int i = 0; i < diasSolicitacao.length(); i++){
            // pega o char dos diasSolicitacao na posição
            char soliDias = diasSolicitacao.charAt(i);
            for(int j = 0; j < diasS.length(); j++){
                // pega o char dos diasS na posição
                char SoliciDias = diasS.charAt(j);
                if(soliDias == SoliciDias){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean alocarEspaco(Solicitacao solicitacao, String espaco,String curso) {
        // Método para alocar uma solicitação em um espaço específico

        // Obtendo a lista de solicitações já alocadas para a sala ou curso especifico
        ArrayList<Solicitacao> resultadocurso = alocadosporcurso.get(curso);
        ArrayList<Solicitacao> resultadoespaco = alocadosporespaco.get(espaco);

        if (resultadoespaco == null) {
            // Inicializando a resultadoespaco se ainda não existir
            resultadoespaco = new ArrayList<>();
            alocadosporespaco.put(espaco, resultadoespaco);
        }

        if (resultadocurso == null) {
            // Inicializando a resultadocurso se ainda não existir
            resultadocurso = new ArrayList<>();
            alocadosporcurso.put(curso, resultadocurso);

        }


        if(verificarConflito(solicitacao, espaco)){
            return false;
        }

        if(solicitacao.getTipoSolicitacao().equals("EVENTUAL")){
            // Se for uma solicitação eventual, verifica se é para um auditório
            if(espaco.contains("auditorio") || espaco.contains("AUDITORIO")){
                // Adiciona a solicitação à lista de alocados e retorna verdadeiro
                resultadoespaco.add(solicitacao);
                alocadosporespaco.put(espaco, resultadoespaco);
                resultadocurso.add(solicitacao);
                alocadosporcurso.put(curso, resultadocurso);

                return true;
            } else {
                // Retorna falso se for uma solicitação eventual para uma sala
                return false;
            }
        } else {
            // Se não for uma solicitação eventual, adiciona à lista de alocados e retorna verdadeiro
            resultadoespaco.add(solicitacao);
            alocadosporespaco.put(espaco, resultadoespaco);
            resultadocurso.add(solicitacao);
            alocadosporcurso.put(curso, resultadocurso);
            return true;
        }
    }

    public void removerSolicitacaoDoArquivo(ArrayList<Solicitacao> solicitacoes) {
        try {
            BufferedWriter escritor = new BufferedWriter(new FileWriter("solicitacao.txt"));

            for(Solicitacao s: solicitacoes) {
                if(s.getTipoSolicitacao().equals("FIXA")) {
                    Disciplina disciplina = (Disciplina) s;
                    escritor.write(s.getTipoSolicitacao() + ";" + s.getAno() + ";" + s.getSemestre() + ";" + s.getCurso() + ";" + disciplina.getDisciplina() + ";" + s.getVagas() + ";" + s.getHorario());
                } else {
                    Finalidade finalidade = (Finalidade) s;
                    escritor.write(s.getTipoSolicitacao() + ";" + s.getAno() + ";" + s.getSemestre() + ";" + s.getCurso() + ";" + finalidade.getFinalidade() + ";" + s.getVagas() + ";" + s.getHorario() + ";" + finalidade.getDataInicio() + ";" + finalidade.getDataFim());
                }

                // Adiciona uma quebra de linha
                escritor.newLine();
            }

            escritor.close();
        } catch (Exception e) {
            return ;
        }
    }

    public void escreverMapaAlocadoPorEspaco() {
        try {
            // Lendo o conteúdo do arquivo original
            HashMap<String,  ArrayList<Solicitacao>> mapaOriginal = lerMapaAlocadoporEspaco();

            // Verificando se o mapaOriginal é nulo
            if (mapaOriginal == null) {
                mapaOriginal = new HashMap<>();
            }

            // Criando um arquivo temporário
            File arquivoTemporario = new File("MapaAlocadosPorEspaco_temp.txt");

            // Criando um ObjectOutputStream para escrever objetos no arquivo temporário
            ObjectOutputStream escritorTemp = new ObjectOutputStream(new FileOutputStream(arquivoTemporario));

            // Escrevendo o mapa de alocados por espaço no arquivo temporário
            escritorTemp.writeObject(getAlocadosporespaco());

            // Fechando o ObjectOutputStream do arquivo temporário
            escritorTemp.close();

            // Mesclando os dados do arquivo temporário com os dados originais
            mapaOriginal.putAll(getAlocadosporespaco());

            // Criando um ObjectOutputStream para escrever objetos no arquivo original
            ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream("MapaAlocadosPorEspaco.txt"));

            // Escrevendo o mapa mesclado no arquivo original
            escritor.writeObject(mapaOriginal);

            // Fechando o ObjectOutputStream do arquivo original
            escritor.close();

            // Excluindo o arquivo temporário
            arquivoTemporario.delete();

        } catch (IOException e) {
           return;
        }
    }


    public void escreverMapaAlocadoPorCurso() {
        try {
            // Lendo o conteúdo do arquivo original
            HashMap<String,  ArrayList<Solicitacao>> mapaOriginal = lerMapaAlocadoporCurso();

            // Verificando se o mapaOriginal é nulo
            if (mapaOriginal == null) {
                mapaOriginal = new HashMap<>();
            }

            // Criando um arquivo temporário
            File arquivoTemporario = new File("MapaAlocadosPorCurso_temp.txt");

            // Criando um ObjectOutputStream para escrever objetos no arquivo temporário
            ObjectOutputStream escritorTemp = new ObjectOutputStream(new FileOutputStream(arquivoTemporario));

            // Escrevendo o mapa de alocados por curso no arquivo temporário
            escritorTemp.writeObject(getAlocadosporcurso());

            // Fechando o ObjectOutputStream do arquivo temporário
            escritorTemp.close();

            // Mesclando os dados do arquivo temporário com os dados originais
            mapaOriginal.putAll(getAlocadosporcurso());

            // Criando um ObjectOutputStream para escrever objetos no arquivo original
            ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream("MapaAlocadosPorCurso.txt"));

            // Escrevendo o mapa mesclado no arquivo original
            escritor.writeObject(mapaOriginal);

            // Fechando o ObjectOutputStream do arquivo original
            escritor.close();

            // Excluindo o arquivo temporário
            arquivoTemporario.delete();

        } catch (IOException e) {
            return ;
        }
    }


    public HashMap<String, ArrayList<Solicitacao>> lerMapaAlocadoporCurso() {
        try {
            ObjectInputStream leitor = new ObjectInputStream(new FileInputStream("MapaAlocadosPorCurso.txt"));

            // Passa oq tem no arquivo para o result, que é um HashMap
            HashMap<String, ArrayList<Solicitacao>> result = (HashMap<String, ArrayList<Solicitacao>>) leitor.readObject();

            leitor.close();

            return result;

        } catch(Exception e) {
            return null;
        }
    }
    public  HashMap<String, ArrayList<Solicitacao>> lerMapaAlocadoporEspaco() {
        try {
            // Criando um ObjectInputStream para ler objetos do arquivo "MapaAlocados.txt"
            ObjectInputStream leitor = new ObjectInputStream(new FileInputStream("MapaAlocadosPorEspaco.txt"));

            // Lendo o objeto do arquivo (neste caso, um HashMap<String, ArrayList<Solicitacao>>)
            HashMap<String, ArrayList<Solicitacao>> result = (HashMap<String, ArrayList<Solicitacao>>)leitor.readObject();

            // Fechando o ObjectInputStream
            leitor.close();

            // Retornando o HashMap lido do arquivo
            return result;

        } catch(Exception e) {
            return null;
        }
    }


    public void limparMapasAlocadoporEspacoeCurso() {
        try {
            // Exclui o arquivo do mapa alocado por espaço
            File arquivoEspaco = new File("MapaAlocadosPorEspaco.txt");
            if (arquivoEspaco.exists()) {
                arquivoEspaco.delete();
            }

            // Exclui o arquivo do mapa alocado por curso
            File arquivoCurso = new File("MapaAlocadosPorCurso.txt");
            if (arquivoCurso.exists()) {
                arquivoCurso.delete();
            }

            // Limpa a Hashtable alocadosporespaco
            alocadosporespaco.clear();

            // Limpa a Hashtable alocadosporcurso
            alocadosporcurso.clear();
        } catch (Exception e) {
            return ;
        }
    }

}