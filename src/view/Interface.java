package view;
import controller.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Interface extends JFrame {

    private Departamento departamento;
    private ArrayList<Espaco> espacos;
    private ArrayList<Solicitacao> solicitacoes;
    private JPanel panel;

    public Interface() {

        departamento = new Departamento();
        espacos = departamento.lerEspaco();
        solicitacoes = departamento.lerSolicitacao();

        // Configurações da janela
        setTitle("Sistema de Alocação de Espaços");
        setSize(300, 500); //  tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Painel principal
        panel = new JPanel(new GridBagLayout()); //  layout para GridBagLayout
        add(panel);

        // Configuração de layout para centralizar os botões
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0; // Peso vertical para ocupar o espaço disponível
        gbc.insets = new Insets(5, 0, 5, 0); // Adiciona espaçamento entre os botões

        // Botões de ação
        JButton adicionarEspacoButton = criarBotao("Adicionar Espaços");
        JButton adicionarSolicitacaoButton = criarBotao("Adicionar Solicitações");
        JButton alocarEspacoButton = criarBotao("Alocar Espaço");
        JButton lerRelatorioButton = criarBotao("Ler Relatório");
        JButton limparRelatorioButton = criarBotao("Limpar Relatório");
        JButton sairButton = criarBotao("Sair");

        // Adiciona ação aos botões
        adicionarEspacoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarEspacos();
            }
        });

        adicionarSolicitacaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarSolicitacoes();
            }
        });

        alocarEspacoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alocarSolicitacao();
            }
        });


        lerRelatorioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lerRelatorio();
            }
        });

        limparRelatorioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparMapasAlocados();
                JOptionPane.showMessageDialog(panel, "Relatórios limpos com sucesso!");
            }
        });
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Adiciona botões ao painel principal
        panel.add(adicionarEspacoButton);
        panel.add(adicionarSolicitacaoButton);
        panel.add(alocarEspacoButton);
        panel.add(lerRelatorioButton);
        panel.add(limparRelatorioButton);
        panel.add(sairButton);

        panel.add(adicionarEspacoButton, gbc);
        gbc.gridy++;
        panel.add(adicionarSolicitacaoButton, gbc);
        gbc.gridy++;
        panel.add(alocarEspacoButton, gbc);
        gbc.gridy++;
        panel.add(lerRelatorioButton, gbc);
        gbc.gridy++;
        panel.add(limparRelatorioButton, gbc);
        gbc.gridy++;
        panel.add(sairButton, gbc);

        // Centraliza a janela na tela
        setLocationRelativeTo(null);


    }


    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(220, 50)); // tamanho dos botões
        return botao;
    }
    private void adicionarEspacos() {
        try {
            String nomeEspaco = JOptionPane.showInputDialog("Digite o nome do espaço:").toUpperCase();
            String localizacaoEspaco = JOptionPane.showInputDialog("Digite a localização do espaço:");
            int capacidadeEspaco = Integer.parseInt(JOptionPane.showInputDialog("Digite a capacidade do espaço:"));

            if(departamento.adicionarEspacoNoArquivo(nomeEspaco, capacidadeEspaco, localizacaoEspaco)){
                JOptionPane.showMessageDialog(panel, "Espaço adcionado com sucesso!");
            }

            espacos = departamento.lerEspaco();
        } catch (Exception e){
            JOptionPane.showMessageDialog(panel, "Precisa digitar algo.", "Alerta", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void adicionarSolicitacoes() {
        try {
            int EventualOuFixa = Integer.parseInt(JOptionPane.showInputDialog("Digite o tipo da solicitação (1 - FIXA | 2 - EVENTUAL):"));
            if(EventualOuFixa > 2 || EventualOuFixa < 1){
                JOptionPane.showMessageDialog(panel, "Opção inválida");
                return ;
            }
            int ano = Integer.parseInt(JOptionPane.showInputDialog("Digite o ano da solicitação:"));
            float semestre = Float.parseFloat(JOptionPane.showInputDialog("Digite o semestre da solicitação:"));
            String curso = JOptionPane.showInputDialog("Digite o curso da solicitação:");
            int vagas = Integer.parseInt(JOptionPane.showInputDialog("Digite as vagas da solicitação:"));
            String escolha = null;
            String diasSolicitacao = JOptionPane.showInputDialog("Digite os dias da solicitação no formato sigaa:");
            String turnoSolicitacao = JOptionPane.showInputDialog("Digite os turnos da solicitação no formato sigaa:");
            if(!turnoSolicitacao.equals("T")  || !turnoSolicitacao.equals("N") || !turnoSolicitacao.equals("M")){
                JOptionPane.showMessageDialog(panel, "Não existe esse turno");
                return ;
            }
            String horarioSolicitacao = JOptionPane.showInputDialog("Digite os horários da solicitação no formato sigaa:");

            Horario h = new Horario(diasSolicitacao, turnoSolicitacao, horarioSolicitacao);

            String dataInicio = null;
            String dataFim = null;
            String tipoSolicitacao = null;
            if (EventualOuFixa == 1) {
                tipoSolicitacao = "FIXA";
                escolha=JOptionPane.showInputDialog("Digite a disciplina:");
            } else if (EventualOuFixa == 2) {
                tipoSolicitacao = "EVENTUAL";
                escolha=JOptionPane.showInputDialog("Digite a  finalidade:");
                dataInicio = JOptionPane.showInputDialog("Digite o dia de inicio");
                dataFim = JOptionPane.showInputDialog("Digite o dia do fim");
            }

            if (tipoSolicitacao != null) {
                if(departamento.adicionarSolicitacaoNoArquivo(tipoSolicitacao, ano, semestre, curso, vagas, h, escolha, dataInicio, dataFim)){
                    JOptionPane.showMessageDialog(panel, "Solicitação adcionada com sucesso!");
                }
            }

            solicitacoes = departamento.lerSolicitacao();
        } catch (Exception e){
            JOptionPane.showMessageDialog(panel, "Precisa digitar algo.", "Alerta", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void alocarSolicitacao() {
        try {
            if (this.espacos != null && this.solicitacoes != null) {
                if (solicitacoes.toArray().length > 0) {
                    StringBuilder solicitacoesDisponiveis = new StringBuilder("Lista de Solicitações Disponíveis para Alocar:\n");
                    for (int i = 0; i < solicitacoes.size(); i++) {
                        solicitacoesDisponiveis.append(i).append(" - ").append(solicitacoes.get(i)).append("\n");
                    }

                    int numeroSolicitacao = -1; // Inicializar com valor inválido

                    // Utilizar um loop para garantir que o número da solicitação seja válido
                    while (numeroSolicitacao < 0 || numeroSolicitacao >= solicitacoes.size()) {
                        String input = JOptionPane.showInputDialog(solicitacoesDisponiveis.toString() +
                                "Digite o número da solicitação que deseja alocar:");

                        // Verificar se o usuário cancelou a entrada
                        if (input == null) {
                            return;
                        }

                        // Tentar converter a entrada para um número inteiro
                        try {
                            numeroSolicitacao = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            // Se a conversão falhar, continuar no loop
                        }
                    }

                    Solicitacao solicitacaoEscolhida = solicitacoes.get(numeroSolicitacao);
                    String nomeCurso = solicitacaoEscolhida.getCurso();

                    ArrayList<Espaco> result = departamento.listarSalaCompativel(solicitacaoEscolhida, espacos);

                    StringBuilder espacosDisponiveis = new StringBuilder("Espaços Disponíveis para Alocação:\n");

                    for(int i = 0; i < result.size(); i++){
                        espacosDisponiveis.append(i).append(" - ").append(result.get(i).getNome()).append("\n");
                    }
                    int numeroEspaco = -1; // Inicializar com valor inválido

                    // Utilizar um loop para garantir que o número do espaço seja válido
                    while (numeroEspaco < 0 || numeroEspaco >= result.size()) {
                        String input = JOptionPane.showInputDialog(espacosDisponiveis.toString() +
                                "Digite o número do espaço que deseja alocar:");

                        // Verificar se o usuário cancelou a entrada
                        if (input == null) {
                            return;
                        }

                        // Tentar converter a entrada para um número inteiro
                        try {
                            numeroEspaco = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            // Se a conversão falhar, continuar no loop
                        }
                    }

                    Espaco espacoEscolhido = result.get(numeroEspaco);

                    if (departamento.alocarEspaco(solicitacaoEscolhida, espacoEscolhido.getNome(), solicitacaoEscolhida.getCurso())) {
                        JOptionPane.showMessageDialog(panel, "Alocação bem sucedida");
                        solicitacoes.remove(numeroSolicitacao);
                        departamento.escreverMapaAlocadoPorCurso();
                        departamento.escreverMapaAlocadoPorEspaco();
                        departamento.removerSolicitacaoDoArquivo(solicitacoes);
                    } else {
                        JOptionPane.showMessageDialog(panel, "Não foi possivel fazer alocação. Conflito de horario ou espaco inválido com o tipo de solicitacao");
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Não tem solicitações disponiveis", "Alerta", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Ocorreu um erro. Certifique-se de inserir dados válidos.", "Alerta", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void lerRelatorio() {
        // Criar um JDialog para exibir opções ao usuário
        JDialog dialog = new JDialog(this, "Escolha do Relatório", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new FlowLayout());

        // Adicionar botões de opção ao diálogo
        JRadioButton cursoRadioButton = new JRadioButton("Por Curso");
        JRadioButton espacoFisicoRadioButton = new JRadioButton("Por Espaço Físico");
        ButtonGroup botaoGrupo = new ButtonGroup();
        botaoGrupo.add(cursoRadioButton);
        botaoGrupo.add(espacoFisicoRadioButton);
        JButton confirmarButton = new JButton("Confirmar");

        // Adicionar ActionListener aos botões de opção
        cursoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para relatório por curso
            }
        });

        espacoFisicoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para relatório por espaço físico
            }
        });

        // Adicionar ActionListener ao botão de confirmação
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verificar qual botão de opção está selecionado e gerar o relatório correspondente
                if (cursoRadioButton.isSelected()) {
                    gerarRelatorioPorCurso();
                } else if (espacoFisicoRadioButton.isSelected()) {
                    gerarRelatorioPorEspacoFisico();
                }

                // Fechar o diálogo após a confirmação
                dialog.dispose();
            }
        });

        // Adicionar botões ao diálogo
        dialog.add(cursoRadioButton);
        dialog.add(espacoFisicoRadioButton);
        dialog.add(confirmarButton);

        // Centralizar o diálogo na tela principal
        dialog.setLocationRelativeTo(this);

        // Tornar o diálogo visível
        dialog.setVisible(true);
    }

    private void gerarRelatorioPorCurso() {
        try {
            HashMap<String, ArrayList<Solicitacao>> mapaAlocacaoPorCurso = departamento.lerMapaAlocadoporCurso();

            if (mapaAlocacaoPorCurso != null) {
                StringBuilder relatoriocurso = new StringBuilder();

                for (Map.Entry<String, ArrayList<Solicitacao>> entry : mapaAlocacaoPorCurso.entrySet()) {
                    String curso = entry.getKey();
                    ArrayList<Solicitacao> listaSolicitacoesCurso = entry.getValue();

                    relatoriocurso.append("Curso: ").append(curso).append("\n");
                    for (Solicitacao solicitacao : listaSolicitacoesCurso) {
                        relatoriocurso.append("   - ").append(solicitacao).append("\n");
                    }
                }

                JOptionPane.showMessageDialog(panel, relatoriocurso.toString());
            } else {
                // Exibe uma mensagem caso a leitura não seja bem-sucedida
                JOptionPane.showMessageDialog(panel, "Erro ao ler o mapa alocado por curso.");
            }

        } catch (Exception e) {
            // Exibe uma mensagem caso ocorra uma exceção durante o processo
            JOptionPane.showMessageDialog(panel, "Erro ao exibir o relatório: " + e.getMessage());
        }
    }

    private void gerarRelatorioPorEspacoFisico() {
        try {
            HashMap<String, ArrayList<Solicitacao>> mapaAlocacaoPorEspaco = departamento.lerMapaAlocadoporEspaco();

            if (mapaAlocacaoPorEspaco != null) {
                StringBuilder relatorioespaco = new StringBuilder();

                for (Map.Entry<String, ArrayList<Solicitacao>> entry : mapaAlocacaoPorEspaco.entrySet()) {
                    String espaco = entry.getKey();
                    ArrayList<Solicitacao> listaSolicitacoesEspaco = entry.getValue();

                    relatorioespaco.append("Espaço: ").append(espaco).append("\n");
                    for (Solicitacao solicitacao : listaSolicitacoesEspaco) {
                        relatorioespaco.append("   - ").append(solicitacao).append("\n");
                    }
                }

                JOptionPane.showMessageDialog(panel, relatorioespaco.toString());
            } else {
                // Exibe uma mensagem caso a leitura não seja bem-sucedida
                JOptionPane.showMessageDialog(panel, "Erro ao ler o mapa alocado por espaço.");
            }

        } catch (Exception e) {
            // Exibe uma mensagem caso ocorra uma exceção durante o processo
            JOptionPane.showMessageDialog(panel, "Erro ao exibir o relatório: " + e.getMessage());
        }
    }
    private void limparMapasAlocados(){
        departamento.limparMapasAlocadoporEspacoeCurso();
    }


}