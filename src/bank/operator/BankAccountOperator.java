package bank.operator;

import bank.entity.BankAccount;
import bank.util.HibernateUtil;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Harun
 */
public class BankAccountOperator {

    public BigDecimal showAccountState(String accountNumber) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        BankAccount bankAccount = (BankAccount) session.get(BankAccount.class, accountNumber);
        BigDecimal amount = bankAccount.getAmount();
        return amount;
    }

    public boolean transferMoney(BankAccount fromAccount, BankAccount toAccount, BigDecimal amount) {
        if (fromAccount == null || fromAccount.getAccountNumber() == null || fromAccount.getAccountNumber().isEmpty()) {
            return false;
        }
        if (toAccount == null || toAccount.getAccountNumber() == null || toAccount.getAccountNumber().isEmpty()) {
            return false;
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (fromAccount.getAccountNumber().equals(toAccount.getAccountNumber())) {
            return false;
        }

        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.getTransaction().begin();
            fromAccount.setAmount(fromAccount.getAmount().subtract(amount));
            toAccount.setAmount(toAccount.getAmount().add(amount));
            session.update(fromAccount);
            session.update(toAccount);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public List<BankAccount> loadBankAccounts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query createQuery = session.createQuery("from BankAccount");
        List<BankAccount> bankAccounts = createQuery.list();
        return bankAccounts;
    }

}
