package escalonamento;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


public class Arquivo {

    private ArrayList<String> dados;

    public Arquivo() {
        dados = new ArrayList<String>();
    }

    public void leArquivo(String nomeDoArquivo) {
        Path path = Paths.get(nomeDoArquivo);
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            percorreConteudoDoArquivo(br);
        } catch (IOException e) {
            System.err.format("Ocorreu um erro ao ler o arquivo.", e);
        }
    }

    private void percorreConteudoDoArquivo(BufferedReader br) throws IOException {
        String linha;
        while ((linha = br.readLine()) != null) {
            Scanner sc = new Scanner(linha).useDelimiter("\n");
            while (sc.hasNext()) {
                String dado = sc.next();
                adicionaDadoNaLista(dado);
            }
        }
    }

    private void adicionaDadoNaLista(String dado) {
        dados.add(dado);
    }

    public ArrayList<String> pegaDadosDoArquivo() {
        return dados;
    }

}
