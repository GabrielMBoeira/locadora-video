package dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.AluguelDAO;
import dao.FilmeDAO;
import entidades.Cliente;
import entidades.Filme;

public class FilmeDAOImpl implements FilmeDAO {

	@Override
	public void insert(Connection conn, Filme filme) throws Exception {
		
        PreparedStatement myStmt = conn.prepareStatement("insert into en_filme (id_filme, data_lancamento, nome, descricao) values (?, ?, ?, ?)");

        Integer idFilme = this.getNextId(conn);

        myStmt.setInt(1, idFilme);
        myStmt.setDate(2, new java.sql.Date(filme.getDataLancamento().getTime()));
        myStmt.setString(3, filme.getNome());
        myStmt.setString(4, filme.getDescricao());
        myStmt.execute();
        conn.commit();

        filme.setIdFilme(idFilme);
		
	}

	@Override
	public Integer getNextId(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select nextval('seq_en_filme')");
		ResultSet rs = myStmt.executeQuery();
		rs.next();
		return rs.getInt(1);
	}

	@Override
	public void edit(Connection conn, Filme filme) throws Exception {
		
        PreparedStatement myStmt = conn.prepareStatement("update en_filme set nome = (?), descricao = (?), data_lancamento = (?) where id_filme = (?)");

        myStmt.setString(1, filme.getNome());
        myStmt.setString(2, filme.getDescricao());
        myStmt.setDate(3, new java.sql.Date(filme.getDataLancamento().getTime()));
        myStmt.setInt(4, filme.getIdFilme());

        myStmt.execute();
        conn.commit();
		
	}

	@Override
	public void delete(Connection conn, Integer idFilme) throws Exception {
		
        PreparedStatement myStmt = conn.prepareStatement("delete from en_filme where id_filme = (?)");

        myStmt.setInt(1, idFilme);

        myStmt.execute();
        conn.commit();
		
	}

	@Override
	public Filme find(Connection conn, Integer idFilme) throws Exception {
		
        PreparedStatement myStmt = conn.prepareStatement("select * from en_filme where id_filme = (?)");

        myStmt.setInt(1, idFilme);

        ResultSet myRs = myStmt.executeQuery();
        
        if (!myRs.next()) {
            return null;
        }

        Date dataLancamento = myRs.getDate("data_lancamento");
        String descricao = myRs.getString("descricao");
        String nome = myRs.getString("nome");
        return new Filme(idFilme, dataLancamento, nome, descricao);
	}

	@Override
	public Collection<Filme> list(Connection conn) throws Exception {
		
        PreparedStatement myStmt = conn.prepareStatement("select * from en_filme order by nome");
        ResultSet myRs = myStmt.executeQuery();

        Collection<Filme> items = new ArrayList<>();

        while (myRs.next()) {
            Integer idFilme = myRs.getInt("id_filme");
            Date dataLancamento = myRs.getDate("data_lancamento");
            String nome = myRs.getString("nome");
            String descricao = myRs.getString("descricao");

            items.add(new Filme(idFilme, dataLancamento, nome, descricao));
        }
        return items;
	}

}
