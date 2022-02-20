/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.layout;

import app.entity.BankAccount;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
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
public class BankFrameSwing extends JFrame {

    private final JLabel fromBankAccountLabel = new JLabel("From :");
    private JComboBox<BankAccount> fromAccountComboBox = new JComboBox<>();

    private final JLabel toBankAccountLabel = new JLabel("To :");
    private JComboBox<BankAccount> toAccountComboBox = new JComboBox<>();

    private JLabel ammountLabel = new JLabel("Ammount");
    private JTextField ammountTextField = new JTextField(10);

    private JButton executeButton = new JButton("Execute");

    //private AbstractBankAccount abstractBankAccount = new AbstractBankAccount();
    public BankFrameSwing() {
        super("Bank Account Transfer");
        setSize(400, 300);
        setLayout(new GridLayout(4, 1));
        BankAccount.retriveAll().forEach(fromAccountComboBox::addItem);
        JPanel fromJPanel = new JPanel();
        fromJPanel.add(fromBankAccountLabel);
        fromJPanel.add(fromAccountComboBox);
        add(fromJPanel);
        BankAccount.retriveAll().forEach(toAccountComboBox::addItem);
        JPanel toJPanel = new JPanel();
        toJPanel.add(toBankAccountLabel);
        toJPanel.add(toAccountComboBox);
        add(toJPanel);
        JPanel amountPanel = new JPanel();
        amountPanel.add(ammountLabel);
        amountPanel.add(ammountTextField);
        add(amountPanel);
        JPanel buttnPanel = new JPanel();
        buttnPanel.add(executeButton);
        executeButton.addActionListener(this::executeTransfer);
        add(buttnPanel);

    }

    private void executeTransfer(ActionEvent e) {
        BankAccount fromAccount = (BankAccount) fromAccountComboBox.getSelectedItem();
        System.out.println("FROM: " + fromAccount);
        BankAccount toAccount = (BankAccount) toAccountComboBox.getSelectedItem();
        System.out.println("TO: " + toAccount);
        BigDecimal amount = new BigDecimal(ammountTextField.getText());
        //Ažuriram bazu odnosno tabelu u bazi
        BankAccount.transferMoney(fromAccount, toAccount, amount);

        //Ažuriram UI odnosno prozor
        fromAccountComboBox.removeAllItems();
        BankAccount.retriveAll().forEach(fromAccountComboBox::addItem);
        toAccountComboBox.removeAllItems();
        BankAccount.retriveAll().forEach(toAccountComboBox::addItem);
        
        System.out.println("FROM after transfer: " + fromAccount);
        System.out.println("TO after transfer: " + toAccount);
        
        fromAccountComboBox.setSelectedItem(fromAccount);
        toAccountComboBox.setSelectedItem(toAccount);
        ammountTextField.setText("");
    }

    public void showFrame() {
        pack();
        setVisible(true);
    }

}
