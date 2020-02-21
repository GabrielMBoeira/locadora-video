import dao.AluguelDAO;
import dao.ClienteDAO;
import dao.FilmeDAO;
import dao.jdbc.AluguelDAOImpl;
import dao.jdbc.ClienteDAOImpl;
import dao.jdbc.FilmeDAOImpl;
import entidades.Aluguel;
import entidades.Cliente;
import entidades.Filme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;


public class Main {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/locadora", "postgres", "123456");
            conn.setAutoCommit(false); 
    
            //Demonstrar o funcionamento aqui
            ClienteDAO clienteDAO = new ClienteDAOImpl();
            AluguelDAO aluguelDAO = new AluguelDAOImpl(); 
            FilmeDAO filmeDAO = new FilmeDAOImpl();
            
            Cliente cliente = new Cliente();
            Filme filme = new Filme();
            Aluguel aluguel = new Aluguel();
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            cliente.setNome("Gabriel Boeira");
            
            clienteDAO.insert(conn, cliente); //Inserindo Cliente
            
            filme.setNome("A melhor contratação - PariPassu");
            filme.setDataLancamento(sdf.parse("01/01/2020"));
            filme.setDescricao("Ação/Superação");
            
            filmeDAO.insert(conn, filme); //Inserindo Filme
            
            List<Filme> filmes = new ArrayList<>();
            filmes.add(filme);
            
            aluguel.setFilmes(filmes);
            aluguel.setCliente(cliente);
            aluguel.setDataAluguel(sdf.parse("24/02/2020"));
            aluguel.setValor(6.00f);
            
            aluguelDAO.insert(conn, aluguel); //Inserindo Aluguel
            
            //System.out.println(aluguelDAO.list(conn));
            
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fim do teste.");
     
    }
    
}