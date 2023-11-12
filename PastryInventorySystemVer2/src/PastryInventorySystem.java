import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PastryInventorySystem extends JFrame {
    private static final int STACK_SIZE = 32;  // Declare STACK_SIZE here

    private DefaultTableModel tableModel;
    private JTable pastryTable;

    public PastryInventorySystem() {
        super("Pastry Inventory System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        initializeUI();

        setVisible(true);
    }

    private void initializeUI() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Pastry Name");
        tableModel.addColumn("Quantity");

        pastryTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(pastryTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton consumeButton = new JButton("Consume Pastries");
        consumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consumePastries();
            }
        });

        JButton addButton = new JButton("Add Pastry");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPastry();
            }
        });

        JButton deleteButton = new JButton("Delete Pastry");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePastry();
            }
        });

        JButton editButton = new JButton("Edit Name");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editPastryName();
            }
        });

        buttonPanel.add(consumeButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void consumePastries() {
        int selectedRow = pastryTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int quantity = Integer.parseInt(tableModel.getValueAt(selectedRow, 1).toString());
                if (quantity > 0) {
                    JOptionPane.showMessageDialog(this, "Consume " + quantity + " pastries of " + tableModel.getValueAt(selectedRow, 0));
                    tableModel.setValueAt(0, selectedRow, 1);
                } else {
                    JOptionPane.showMessageDialog(this, "No pastries in the stack for " + tableModel.getValueAt(selectedRow, 0));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid quantity for " + tableModel.getValueAt(selectedRow, 0));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a pastry to consume.");
        }
    }

    private void addPastry() {
        String pastryName = JOptionPane.showInputDialog(this, "Enter the name for the new pastry");
        if (pastryName != null && !pastryName.trim().isEmpty()) {
            tableModel.addRow(new Object[]{pastryName, STACK_SIZE});
        }
    }

    private void deletePastry() {
        int selectedRow = pastryTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a pastry to delete.");
        }
    }

    private void editPastryName() {
        int selectedRow = pastryTable.getSelectedRow();
        if (selectedRow != -1) {
            String newName = JOptionPane.showInputDialog(this, "Enter new name for " + tableModel.getValueAt(selectedRow, 0));
            if (newName != null && !newName.trim().isEmpty()) {
                tableModel.setValueAt(newName, selectedRow, 0);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a pastry to edit.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PastryInventorySystem();
            }
        });
    }
}
