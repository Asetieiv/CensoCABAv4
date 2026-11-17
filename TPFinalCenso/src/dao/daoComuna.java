package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Comuna;
import modelo.Persona;

public class daoComuna {
	String user = "root";
	String psw = "admin";
	String path = "jdbc:mysql://localhost:3306/censocabatp";


	
    public Connection conectar() {
		Connection c = null;
		try {
			c = DriverManager.getConnection(path, user, psw);
			System.out.println("Se conectó");
		} catch (SQLException e) {
			e.printStackTrace();

		}
		System.out.println("se conectó");
		return c;
	}
	

	public ArrayList<Persona> consultar() {
		Connection c = null;
		String sql = "SELECT * from personas";
		ArrayList<Persona> solucion = new ArrayList<>();
		try {
			c = conectar();
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				float locura = (float)rs.getInt("locura");
				float secretos = (float)rs.getInt("secretos");
				float ubicacion = (float)rs.getInt("ubicacion");
				Persona p = new Persona(nombre,locura,secretos,ubicacion);
				solucion.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return solucion;
	}
    
    
	public void eliminarComunaPorParametro (int numComuna) {
		Connection c = null;
		String SQL = "DELETE from comuna WHERE numComuna = ?";
		try {
			c = conectar();
			PreparedStatement stmt = c.prepareStatement(SQL);
			stmt.setInt(1,numComuna);
			stmt.executeUpdate();
			System.out.println("Se elimino el registro con exito");
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}	
    
	
	public void agregarComunaxParametro(Comuna co) {
		Connection c = null;
		String SQL = "INSERT INTO comuna (sede, numComuna, cantidadBarrios) VALUES (?,?,?);";
		try {
			c = conectar();
			PreparedStatement stmt = c.prepareStatement(SQL);
	        stmt.setString(1, co.getSede());
	        stmt.setInt(2, co.getNumComuna());
	        stmt.setInt(3, co.cantidadDeBarrios());		
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
