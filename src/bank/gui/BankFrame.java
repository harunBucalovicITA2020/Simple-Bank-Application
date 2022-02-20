/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank.gui;

import bank.entity.BankAccount;
import bank.operator.BankAccountOperator;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Harun
 */
public class BankFrame extends JFrame{

    private JLabel fromLabel = new JLabel("From :");
    private JLabel toLabel = new JLabel("To :");
    private JLabel amountLabel = new JLabel("Amount :");

    private JComboBox<BankAccount> fromAccountComboBox = new JComboBox<>();
    private JComboBox<BankAccount> toAccountComboBox = new JComboBox<>();

    private JTextField amountTexField = new JTextField(10);
    private JButton executeTransferButton = new JButton("Execute Transfer");

    private BankAccountOperator bankAccountOperator = new BankAccountOperator();

    public BankFrame() {
        super("Bank account transfer");

        setSize(400, 250);
        setLayout(new GridLayout(4, 1));
        List<BankAccount> accounts = bankAccountOperator.loadBankAccounts();
        
        JPanel fromPanel = new JPanel();
        fromPanel.add(fromLabel);
        fromPanel.add(fromAccountComboBox);
        accounts.stream().forEach(it -> fromAccountComboBox.addItem(it));
        add(fromPanel);
        JPanel toPanel = new JPanel();
        toPanel.add(toLabel);
        toPanel.add(toAccountComboBox);
        accounts.stream().forEach(toAccountComboBox::addItem);
        add(toPanel);
        JPanel amountPanel = new JPanel();
        amountPanel.add(amountLabel);
        amountPanel.add(amountTexField);
        add(amountPanel);
        JPanel buttonPanel = new JPanel();
        executeTransferButton.addActionListener(this::onExecuteTransferButtonClick);
        buttonPanel.add(executeTransferButton);
        add(buttonPanel);
    }

    public void showframe() {
        pack();
        setVisible(true);
    }
   
    
    private void onExecuteTransferButtonClick(ActionEvent actionEvent) {
        BankAccount fromBankAccount = (BankAccount) fromAccountComboBox.getSelectedItem();
        BankAccount toBankAccount = (BankAccount) toAccountComboBox.getSelectedItem();
        BigDecimal amount = new BigDecimal(amountTexField.getText());
        bankAccountOperator.transferMoney(fromBankAccount, toBankAccount, amount);

        //Update UI -> znaci da nakon sto izvrsim transfer osvjezim user interface sa novim stanjem nakon transfera
        fromAccountComboBox.removeAllItems();
        bankAccountOperator.loadBankAccounts().forEach(account -> fromAccountComboBox.addItem(account));

        toAccountComboBox.removeAllItems();
        bankAccountOperator.loadBankAccounts().forEach(toAccountComboBox::addItem);

        fromAccountComboBox.setSelectedItem(fromBankAccount);
        toAccountComboBox.setSelectedItem(toBankAccount);

        amountTexField.setText("");
       
        
    }
}
