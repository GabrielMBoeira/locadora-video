package dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.AluguelDAO;
import entidades.Aluguel;
import entidades.Cliente;
import entidades.Filme;

public class AluguelDAOImpl implements AluguelDAO {

	@Override
	public void insert(Connection conn, Aluguel aluguel) throws Exception {
		
	      PreparedStatement myStmt = conn.prepareStatement("insert into en_aluguel (id_aluguel, id_cliente, data_aluguel, valor) values (?, ?, ?, ?)");

	      Integer idAluguel = this.getNextId(conn);

	      myStmt.setInt(1, idAluguel);
	      myStmt.setInt(2, aluguel.getCliente().getIdCliente());
	      myStmt.setDate(3, new java.sql.Date(aluguel.getDataAluguel().getTime()));
	      myStmt.setFloat(4, aluguel.getValor());
	      
	      myStmt.execute();
	      conn.commit();

	      aluguel.setIdAluguel(idAluguel);
		
	}

	@Override
	public Integer getNextId(Connection conn) throws Exception {
        PreparedStatement myStmt = conn.prepareStatement("select nextval('seq_en_aluguel')");
        ResultSet rs = myStmt.executeQuery();
        rs.next();
        return rs.getInt(1);
	}

	@Override
	public void edit(Connection conn, Aluguel aluguel) throws Exception {
		
        PreparedStatement myStmt = conn.prepareStatement("update en_aluguel set id_cliente = (?), data_aluguel = (?), valor = (?) where id_aluguel = (?)");

        myStmt.setInt(1, aluguel.getCliente().getIdCliente());
        myStmt.setDate(2, new java.sql.Date(aluguel.getDataAluguel().getTime()));
        myStmt.setFloat(3, aluguel.getValor());
        myStmt.setInt(4, aluguel.getIdAluguel());

        myStmt.execute();
        conn.commit();

	}

	@Override
	public void delete(Connection conn, Aluguel aluguel) throws Exception {
		
		PreparedStatement myStmt = conn.prepareStatement("delete from en_aluguel where id_aluguel = (?)");
		
		myStmt.setInt(1, aluguel.getIdAluguel());
		
		myStmt.execute();
		conn.commit();

	}

	@Override
	public Aluguel find(Connection conn, Integer idAluguel) throws Exception {
		
		PreparedStatement myStmt = conn.prepareStatement("select id_aluguel, en_cliente.nome, data_aluguel, valor from en_aluguel inner join en_cliente on en_aluguel.id_cliente = en_cliente.id_cliente where id_aluguel = (?);");
		
		myStmt.setInt(1, idAluguel);
		
		ResultSet myRs = myStmt.executeQuery();
		
        if (!myRs.next()) {
            return null;
        }
		
		Cliente cliente = new Cliente();
		List<Filme> filmes = new ArrayList<Filme>();
		
		filmes.add(null);
		cliente.setNome(myRs.getString("nome"));
		Date dataAluguel = myRs.getDate("data_aluguel");
		Float valor = myRs.getFloat("valor");
		
		
		//cliente.setIdCliente(myRs.getInt("id_cliente"));

		
		return new Aluguel(idAluguel, filmes, cliente, dataAluguel, valor);
	}

	@Override
	public Collection<Aluguel> list(Connection conn) throws Exception {
		
        PreparedStatement myStmt = conn.prepareStatement("select id_aluguel, nome, data_aluguel, valor from en_aluguel inner join en_cliente on en_aluguel.id_cliente = en_cliente.id_cliente;");
        ResultSet myRs = myStmt.executeQuery();
        
        Cliente cliente = new Cliente();

        Collection<Aluguel> items = new ArrayList<>();

        while (myRs.next()) {
        	Aluguel aluguel = new Aluguel();
        	aluguel.setIdAluguel(myRs.getInt("id_aluguel"));
        	aluguel.setCliente(cliente.setNome(myRs.getString("nome")));
        	aluguel.setDataAluguel(myRs.getDate("data_aluguel"));
        	aluguel.setValor(myRs.getFloat("valor"));
        	
        	items.add(aluguel);
        }
        return items;
	}

}
