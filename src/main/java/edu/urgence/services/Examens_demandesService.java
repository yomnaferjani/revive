package edu.urgence.services;

import edu.urgence.entities.Examens_demandes;
import edu.urgence.interfaces.IExamenCrud;
import edu.urgence.utils.Myconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Examens_demandesService implements IExamenCrud {

    private final Connection conn = Myconnection.getInstance().getConnection();

    private Examens_demandes map(ResultSet rs) throws SQLException {
        Examens_demandes e = new Examens_demandes();
        e.setIdDemande(rs.getInt("id_demande"));
        e.setIdConsultation(rs.getInt("id_consultation"));
        e.setTypeExamen(rs.getString("type_examen"));
        Timestamp ts = rs.getTimestamp("date_demande");
        if (ts != null) e.setDateDemande(new Date(ts.getTime()));
        e.setStatut(rs.getString("statut"));
        e.setUrgent(rs.getBoolean("urgent"));
        return e;
    }

    @Override
    public void ajouterExamen(Examens_demandes ex) {
        String sql = "INSERT INTO examens_demandes (id_consultation, type_examen, date_demande, statut, urgent) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ex.getIdConsultation());
            ps.setString(2, ex.getTypeExamen());
            ps.setTimestamp(3, ex.getDateDemande() != null
                    ? new Timestamp(ex.getDateDemande().getTime())
                    : new Timestamp(System.currentTimeMillis()));
            ps.setString(4, ex.getStatut() != null ? ex.getStatut() : "En attente");
            ps.setBoolean(5, ex.isUrgent());
            ps.executeUpdate();
            System.out.println("✅ Examen ajouté avec succès.");
        } catch (SQLException e) {
            System.err.println("❌ Erreur ajout examen : " + e.getMessage());
        }
    }

    @Override
    public void modifierExamen(Examens_demandes ex) {
        String sql = "UPDATE examens_demandes SET id_consultation=?, type_examen=?, " +
                "date_demande=?, statut=?, urgent=? WHERE id_demande=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ex.getIdConsultation());
            ps.setString(2, ex.getTypeExamen());
            ps.setTimestamp(3, new Timestamp(ex.getDateDemande().getTime()));
            ps.setString(4, ex.getStatut());
            ps.setBoolean(5, ex.isUrgent());
            ps.setInt(6, ex.getIdDemande());
            ps.executeUpdate();
            System.out.println("✅ Examen modifié.");
        } catch (SQLException e) {
            System.err.println("❌ Erreur modification examen : " + e.getMessage());
        }
    }

    @Override
    public void supprimerExamen(int idDemande) {
        String sql = "DELETE FROM examens_demandes WHERE id_demande=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDemande);
            ps.executeUpdate();
            System.out.println("✅ Examen supprimé.");
        } catch (SQLException e) {
            System.err.println("❌ Erreur suppression : " + e.getMessage());
        }
    }

    @Override
    public Examens_demandes getExamenById(int idDemande) {
        String sql = "SELECT * FROM examens_demandes WHERE id_demande=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDemande);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) {
            System.err.println("❌ Erreur getById : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Examens_demandes> getAllExamens() {
        List<Examens_demandes> list = new ArrayList<>();
        String sql = "SELECT * FROM examens_demandes ORDER BY date_demande DESC";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            System.err.println("❌ Erreur getAllExamens : " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Examens_demandes> getExamensByConsultation(int idConsultation) {
        List<Examens_demandes> list = new ArrayList<>();
        String sql = "SELECT * FROM examens_demandes WHERE id_consultation=? ORDER BY urgent DESC, date_demande DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idConsultation);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            System.err.println("❌ Erreur getByConsultation : " + e.getMessage());
        }
        return list;
    }

    @Override
    public void marquerRealise(int idDemande) {
        String sql = "UPDATE examens_demandes SET statut='Realise' WHERE id_demande=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDemande);
            ps.executeUpdate();
            System.out.println("✅ Examen marqué comme réalisé.");
        } catch (SQLException e) {
            System.err.println("❌ Erreur marquerRealise : " + e.getMessage());
        }
    }
}