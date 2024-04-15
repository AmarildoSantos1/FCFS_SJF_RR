package escalonamento;

import java.util.ArrayList;

class GerenciadorDeProcessos {

    private int quantidadeProcessos;
    private int tamanhoDaFatiaDeTempo;
    private ArrayList<String> dadosDoArquivo;
    private ArrayList<Processo> listaDeProcessos;

    GerenciadorDeProcessos(int quantidadeProcessos, int tamanhoDaFatiaDeTempo, ArrayList<String> dadosDoArquivo) {
        this.quantidadeProcessos = quantidadeProcessos;
        this.tamanhoDaFatiaDeTempo = tamanhoDaFatiaDeTempo;
        this.dadosDoArquivo = dadosDoArquivo;
        listaDeProcessos = new ArrayList<Processo>();
    }

    public void adicionaProcessos() {
        int numeroDoProcesso = 1;

        for (int i = 2; i < dadosDoArquivo.size(); i++) {
            String[] lista = dadosDoArquivo.get(i).split(" ");
            int tempoDeChegada = Integer.parseInt(lista[0]);
            int tempoDeExecucao = Integer.parseInt(lista[1]);
            int prioridade = Integer.parseInt(lista[2]);
            listaDeProcessos.add(new Processo(numeroDoProcesso, tempoDeChegada, tempoDeExecucao, prioridade));

            numeroDoProcesso++;
        }
    }

    public ArrayList<Processo> getListaDeProcessos() {
        return listaDeProcessos;
    }

    public void atualizaOTempoQueFaltaExecutar() {
        for (int i = 0; i < listaDeProcessos.size(); i++) {
            listaDeProcessos.get(i).atualizaTempoQueFaltaExecutar();
        }
    }

}
