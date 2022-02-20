package bank;

import bank.entity.BankAccount;
import bank.gui.BankFrame;
import bank.operator.BankAccountOperator;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 *
 * @author Harun
 */
public class BankApplication {

    public static void main(String[] args) {
        BankFrame bankFrame = new BankFrame();
        SwingUtilities.invokeLater(bankFrame::showframe);

        BankAccountOperator operator = new BankAccountOperator();
        List<BankAccount> bankAccounts = operator.loadBankAccounts();
       // bankAccounts.stream().forEach(System.out::println);

    }

}
