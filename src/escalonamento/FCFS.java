package escalonamento;

import java.util.ArrayList;

public class FCFS {
    private ArrayList<Processo> listaDeProcessos;
    private ArrayList<String> graficoSaida;

    public FCFS(ArrayList<Processo> listaDeProcessos) {
        this.listaDeProcessos = listaDeProcessos;
        this.graficoSaida = new ArrayList<>();
        executaEscalonamento();
    }

    private void executaEscalonamento() {
        int tempoAtual = 0;

        for (Processo processo : listaDeProcessos) {
            int numeroDoProcesso = processo.getNumeroDoProcesso();
            int tempoDeChegada = processo.getTempoDeChegada();
            int tempoDeExecucao = processo.getTempoDeExecucao();

            // Verifica se o processo chegou antes do tempo atual
            if (tempoAtual < tempoDeChegada) {
                tempoAtual = tempoDeChegada; // Avança o tempo atual para o tempo de chegada do processo
            }

            // Adiciona o processo ao gráfico de saída durante sua execução
            for (int t = 0; t < tempoDeExecucao; t++) {
                graficoSaida.add(String.valueOf(numeroDoProcesso));
            }

            tempoAtual += tempoDeExecucao; // Avança o tempo atual pelo tempo de execução do processo
        }
    }

    public ArrayList<String> getGraficoDeSaida() {
        return graficoSaida;
    }

    public static void main(String[] args) {
        // Exemplo de uso da classe FCFS
        ArrayList<Processo> listaDeProcessos = new ArrayList<>();
        listaDeProcessos.add(new Processo(1, 0, 3, 1));  // Processo 1: Chegada 0, Execução 3, Prioridade 1
        listaDeProcessos.add(new Processo(2, 2, 4, 2));  // Processo 2: Chegada 2, Execução 4, Prioridade 2

        // Execução do algoritmo FCFS
        FCFS fcfs = new FCFS(listaDeProcessos);
        ArrayList<String> graficoDeSaidaFCFS = fcfs.getGraficoDeSaida();

        // Exibindo o gráfico de saída do FCFS
        System.out.println("Gráfico de Saída (FCFS):");
        for (String linha : graficoDeSaidaFCFS) {
            System.out.print(linha + " ");
        }
        System.out.println();

        // Calculando e exibindo os tempos médios manualmente
        int tempoTotalEspera = 0;
        int tempoTotalTurnaround = 0;

        for (Processo processo : listaDeProcessos) {
            int tempoEspera = processo.getTempoDeChegada(); // Tempo de espera é o mesmo que o tempo de chegada para FCFS
            int tempoTurnaround = tempoEspera + processo.getTempoDeExecucao();
            tempoTotalEspera += tempoEspera;
            tempoTotalTurnaround += tempoTurnaround;
        }

        int quantidadeProcessos = listaDeProcessos.size();
        double tempoMedioEspera = (double) tempoTotalEspera / quantidadeProcessos;
        double tempoMedioTurnaround = (double) tempoTotalTurnaround / quantidadeProcessos;

        System.out.println("Tempo Médio de Espera: " + tempoMedioEspera);
        System.out.println("Tempo Médio de Turnaround: " + tempoMedioTurnaround);
    }
}
