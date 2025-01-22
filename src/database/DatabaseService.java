package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseService {
    private static final String DB_URL = "jdbc:sqlite:livros.db"; // Caminho do banco de dados SQLite

    // Conecta ao SQLite
    private Connection conectar() throws Exception {
        return DriverManager.getConnection(DB_URL);
    }

    // Cria a tabela livros (caso não exista)
    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS livros (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titulo TEXT NOT NULL," +
                "autores TEXT NOT NULL" +
                ");";
        try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Salvar livro no banco de dados
    public void salvarLivro(String titulo, String autores) {
        String sql = "INSERT INTO livros(titulo, autores) VALUES(?, ?)";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, titulo);
            pstmt.setString(2, autores);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Listar livros no banco de dados
    public void listarLivros() {
        String sql = "SELECT * FROM livros";
        try (Connection conn = conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Título: " + rs.getString("titulo"));
                System.out.println("Autores: " + rs.getString("autores"));
                System.out.println("---------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Buscar livros por título no banco de dados
    public void buscarLivrosPorTitulo(String titulo) {
        String sql = "SELECT * FROM livros WHERE titulo LIKE ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + titulo + "%"); // Usar LIKE para buscar por parte do título
            ResultSet rs = pstmt.executeQuery();
            boolean encontrou = false;
            while (rs.next()) {
                System.out.println("Título: " + rs.getString("titulo"));
                System.out.println("Autores: " + rs.getString("autores"));
                System.out.println("---------------------------");
                encontrou = true;
            }
            if (!encontrou) {
                System.out.println("Nenhum livro encontrado com o título informado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
