package com.skeletonhexa.infrastructure.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.skeletonhexa.domain.entities.Cita;
import com.skeletonhexa.domain.repository.Citarepository;
import com.skeletonhexa.infrastructure.database.ConnectionDb;

public class CitaRepositoryImpl implements Citarepository {
    private final ConnectionDb connectionDb;

    public CitaRepositoryImpl(ConnectionDb connectionDb) {
        this.connectionDb = connectionDb;
    }

    @Override
    public Cita save(Cita cita) {
        String sql = "INSERT INTO citas (paciente_id, medico_id, fecha_hora, estado) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, cita.getPacienteId());
            stmt.setInt(2, cita.getMedicoId());
            stmt.setTimestamp(3, Timestamp.valueOf(cita.getFechaHora()));
            stmt.setString(4, cita.getEstado());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating cita failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cita.setId(generatedKeys.getInt(1));
                    return cita;
                } else {
                    throw new SQLException("Creating cita failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating cita", e);
        }
    }

    @Override
    public Optional<Cita> findById(Integer id) {
        String sql = "SELECT * FROM citas WHERE id = ?";
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setPacienteId(rs.getInt("paciente_id"));
                cita.setMedicoId(rs.getInt("medico_id"));
                cita.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                cita.setEstado(rs.getString("estado"));
                return Optional.of(cita);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding cita", e);
        }
    }

    @Override
    public List<Cita> findAll() {
        String sql = "SELECT * FROM citas";
        List<Cita> citas = new ArrayList<>();
        
        try (Connection conn = connectionDb.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setPacienteId(rs.getInt("paciente_id"));
                cita.setMedicoId(rs.getInt("medico_id"));
                cita.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                cita.setEstado(rs.getString("estado"));
                citas.add(cita);
            }
            return citas;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all citas", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM citas WHERE id = ?";
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting cita", e);
        }
    }

    @Override
    public Cita update(Cita cita) {
        String sql = "UPDATE citas SET paciente_id = ?, medico_id = ?, fecha_hora = ?, estado = ? WHERE id = ?";
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cita.getPacienteId());
            stmt.setInt(2, cita.getMedicoId());
            stmt.setTimestamp(3, Timestamp.valueOf(cita.getFechaHora()));
            stmt.setString(4, cita.getEstado());
            stmt.setInt(5, cita.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating cita failed, no rows affected.");
            }
            return cita;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating cita", e);
        }
    }

    @Override
    public List<Cita> findByPaciente(Integer pacienteId) {
        String sql = "SELECT * FROM citas WHERE paciente_id = ?";
        List<Cita> citas = new ArrayList<>();
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, pacienteId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setPacienteId(rs.getInt("paciente_id"));
                cita.setMedicoId(rs.getInt("medico_id"));
                cita.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                cita.setEstado(rs.getString("estado"));
                citas.add(cita);
            }
            return citas;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding citas by paciente", e);
        }
    }

    @Override
    public List<Cita> findByMedico(Integer medicoId) {
        String sql = "SELECT * FROM citas WHERE medico_id = ?";
        List<Cita> citas = new ArrayList<>();
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, medicoId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setPacienteId(rs.getInt("paciente_id"));
                cita.setMedicoId(rs.getInt("medico_id"));
                cita.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                cita.setEstado(rs.getString("estado"));
                citas.add(cita);
            }
            return citas;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding citas by medico", e);
        }
    }

    @Override
    public List<Cita> findByEstado(String estado) {
        String sql = "SELECT * FROM citas WHERE estado = ?";
        List<Cita> citas = new ArrayList<>();
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setPacienteId(rs.getInt("paciente_id"));
                cita.setMedicoId(rs.getInt("medico_id"));
                cita.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                cita.setEstado(rs.getString("estado"));
                citas.add(cita);
            }
            return citas;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding citas by estado", e);
        }
    }
}