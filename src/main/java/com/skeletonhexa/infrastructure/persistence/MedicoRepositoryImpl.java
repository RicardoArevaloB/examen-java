package com.skeletonhexa.infrastructure.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.skeletonhexa.domain.entities.Medico;
import com.skeletonhexa.domain.repository.Medicorepository;
import com.skeletonhexa.infrastructure.database.ConnectionDb;

public class MedicoRepositoryImpl implements Medicorepository {
    private final ConnectionDb connectionDb;

    public MedicoRepositoryImpl(ConnectionDb connectionDb) {
        this.connectionDb = connectionDb;
    }

    @Override
    public Medico save(Medico medico) {
        String sql = "INSERT INTO medicos (nombre, especialidad_id, horario_inicio, horario_fin) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, medico.getNombre());
            stmt.setInt(2, medico.getEspecialidadId());
            stmt.setString(3, medico.getHorarioInicio());
            stmt.setString(4, medico.getHorarioFin());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating medico failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    medico.setId(generatedKeys.getInt(1));
                    return medico;
                } else {
                    throw new SQLException("Creating medico failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating medico", e);
        }
    }

    @Override
    public Optional<Medico> findById(Integer id) {
        String sql = "SELECT * FROM medicos WHERE id = ?";
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Medico medico = new Medico();
                medico.setId(rs.getInt("id"));
                medico.setNombre(rs.getString("nombre"));
                medico.setEspecialidadId(rs.getInt("especialidad_id"));
                medico.setHorarioInicio(rs.getString("horario_inicio"));
                medico.setHorarioFin(rs.getString("horario_fin"));
                return Optional.of(medico);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding medico", e);
        }
    }

    @Override
    public List<Medico> findAll() {
        String sql = "SELECT * FROM medicos";
        List<Medico> medicos = new ArrayList<>();
        
        try (Connection conn = connectionDb.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Medico medico = new Medico();
                medico.setId(rs.getInt("id"));
                medico.setNombre(rs.getString("nombre"));
                medico.setEspecialidadId(rs.getInt("especialidad_id"));
                medico.setHorarioInicio(rs.getString("horario_inicio"));
                medico.setHorarioFin(rs.getString("horario_fin"));
                medicos.add(medico);
            }
            return medicos;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all medicos", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM medicos WHERE id = ?";
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting medico", e);
        }
    }

    @Override
    public Medico update(Medico medico) {
        String sql = "UPDATE medicos SET nombre = ?, especialidad_id = ?, horario_inicio = ?, horario_fin = ? WHERE id = ?";
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, medico.getNombre());
            stmt.setInt(2, medico.getEspecialidadId());
            stmt.setString(3, medico.getHorarioInicio());
            stmt.setString(4, medico.getHorarioFin());
            stmt.setInt(5, medico.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating medico failed, no rows affected.");
            }
            return medico;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating medico", e);
        }
    }

    @Override
    public List<Medico> findByEspecialidad(Integer especialidadId) {
        String sql = "SELECT * FROM medicos WHERE especialidad_id = ?";
        List<Medico> medicos = new ArrayList<>();
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, especialidadId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Medico medico = new Medico();
                medico.setId(rs.getInt("id"));
                medico.setNombre(rs.getString("nombre"));
                medico.setEspecialidadId(rs.getInt("especialidad_id"));
                medico.setHorarioInicio(rs.getString("horario_inicio"));
                medico.setHorarioFin(rs.getString("horario_fin"));
                medicos.add(medico);
            }
            return medicos;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding medicos by especialidad", e);
        }
    }
}