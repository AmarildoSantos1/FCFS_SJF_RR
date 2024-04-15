package escalonamento;

public class Processo {

    private int numeroDoProcesso;
    private int tempoDeChegada;
    private int tempoDeExecucao;
    private int tempoQueFaltaExecutar;
    private int prioridade;
    private int core; // Novo atributo para representar o core do processador

    public Processo(int numeroDoProcesso, int tempoDeChegada, int tempoDeExecucao, int prioridade) {
        this.numeroDoProcesso = numeroDoProcesso;
        this.tempoDeChegada = tempoDeChegada;
        this.tempoDeExecucao = tempoDeExecucao;
        this.prioridade = prioridade;
        this.tempoQueFaltaExecutar = tempoDeExecucao; // Inicialmente, o tempo que falta para executar é igual ao tempo de execução
        this.core = -1; // Inicializa o core como -1 (ainda não atribuído a nenhum core)
    }

    public int getTempoQueFaltaExecutar() {
        return tempoQueFaltaExecutar;
    }

    public void setTempoExecutado(int fatiaDeTempo) {
        this.tempoQueFaltaExecutar -= fatiaDeTempo;
    }

    public void atualizaTempoQueFaltaExecutar() {
        tempoQueFaltaExecutar = tempoDeExecucao;
    }

    public int getTempoDeChegada() {
        return tempoDeChegada;
    }

    public int getTempoDeExecucao() {
        return tempoDeExecucao;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public int getNumeroDoProcesso() {
        return numeroDoProcesso;
    }

    public int getCore() {
        return core;
    }

    public void setCore(int core) {
        this.core = core;
    }
}
