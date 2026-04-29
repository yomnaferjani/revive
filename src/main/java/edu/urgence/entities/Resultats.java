package edu.urgence.entities;

import java.util.Date;

public class Resultats {

    private int idResultat;
    private int idDemande;
    private String compteRendu;
    private String fichierJoint;
    private Date dateResultat;

    public Resultats() {
    }

    public Resultats(int idDemande, String compteRendu, String fichierJoint) {
        this.idDemande = idDemande;
        this.compteRendu = compteRendu;
        this.fichierJoint = fichierJoint;
    }

    public Resultats(int idResultat, int idDemande, String compteRendu, String fichierJoint, Date dateResultat) {
        this.idResultat = idResultat;
        this.idDemande = idDemande;
        this.compteRendu = compteRendu;
        this.fichierJoint = fichierJoint;
        this.dateResultat = dateResultat;
    }

    public int getIdResultat() {
        return idResultat;
    }

    public void setIdResultat(int idResultat) {
        this.idResultat = idResultat;
    }

    public int getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(int idDemande) {
        this.idDemande = idDemande;
    }

    public String getCompteRendu() {
        return compteRendu;
    }

    public void setCompteRendu(String compteRendu) {
        this.compteRendu = compteRendu;
    }

    public String getFichierJoint() {
        return fichierJoint;
    }

    public void setFichierJoint(String fichierJoint) {
        this.fichierJoint = fichierJoint;
    }

    public Date getDateResultat() {
        return dateResultat;
    }

    public void setDateResultat(Date dateResultat) {
        this.dateResultat = dateResultat;
    }

    @Override
    public String toString() {
        return "Resultats{" +
                "idResultat=" + idResultat +
                ", idDemande=" + idDemande +
                ", compteRendu='" + compteRendu + '\'' +
                ", fichierJoint='" + fichierJoint + '\'' +
                ", dateResultat=" + dateResultat +
                '}';
    }

    private String etat;

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}