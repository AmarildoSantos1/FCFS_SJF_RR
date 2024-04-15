package escalonamento;

import java.util.ArrayList;

public class RoundRobinComPreempcao {

    private ArrayList<Processo> listaDeProcessos;
    private ArrayList<Processo> listaDeProcessosCopia;
    private ArrayList<Processo> listaParaOrdenar;
    private ArrayList<Processo> listaDeProcessosParaExecutar;
    private ArrayList<String> graficoSaida;
    private int tamanhoDaFatiaDeTempo;
    private int tempo = 0;

    public RoundRobinComPreempcao(ArrayList<Processo> listaDeProcessos, int tamanhoDaFatiaDeTempo) {
        copiaListaDeProcessos(listaDeProcessos);
        this.listaDeProcessos = listaDeProcessos;
        this.tamanhoDaFatiaDeTempo = tamanhoDaFatiaDeTempo;
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
        for (int i = 0; i < listaDeProcessosCopia.size(); i++) {
            if (listaDeProcessosCopia.get(i).getTempoDeChegada() <= tempo) {
                listaParaOrdenar.add(listaDeProcessosCopia.get(i));
                listaDeProcessosCopia.remove(i);
                i--;
            }
        }
        return listaParaOrdenar.size() >= 1;
    }

    private void atualizaFila() {
        for (int i = 0; i < listaParaOrdenar.size(); i++) {
            int prioridadeDoProcessoParaOrdenar = listaParaOrdenar.get(i).getPrioridade();
            int localParaInserirOProcesso = 0;

            if (!listaDeProcessosParaExecutar.isEmpty()) {
                for (int j = 0; j < listaDeProcessosParaExecutar.size(); j++) {
                    if (prioridadeDoProcessoParaOrdenar
                            >= listaDeProcessosParaExecutar.get(j).getPrioridade()) {
                        localParaInserirOProcesso++;
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
        boolean proximoProcessoTemPrioridade = false;
        int tempoQueFaltaExecutar = listaDeProcessosParaExecutar.get(0).getTempoQueFaltaExecutar();
        int numeroDoProcesso = listaDeProcessosParaExecutar.get(0).getNumeroDoProcesso();
        int tempoExecutado = 0;

        if (tempoQueFaltaExecutar >= tamanhoDaFatiaDeTempo) {
            for (int i = 0; i < tamanhoDaFatiaDeTempo; i++) {
                graficoSaida.add(String.valueOf(numeroDoProcesso));
                atualizaTempo();
                tempoExecutado += 1;
                if (processoChegou()) {
                    if (prioridadeEhMaior()) {
                        proximoProcessoTemPrioridade = true;
                        break;
                    }
                }
            }
        } else {
            for (int i = 0; i < tempoQueFaltaExecutar; i++) {
                graficoSaida.add(String.valueOf(numeroDoProcesso));
                atualizaTempo();
                tempoExecutado += 1;
                if (processoChegou()) {
                    if (prioridadeEhMaior()) {
                        proximoProcessoTemPrioridade = true;
                        break;
                    }
                }
            }
        }
        listaDeProcessosParaExecutar.get(0).setTempoExecutado(tempoExecutado);

        if (listaDeProcessosParaExecutar.get(0).getTempoQueFaltaExecutar() == 0) {
            listaDeProcessosParaExecutar.remove(0);
        } else {
            Processo processo = listaDeProcessosParaExecutar.remove(0);
            listaParaOrdenar.add(processo);
            atualizaFila();
        }
    }

    private void atualizaTempo() {
        tempo = graficoSaida.size();
    }

    public ArrayList<String> getGraficoDeSaida() {
        return graficoSaida;
    }

    private boolean prioridadeEhMaior() {
        for (int i = 0; i < listaParaOrdenar.size(); i++) {
            if (listaParaOrdenar.get(i).getPrioridade()
                    < listaDeProcessosParaExecutar.get(0).getPrioridade()) {
                return true;
            }
        }
        return false;
    }

}
