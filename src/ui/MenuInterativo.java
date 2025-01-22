package ui;

import database.DatabaseService;
import service.GutendexService;

import java.util.Scanner;

public class MenuInterativo {
    public static void main(String[] args) {
        // Inicializa os serviços
        DatabaseService dbService = new DatabaseService();
        GutendexService gutendex = new GutendexService();

        // Cria a tabela, garantindo que ela exista.
        dbService.criarTabela();

        Scanner scanner = new Scanner(System.in);

        // Loop do menu
        while (true) {
            System.out.println("\nBem-vindo ao LiterAlura!");
            System.out.println("1. Buscar livros na API");
            System.out.println("2. Salvar livro no catálogo");
            System.out.println("3. Listar livros do catálogo");
            System.out.println("4. Buscar livros por título no catálogo");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha

            switch (opcao) {
                case 1:
                    System.out.print("Digite o título ou autor para buscar: ");
                    String query = scanner.nextLine();
                    String resultado = gutendex.buscarLivros(query);
                    System.out.println("Resultado da busca: " + resultado);
                    break;
                case 2:
                    System.out.print("Digite o título do livro: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Digite os autores (separados por vírgulas): ");
                    String autores = scanner.nextLine();
                    dbService.salvarLivro(titulo, autores);
                    System.out.println("Livro salvo com sucesso!");
                    break;
                case 3:
                    dbService.listarLivros();
                    break;
                case 4:
                    System.out.print("Digite o título para buscar no catálogo: ");
                    String tituloBusca = scanner.nextLine();
                    dbService.buscarLivrosPorTitulo(tituloBusca); // Chama a nova função para buscar por título
                    break;
                case 5:
                    System.out.println("Saindo... Obrigado por testar!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
