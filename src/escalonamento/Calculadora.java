package escalonamento;

import java.util.ArrayList;

public class Calculadora {

    private ArrayList<Processo> listaDeProcessos;
    private ArrayList<String> graficoDeSaida;
    private double tempoDeEspera;
    private double tempoDeResposta;
    private double tempoDeTurnaround;

    public Calculadora(ArrayList<Processo> listaDeProcessos, ArrayList<String> graficoDeSaida) {
        this.listaDeProcessos = listaDeProcessos;
        this.graficoDeSaida = graficoDeSaida;
        this.tempoDeEspera = 0;
        this.tempoDeResposta = 0;
        this.tempoDeTurnaround = 0;
    }

    public void calculaTemposDoProcesso(Processo processo) {
        int tempoDeChegada = processo.getTempoDeChegada();
        int tempoDeExecucao = processo.getTempoDeExecucao();
        int numeroDoProcesso = processo.getNumeroDoProcesso();

        int tempoTerminoDoProcesso = pegaOTempoDeTerminoDoProcesso(numeroDoProcesso);
        int tempoInicioDoProcesso = pegaOTempoDeInicioDoProcesso(numeroDoProcesso);

        this.tempoDeEspera = tempoTerminoDoProcesso - tempoDeChegada - tempoDeExecucao;
        this.tempoDeResposta = tempoInicioDoProcesso - tempoDeChegada;
        this.tempoDeTurnaround = tempoTerminoDoProcesso - tempoDeChegada;
    }

    private int pegaOTempoDeTerminoDoProcesso(int numeroDoProcesso) {
        for (int i = graficoDeSaida.size() - 1; i >= 0; i--) {
            if (graficoDeSaida.get(i).equals("" + numeroDoProcesso)) {
                return i + 1;
            }
        }
        return -1;
    }

    private int pegaOTempoDeInicioDoProcesso(int numeroDoProcesso) {
        for (int i = 0; i < graficoDeSaida.size(); i++) {
            if (graficoDeSaida.get(i).equals("" + numeroDoProcesso)) {
                return i;
            }
        }
        return -1;
    }

    public double getTempoDeEspera() {
        return this.tempoDeEspera;
    }

    public double getTempoDeResposta() {
        return this.tempoDeResposta;
    }

    public double getTempoDeTurnaround() {
        return this.tempoDeTurnaround;
    }

    public ArrayList<String> getGraficoDeSaida() {
        return this.graficoDeSaida;
    }
}
