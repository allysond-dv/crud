import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Classe que representa um produto com ID, nome e preço
class Produto implements Serializable { // Serializable para permitir a persistência
    int id; // Identificador único do produto
    String nome; // Nome do produto
    double preco; // Preço do produto

    // Construtor da classe Produto
    public Produto(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    // Representação textual de um produto
    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Preço: " + preco;
    }
}

// Classe responsável por gerenciar o repositório de produtos
class Repositorio {
    ArrayList<Produto> produtos = new ArrayList<>(); // Lista de produtos

    // Método para incluir um novo produto no repositório
    public void incluir(Produto p) {
        produtos.add(p);
    }

    // Método para alterar os dados de um produto existente
    public void alterar(int id, String nome, double preco) {
        for (Produto p : produtos) {
            if (p.id == id) { // Verifica se o ID corresponde a algum produto
                p.nome = nome; // Atualiza o nome
                p.preco = preco; // Atualiza o preço
                System.out.println("Produto alterado com sucesso!");
                return;
            }
        }
        System.out.println("Produto não encontrado.");
    }

    // Método para listar todos os produtos do repositório
    public void listar() {
        System.out.println("\nLista de Produtos:");
        for (Produto p : produtos) {
            System.out.println(p);
        }
    }

    // Método para excluir um produto pelo ID
    public void excluir(int id) {
        for (Produto p : produtos) {
            if (p.id == id) {
                produtos.remove(p); // Remove o produto da lista
                System.out.println("Produto excluído com sucesso!");
                return;
            }
        }
        System.out.println("Produto não encontrado.");
    }

    // Método para salvar os produtos no arquivo binário
    public void salvar(String arquivo) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo));
        out.writeObject(produtos); // Serializa a lista de produtos
        out.close();
        System.out.println("Produtos salvos com sucesso!");
    }

    // Método para carregar os produtos do arquivo binário
    @SuppressWarnings("unchecked")
    public void carregar(String arquivo) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo));
            produtos = (ArrayList<Produto>) in.readObject(); // Desserializa a lista de produtos
            in.close();
            System.out.println("Produtos carregados com sucesso!");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado. Criando novo arquivo.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

// Classe principal que contém o ponto de entrada do programa
public class Main {
    public static void main(String[] args) throws IOException {
        Repositorio repo = new Repositorio(); // Criação do repositório
        Scanner sc = new Scanner(System.in); // Scanner para entrada do usuário

        // Tenta carregar os produtos do arquivo binário
        repo.carregar("produtos.bin");

        while (true) {
            // Menu de opções
            System.out.println("\n1. Incluir Produto\n2. Alterar Produto\n3. Listar Produtos\n4. Excluir Produto\n5. Salvar e Sair");
            int opcao = sc.nextInt(); // Lê a opção escolhida
            sc.nextLine(); // Consome a quebra de linha

            if (opcao == 1) {
                // Incluir produto
                System.out.println("Digite o ID, Nome e Preço:");
                int id = sc.nextInt();
                sc.nextLine(); // Consome a quebra de linha
                String nome = sc.nextLine();
                double preco = sc.nextDouble();
                repo.incluir(new Produto(id, nome, preco));
            } else if (opcao == 2) {
                // Alterar produto
                System.out.println("Digite o ID do produto para alterar:");
                int id = sc.nextInt();
                sc.nextLine(); // Consome a quebra de linha
                System.out.println("Digite o novo Nome e Preço:");
                String nome = sc.nextLine();
                double preco = sc.nextDouble();
                repo.alterar(id, nome, preco);
            } else if (opcao == 3) {
                // Listar produtos
                repo.listar();
            } else if (opcao == 4) {
                // Excluir produto
                System.out.println("Digite o ID do produto para excluir:");
                int id = sc.nextInt();
                repo.excluir(id);
            } else if (opcao == 5) {
                // Salvar e sair
                repo.salvar("produtos.bin");
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }

        sc.close(); // Fecha o scanner
    }
}
