/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Harun
 */
public abstract class AbstractBankAccount {

    static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("com.programmer.junior_BankApplication_jar_1.0PU");

    public void save() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(this);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

    }

    public void get() {
        try {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.find(BankAccount.class, this);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void delete() {
        try {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(this);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void update() {
        try {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(this);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<BankAccount> retriveAll() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            Query query = entityManager.createNamedQuery("BankAccount.findAll");
            entityTransaction.commit();
            List<BankAccount> bankAccounts = query.getResultList();
            return bankAccounts;
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    public static boolean transferMoney(BankAccount fromBankAccount, BankAccount toBankAccount, BigDecimal transferAmount) {
        if (fromBankAccount == null || toBankAccount == null) {
            return false;
        }
        if (transferAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (fromBankAccount.getAccountNumber().equals(toBankAccount.getAccountNumber())) {
            return false;
        }
        if (fromBankAccount.getAmount().compareTo(transferAmount) < 0) {
            System.err.println("Nema dovoljno sredstava na računu..");
            return false;
        }
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            BigDecimal newFromBankAccountAmount = fromBankAccount.getAmount().subtract(transferAmount);
            fromBankAccount.setAmount(newFromBankAccountAmount);
            BigDecimal newToBankAccountAmount = toBankAccount.getAmount().add(transferAmount);
            toBankAccount.setAmount(newToBankAccountAmount);
            //fromBankAccount.update(); 
            //toBankAccount.update(); može jednostavno pozivom ove funkcije koja je vecć kreirali
            entityManager.merge(fromBankAccount);
            entityManager.merge(toBankAccount);
            entityTransaction.commit();
            return true;
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException(e.getMessage());
        }
    }
}
