package fr.usmb.m2isc.mesure.ejb;

import java.util.LinkedList;
import java.util.List;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import fr.usmb.m2isc.mesure.jpa.Mesure;

// non working example
@DataSourceDefinition (
    name = "java:app/env/jdbc/MyDataSource",
    className = "org.postgresql.ds.PGSimpleDataSource",
    user = "admin",
    password = "admin",
    serverName = "pgserver",
    portNumber = 5432,
    databaseName = "mesures")
// working example
// @DataSourceDefinition (
//     name = "java:app/env/jdbc/MyDataSource",
//     className = "org.postgresql.ds.PGSimpleDataSource",
//     properties = {"user=admin", "password=admin"},
//     url = "jdbc:postgresql://pgserver:5432/mesures")

@Stateless
@LocalBean
public class MesureEJB {
    @PersistenceContext
    private EntityManager em;
       
    /**
     * Constructeur sans parametre obligatoire
     */
    public MesureEJB() {
    }
    
    /**
     * Ajout d'une nouvelle mesure.
     * 
     * @param piece piece ou la mesure a ete faite
     * @param val temprerature mesuree
     * @return mesure ajoutee dans la base
     */
    public Mesure addMesure(String piece, double val) {
        Mesure m = new Mesure(piece, val);
        em.persist(m);
        return m;
    }

    /**
     * Recuperation d'une mesure a l'aide de son identifiant unique.
     * 
     * @param id identifiant de la mesure
     * @return mesure correspondant a l'identifiant ou null
     */
    public Mesure findMesure(long id) {
        Mesure m = em.find(Mesure.class, id);
        return m;
    }
    
    /**
     * Recuperation de la liste des mesures pour une piece donnee.
     * Les mesures sont triees par date, de la plus ancienne a la plus recente.
     * 
     * @param piece dont on veut la liste des mesures
     * @return liste de mesures
     */
    public List<Mesure> findMesures(String piece) {
        TypedQuery<Mesure> rq = em.createQuery("SELECT m FROM Mesure m WHERE m.piece = :piece ORDER BY m.dateMesure ASC", Mesure.class);
        rq.setParameter("piece", piece);
        return rq.getResultList();
    }

    /**
     * Recuperation de la derniere mesure pour une piece.
     * 
     * @param piece
     * @return
     */
    public Mesure findLastMesure(String piece) {
        TypedQuery<Mesure> rq = em.createQuery("SELECT m FROM Mesure m WHERE m.piece = :piece ORDER BY m.dateMesure DESC", Mesure.class);
        rq.setParameter("piece", piece);
        rq.setMaxResults(1);
        return rq.getSingleResult();
    }

    /**
     * Liste des dernieres mesures pour chaque piece.
     * 
     * @return
     */
    public List<Mesure> getLastMesures() {
        List<Mesure> res = new LinkedList<>();
        TypedQuery<String> rq = em.createQuery("SELECT DISTINCT m.piece FROM Mesure m group by m.piece", String.class);
        for(String p : rq.getResultList()) {
            Mesure m = findLastMesure(p);
                res.add(m);
        }
        return res;
    }
}
