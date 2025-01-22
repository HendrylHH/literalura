package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GutendexService {
    private static final String API_URL = "https://gutendex.com/books";

    public String buscarLivros(String query) {
        try {
            // URL de busca
            String urlString = API_URL + "?search=" + query.replace(" ", "+");
            URL url = new URL(urlString);

            // conexão HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Verifica o código de resposta
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                return "Erro na conexão com a API. Código: " + responseCode;
            }

            // Lê a resposta JSON
            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            JsonObject jsonResponse = JsonParser.parseReader(reader).getAsJsonObject();

            // Filtra os livros do JSON
            JsonArray livros = jsonResponse.getAsJsonArray("results");
            StringBuilder resultadoFiltrado = new StringBuilder();

            for (JsonElement livroElement : livros) {
                JsonObject livro = livroElement.getAsJsonObject();
                String titulo = livro.get("title").getAsString();
                JsonArray autoresArray = livro.getAsJsonArray("authors");

                // Constrói a lista de autores
                StringBuilder autores = new StringBuilder();
                for (JsonElement autorElement : autoresArray) {
                    JsonObject autor = autorElement.getAsJsonObject();
                    autores.append(autor.get("name").getAsString()).append(", ");
                }

                // Remove a vírgula extra no final
                if (autores.length() > 2) {
                    autores.setLength(autores.length() - 2);
                }

                // Adiciona o livro ao resultado filtrado
                resultadoFiltrado.append("Título: ").append(titulo).append("\n");
                resultadoFiltrado.append("Autores: ").append(autores).append("\n\n");
            }

            return resultadoFiltrado.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao buscar livros: " + e.getMessage();
        }
    }
}
