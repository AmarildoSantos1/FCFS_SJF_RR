# Escalonamento
Esse projeto tem como objetivo simular algoritmos de escalonamento com e sem preempção.

Ler de um arquivo as seguintes informações, nesta ordem: número de processos, tamanho de fatia de tempo, e para cada processo, tempo de chegada, tempo de execução e prioridade (1 até 9). Imprimir os tempos de turnaround, resposta e espera para cada um dos seguintes algoritmos: SJF não preemptivo (desempate pela prioridade),  Round Robin, Round Robin com prioridade preemptivo. Além disto imprimir um gráfico (texto) mostrando como os processo foram executados. Considerar duas unidades de tempo para troca de contexto (representado abaixo como TC).

Exemplo de arquivo de entrada:

5

3

3 10 2

4 12 1

9 15 2

11 15 1

12 8 5


Exemplo do gráfico de saída para o exemplo acima, para o algoritmo SJF:

---1111111111TC55555555TC222222222222TC444444444444444TC333333333333333
