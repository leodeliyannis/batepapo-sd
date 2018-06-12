package br.com.diffiehellman.teste;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MysteryOfTheUniverse extends JFrame {

    JTextArea textArea;
    JButton solveButton;
    
    public MysteryOfTheUniverse() {
        super("Mystery of the Universe Solver");
        
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize(300, 300);
        
        textArea = new JTextArea();
        
        solveButton = new JButton("Solve Mystery");
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                solveButton.setEnabled(false); 
                solveMysteryOfTheUniverse();
            }
        });

        add(solveButton, BorderLayout.NORTH);
        add(new JScrollPane(textArea));
        add(new JButton("Click me! I'm not blocking."), 
            BorderLayout.SOUTH);
    }

    public void solveMysteryOfTheUniverse() {
        (new MysteryWorker()).execute();
    }

    class MysteryWorker extends SwingWorker<String, Object> {
       
        @Override
        public String doInBackground() {

            // Thinking for 4 seconds, but not blocking the UI
            try {
                Thread.currentThread().sleep(4000);
            } catch (InterruptedException ignore) {}   

            solveButton.setEnabled(true); 

            return "Egg salad";
        }

        @Override
        protected void done() {
            try {
                textArea.setText(get());
            } catch (Exception ignore) {}
        }
    }

    public static void main(String[] args) {
        new MysteryOfTheUniverse().setVisible(true);
    }
}
