package com.skeletonhexa.infrastructure.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.skeletonhexa.domain.entities.Paciente;
import com.skeletonhexa.domain.repository.Pacienterepository;
import com.skeletonhexa.infrastructure.database.ConnectionDb;

public class PacienteRepositoryImpl implements Pacienterepository {
    private final ConnectionDb connectionDb;

    public PacienteRepositoryImpl(ConnectionDb connectionDb) {
        this.connectionDb = connectionDb;
    }

    @Override
    public Paciente save(Paciente paciente) {
        String sql = "INSERT INTO pacientes (nombre, apellido, fecha_nacimiento, direccion, telefono, email) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setDate(3, Date.valueOf(paciente.getFechaNacimiento()));
            stmt.setString(4, paciente.getDireccion());
            stmt.setString(5, paciente.getTelefono());
            stmt.setString(6, paciente.getEmail());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating paciente failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    paciente.setId(generatedKeys.getInt(1));
                    return paciente;
                } else {
                    throw new SQLException("Creating paciente failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating paciente", e);
        }
    }

    @Override
    public Optional<Paciente> findById(Integer id) {
        String sql = "SELECT * FROM pacientes WHERE id = ?";
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setApellido(rs.getString("apellido"));
                paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                paciente.setDireccion(rs.getString("direccion"));
                paciente.setTelefono(rs.getString("telefono"));
                paciente.setEmail(rs.getString("email"));
                return Optional.of(paciente);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding paciente", e);
        }
    }

    @Override
    public List<Paciente> findAll() {
        String sql = "SELECT * FROM pacientes";
        List<Paciente> pacientes = new ArrayList<>();
        
        try (Connection conn = connectionDb.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setApellido(rs.getString("apellido"));
                paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                paciente.setDireccion(rs.getString("direccion"));
                paciente.setTelefono(rs.getString("telefono"));
                paciente.setEmail(rs.getString("email"));
                pacientes.add(paciente);
            }
            return pacientes;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all pacientes", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM pacientes WHERE id = ?";
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting paciente", e);
        }
    }

    @Override
    public Paciente update(Paciente paciente) {
        String sql = "UPDATE pacientes SET nombre = ?, apellido = ?, fecha_nacimiento = ?, direccion = ?, telefono = ?, email = ? WHERE id = ?";
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setDate(3, Date.valueOf(paciente.getFechaNacimiento()));
            stmt.setString(4, paciente.getDireccion());
            stmt.setString(5, paciente.getTelefono());
            stmt.setString(6, paciente.getEmail());
            stmt.setInt(7, paciente.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating paciente failed, no rows affected.");
            }
            return paciente;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating paciente", e);
        }
    }
}
