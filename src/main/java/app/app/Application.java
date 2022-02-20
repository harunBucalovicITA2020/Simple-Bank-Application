
package app.app;

import app.entity.BankAccount;
import java.math.BigDecimal;

public class Application {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount("779900121000", new BigDecimal("7000"));
        bankAccount.save();
        
    }
    
}
