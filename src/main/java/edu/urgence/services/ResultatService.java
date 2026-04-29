package edu.urgence.services;

import edu.urgence.entities.Resultats;
import edu.urgence.utils.Myconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultatService {

    private Connection cnx;

    public ResultatService() {
        cnx = Myconnection.getInstance().getConnection();
    }

    // 🔹 INSERT
    public void ajouter(Resultats r) {
        String sql = "INSERT INTO resultats (id_demande, compte_rendu_texte, fichier_joint) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, r.getIdDemande());
            ps.setString(2, r.getCompteRendu());
            ps.setString(3, r.getFichierJoint());


            ps.executeUpdate();
            System.out.println("Résultat ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur ajout : " + e.getMessage());
        }

    }

    // 🔹 UPDATE
    public void modifier(Resultats r) {
        String sql = "UPDATE resultats SET id_demande=?, compte_rendu_texte=?, fichier_joint=? WHERE id_resultat=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, r.getIdDemande());
            ps.setString(2, r.getCompteRendu());
            ps.setString(3, r.getFichierJoint());
            ps.setInt(4, r.getIdResultat());

            ps.executeUpdate();
            System.out.println("Résultat modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur modification : " + e.getMessage());
        }
    }

    // 🔹 DELETE
    public void supprimer(int idResultat) {
        String sql = "DELETE FROM resultats WHERE id_resultat=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, idResultat);

            ps.executeUpdate();
            System.out.println("Résultat supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur suppression : " + e.getMessage());
        }
    }

    // 🔹 SELECT ALL
    public List<Resultats> afficher() {
        List<Resultats> list = new ArrayList<>();
        String sql = "SELECT * FROM resultats";

        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Resultats r = new Resultats();

                r.setIdResultat(rs.getInt("id_resultat"));
                r.setIdDemande(rs.getInt("id_demande"));
                r.setCompteRendu(rs.getString("compte_rendu_texte"));
                r.setFichierJoint(rs.getString("fichier_joint"));
                r.setDateResultat(rs.getTimestamp("date_resultat"));

                list.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Erreur affichage : " + e.getMessage());
        }

        return list;
    }

    // 🔹 SELECT BY ID
    public Resultats getById(int idResultat) {
        String sql = "SELECT * FROM resultats WHERE id_resultat=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, idResultat);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Resultats r = new Resultats();

                r.setIdResultat(rs.getInt("id_resultat"));
                r.setIdDemande(rs.getInt("id_demande"));
                r.setCompteRendu(rs.getString("compte_rendu_texte"));
                r.setFichierJoint(rs.getString("fichier_joint"));
                r.setDateResultat(rs.getTimestamp("date_resultat"));

                return r;
            }
        } catch (SQLException e) {
            System.out.println("Erreur recherche : " + e.getMessage());
        }

        return null;
    }
}
