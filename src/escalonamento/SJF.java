package escalonamento;

import java.util.ArrayList;

public class SJF {
    
    private ArrayList<Processo> listaDeProcessosCopia;
    private ArrayList<Processo> listaParaOrdenar;
    private ArrayList<Processo> listaDeProcessosParaExecutar;
    private ArrayList<String> graficoSaida;
    private int tempo = 0;
    private int tempoVerificacaoChegada = 0;

    public SJF(ArrayList<Processo> listaDeProcessos) {
        copiaListaDeProcessos(listaDeProcessos);
        listaParaOrdenar = new ArrayList<Processo>();
        listaDeProcessosParaExecutar = new ArrayList<Processo>();
        graficoSaida = new ArrayList<String>();
        executaEscalonamento();
    }

    private void copiaListaDeProcessos(ArrayList<Processo> listaDeProcessos) {
        listaDeProcessosCopia = new ArrayList<Processo>();
        for (int i = 0; i < listaDeProcessos.size(); i++) {
            listaDeProcessosCopia.add(listaDeProcessos.get(i));
        }
    }

    private void executaEscalonamento() {
        while (!listaDeProcessosCopia.isEmpty()
                || listaDeProcessosParaExecutar.size() > 0) {
            if (processoChegou()) {
                atualizaFila();
            }
            if (temProcessoNaFila()) {
                executaProcesso();
                graficoSaida.add("T");
                graficoSaida.add("C");
            } else {
                graficoSaida.add("-");
            }
            atualizaTempo();
        }
    }

    private boolean processoChegou() {
        listaParaOrdenar.clear();
        for (int i = 0; i < listaDeProcessosCopia.size(); i++) {
            if (listaDeProcessosCopia.get(i).getTempoDeChegada() <= tempo
                    && listaDeProcessosCopia.get(i).getTempoDeChegada() > tempoVerificacaoChegada) {
                listaParaOrdenar.add(listaDeProcessosCopia.get(i));
                listaDeProcessosCopia.remove(i);
                i--;
            }
        }
        if (listaParaOrdenar.size() >= 1) {
            return true;
        }
        return false;
    }

    private void atualizaFila() {
        for (int i = 0; i < listaParaOrdenar.size(); i++) {
            int tempoDeExecucaoDoProcessoParaOrdenar = listaParaOrdenar.get(i).getTempoDeExecucao();
            int localParaInserirOProcesso = 0;

            if (!listaDeProcessosParaExecutar.isEmpty()) {
                for (int j = 0; j < listaDeProcessosParaExecutar.size(); j++) {
                    if (tempoDeExecucaoDoProcessoParaOrdenar
                            > listaDeProcessosParaExecutar.get(j).getTempoDeExecucao()) {
                        localParaInserirOProcesso++;
                    }
                    if (tempoDeExecucaoDoProcessoParaOrdenar == listaDeProcessosParaExecutar.get(j).getTempoDeExecucao()) {
                        if (listaParaOrdenar.get(i).getPrioridade() > listaDeProcessosParaExecutar.get(j).getPrioridade()) {
                            localParaInserirOProcesso++;
                        }
                    }
                }
            }
            listaDeProcessosParaExecutar.add(localParaInserirOProcesso, listaParaOrdenar.get(i));
            listaParaOrdenar.remove(i);
            i--;
        }
    }

    private boolean temProcessoNaFila() {
        if (listaDeProcessosParaExecutar.isEmpty()) {
            return false;
        }
        return true;
    }

    private void executaProcesso() {
        int tempoDeExecucaoDoProcesso = listaDeProcessosParaExecutar.get(0).getTempoDeExecucao();
        int numeroDoProcesso = listaDeProcessosParaExecutar.get(0).getNumeroDoProcesso();
        for (int i = 0; i < tempoDeExecucaoDoProcesso; i++) {
            graficoSaida.add(String.valueOf(numeroDoProcesso));
        }
        listaDeProcessosParaExecutar.remove(0);
    }

    private void atualizaTempo() {
        tempo = graficoSaida.size();
    }

    public ArrayList<String> getGraficoDeSaida() {
        return graficoSaida;
    }
}