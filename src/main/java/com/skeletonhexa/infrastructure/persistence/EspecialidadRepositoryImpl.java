package com.skeletonhexa.infrastructure.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.skeletonhexa.domain.entities.Especialidadmedic;
import com.skeletonhexa.domain.repository.especialidadrepository;
import com.skeletonhexa.infrastructure.database.ConnectionDb;

public class EspecialidadRepositoryImpl implements especialidadrepository {

    private final ConnectionDb connectionDb;

    public EspecialidadRepositoryImpl(ConnectionDb connectionDb) {
        this.connectionDb = connectionDb;
    }

    @Override
    public Especialidadmedic save(Especialidadmedic especialidad) {
        String sql = "INSERT INTO especialidades (nombre) VALUES (?)";
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, especialidad.getNombre());
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    especialidad.setId(generatedKeys.getInt(1));
                }
            }
            return especialidad;
        } catch (SQLException e) {
            System.err.println("Error al guardar especialidad: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<Especialidadmedic> findById(Integer id) {
        String sql = "SELECT * FROM especialidades WHERE id = ?";
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Especialidadmedic especialidad = new Especialidadmedic();
                especialidad.setId(rs.getInt("id"));
                especialidad.setNombre(rs.getString("nombre"));
                return Optional.of(especialidad);
            }
            return Optional.empty();
        } catch (SQLException e) {
            System.err.println("Error al buscar especialidad: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Especialidadmedic> findAll() {
        String sql = "SELECT * FROM especialidades";
        List<Especialidadmedic> especialidades = new ArrayList<>();
        
        try (Connection conn = connectionDb.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Especialidadmedic especialidad = new Especialidadmedic();
                especialidad.setId(rs.getInt("id"));
                especialidad.setNombre(rs.getString("nombre"));
                especialidades.add(especialidad);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar especialidades: " + e.getMessage());
        }
        return especialidades;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM especialidades WHERE id = ?";
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar especialidad: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Especialidadmedic update(Especialidadmedic especialidad) {
        String sql = "UPDATE especialidades SET nombre = ? WHERE id = ?";
        
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, especialidad.getNombre());
            stmt.setInt(2, especialidad.getId());
            stmt.executeUpdate();
            return especialidad;
        } catch (SQLException e) {
            System.err.println("Error al actualizar especialidad: " + e.getMessage());
            return null;
        }
    }
}