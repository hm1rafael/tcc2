// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package tcc2.portal.domain;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import tcc2.portal.domain.Desafio;

privileged aspect Desafio_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Desafio.entityManager;
    
    public static final List<String> Desafio.fieldNames4OrderClauseFilter = java.util.Arrays.asList("nomeDesafio", "dificuldade", "inputFile", "outputFile", "explicacaoDesafio", "quantidadeDeValidacoes", "desafioMetricas", "usuariosDesafios");
    
    public static final EntityManager Desafio.entityManager() {
        EntityManager em = new Desafio().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Desafio.countDesafios() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Desafio o", Long.class).getSingleResult();
    }
    
    public static List<Desafio> Desafio.findAllDesafios() {
        return entityManager().createQuery("SELECT o FROM Desafio o", Desafio.class).getResultList();
    }
    
    public static List<Desafio> Desafio.findAllDesafios(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Desafio o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Desafio.class).getResultList();
    }
    
    public static Desafio Desafio.findDesafio(Long id) {
        if (id == null) return null;
        return entityManager().find(Desafio.class, id);
    }
    
    public static List<Desafio> Desafio.findDesafioEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Desafio o", Desafio.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Desafio> Desafio.findDesafioEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Desafio o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Desafio.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Desafio.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Desafio.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Desafio attached = Desafio.findDesafio(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Desafio.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Desafio.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Desafio Desafio.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Desafio merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
