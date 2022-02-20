/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.account;

import app.layout.BankFrameSwing;
import javax.swing.SwingUtilities;

/**
 *
 * @author Harun
 */
public class BankAccountTransfer {
    public static void main(String[] args) {
        BankFrameSwing frame = new BankFrameSwing();
        SwingUtilities.invokeLater(frame::showFrame);
        
    }
    
}
