package escalonamento;

import java.util.ArrayList;

public class Escalonamento {

    public static void main(String[] args) {
        Arquivo a = new Arquivo();
        a.leArquivo("arquivo.txt");

        ArrayList<String> dadosDoArquivo = a.pegaDadosDoArquivo();

        // Manipula os dados para passar para o gerenciador de processos
        int quantidadeProcessos = Integer.parseInt(dadosDoArquivo.get(0));
        int tamanhoDaFatiaDeTempo = Integer.parseInt(dadosDoArquivo.get(1));

        // Passa os dados manipulados para o gerenciador de processos
        GerenciadorDeProcessos gerenciadorDeProcessos = new GerenciadorDeProcessos(quantidadeProcessos, tamanhoDaFatiaDeTempo, dadosDoArquivo);
        gerenciadorDeProcessos.adicionaProcessos();

        ArrayList<Processo> listaDeProcessos = gerenciadorDeProcessos.getListaDeProcessos();

        // Execução dos processos
        for (int i = 0; i < listaDeProcessos.size(); i++) {
            Processo processo = listaDeProcessos.get(i);
            int core = i % Runtime.getRuntime().availableProcessors(); // Atribui core com base nos processadores disponíveis

            processo.setCore(core);
            System.out.println("O Processo " + processo.getNumeroDoProcesso() + " está no Core " + core + " do processador");

            // Execute o processo neste core
            // ...
        }

        // Execução do algoritmo FCFS
        executarAlgoritmoFCFS(listaDeProcessos);

        // Execução do algoritmo SJF
        executarAlgoritmoSJF(listaDeProcessos);

        // Execução do Round Robin
        executarAlgoritmoRoundRobin(listaDeProcessos, tamanhoDaFatiaDeTempo);

        // Execução do Round Robin com preempção
        executarAlgoritmoRoundRobinComPreempcao(listaDeProcessos, tamanhoDaFatiaDeTempo);
    }

    private static void executarAlgoritmoFCFS(ArrayList<Processo> listaDeProcessos) {
        FCFS fcfs = new FCFS(listaDeProcessos);

        ArrayList<String> graficoDeSaidaFCFS = fcfs.getGraficoDeSaida();
        Calculadora calculadoraFCFS = new Calculadora(listaDeProcessos, graficoDeSaidaFCFS);

        imprimirResultados("FCFS", listaDeProcessos, calculadoraFCFS);
    }

    private static void executarAlgoritmoSJF(ArrayList<Processo> listaDeProcessos) {
        GerenciadorDeProcessos gerenciadorDeProcessos = new GerenciadorDeProcessos(listaDeProcessos.size(), 1, null); // Use um tamanho de fatia de tempo mínimo para o SJF
        gerenciadorDeProcessos.atualizaOTempoQueFaltaExecutar();
        SJF sjf = new SJF(listaDeProcessos);

        ArrayList<String> graficoDeSaidaSJF = sjf.getGraficoDeSaida();
        Calculadora calculadoraSJF = new Calculadora(listaDeProcessos, graficoDeSaidaSJF);

        imprimirResultados("SJF", listaDeProcessos, calculadoraSJF);
    }

    private static void executarAlgoritmoRoundRobin(ArrayList<Processo> listaDeProcessos, int tamanhoDaFatiaDeTempo) {
        GerenciadorDeProcessos gerenciadorDeProcessos = new GerenciadorDeProcessos(listaDeProcessos.size(), tamanhoDaFatiaDeTempo, null);
        gerenciadorDeProcessos.atualizaOTempoQueFaltaExecutar();
        RoundRobin roundrobin = new RoundRobin(listaDeProcessos, tamanhoDaFatiaDeTempo);

        ArrayList<String> graficoDeSaidaRoundRobin = roundrobin.getGraficoDeSaida();
        Calculadora calculadoraRoundRobin = new Calculadora(listaDeProcessos, graficoDeSaidaRoundRobin);

        imprimirResultados("RoundRobin", listaDeProcessos, calculadoraRoundRobin);
    }

    private static void executarAlgoritmoRoundRobinComPreempcao(ArrayList<Processo> listaDeProcessos, int tamanhoDaFatiaDeTempo) {
        GerenciadorDeProcessos gerenciadorDeProcessos = new GerenciadorDeProcessos(listaDeProcessos.size(), tamanhoDaFatiaDeTempo, null);
        gerenciadorDeProcessos.atualizaOTempoQueFaltaExecutar();
        RoundRobinComPreempcao roundRobinComPreempcao = new RoundRobinComPreempcao(listaDeProcessos, tamanhoDaFatiaDeTempo);

        ArrayList<String> graficoDeSaidaRoundRobinComPreempcao = roundRobinComPreempcao.getGraficoDeSaida();
        Calculadora calculadoraRoundRobinComPreempcao = new Calculadora(listaDeProcessos, graficoDeSaidaRoundRobinComPreempcao);

        imprimirResultados("RoundRobin com Preempção", listaDeProcessos, calculadoraRoundRobinComPreempcao);
    }

    private static void imprimirResultados(String algoritmo, ArrayList<Processo> listaDeProcessos, Calculadora calculadora) {
        System.out.println("Gráfico de saída: " + algoritmo);
        for (String linha : calculadora.getGraficoDeSaida()) {
            System.out.print(linha);
        }

        System.out.println();

        double acumuladorTempoDeEspera = 0;
        double acumuladorTempoDeResposta = 0;
        double acumuladorTempoDeTurnaround = 0;

        for (Processo processo : listaDeProcessos) {
            System.out.println("Cálculo para o processo " + processo.getNumeroDoProcesso());
            calculadora.calculaTemposDoProcesso(processo);

            System.out.println("Tempo de Espera: " + calculadora.getTempoDeEspera());
            acumuladorTempoDeEspera += calculadora.getTempoDeEspera();

            System.out.println("Tempo de Resposta: " + calculadora.getTempoDeResposta());
            acumuladorTempoDeResposta += calculadora.getTempoDeResposta();

            System.out.println("Tempo de Turnaround: " + calculadora.getTempoDeTurnaround());
            acumuladorTempoDeTurnaround += calculadora.getTempoDeTurnaround();

            System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
        }

        int quantidadeProcessos = listaDeProcessos.size();
        System.out.println("Tempo médio de Espera: " + (acumuladorTempoDeEspera / quantidadeProcessos));
        System.out.println("Tempo médio de Resposta: " + (acumuladorTempoDeResposta / quantidadeProcessos));
        System.out.println("Tempo médio de Turnaround: " + (acumuladorTempoDeTurnaround / quantidadeProcessos));
    }
}
