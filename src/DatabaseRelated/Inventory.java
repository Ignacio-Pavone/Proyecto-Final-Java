package DatabaseRelated;
import PersonRelated.Customer;
import PersonRelated.Supplier;
import UserRelated.Employee;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.*;


public class Inventory extends JDialog {

    private JPanel products1;
    private JTabbedPane sellTable;
    private JButton exitButton;
    private JButton modifyButton;
    private JTable productsTable;
    private JTextField codeField;
    private JTextField nameField;
    private JTextField stockField;
    private JTextField priceField;
    private JButton addButton;
    private JButton exitingButton;
    private JTextField updateID;
    private JTextField updatePrice;
    private JTextField updateName;
    private JTextField updateStock;
    private JButton updateButton;
    private JLabel nameText;
    private JPanel addProducts;
    private JLabel pricetxt;
    private JLabel codetxt;
    private JLabel stockLabel;
    private JButton deleteButton;
    private JTable cartTable;
    private JButton exitbutton1;
    private JTable clientProductList;
    private JButton exitUserList;
    private JButton addToCartButton;
    private JTextField userAmount;
    private JButton CONFIRMPURCHASEButton;
    private JScrollPane tabl;
    private JLabel ammountValueLabel;
    private JButton deleteCartElement;
    private JLabel textFinalPrice;
    private JPanel adminPanel;
    private JLabel finalPrice;
    private JButton exitButton2;
    private JTextField cantField;
    private JButton refreshButton;
    private Inventory productData;
    private JTable sellsTable;
    private JPanel addSells;
    private JButton GENERARFACTURAButton;
    private JTable supplierTable;
    private JButton addSupplierButton;
    private JTextField supplierNameField;
    private JTextField supplierPhoneField;
    private JTextField supplierIDField;
    private JTextField supplierWorkingArea;
    private JComboBox comboBox1;
    private JLabel supplierText;
    private JLabel nameLabel;
    private JLabel phoneLabel;
    private JLabel taxPayerLabel;
    private JLabel WorkigAreaLabel;
    private JTextField sellPriceField;
    private JLabel sellPriceLabel;
    private JTextField updateSupplier;
    private JTextField updateSellPrice;
    private JButton DELETEELEMENTButton;
    private JPanel supplierTab;
    private JLabel setAmountDay;
    private JLabel lineLabel1;
    private JLabel lineLabel2;
    private JButton deskClosing;
    private JTable statisticsTable;
    private JTable customerTable;
    private JTextField CnameCustomer;
    private JTextField CphoeNumberCustomer;
    private JTextField CtaxPayerIDCustomer;
    private JTextField CcategoryCustomer;
    private JButton ADDButton;
    private JLabel CnameLabel;
    private JLabel CtaxLabel;
    private JLabel CphoneLabel;
    private JLabel CcategoryLabel;
    private JComboBox comboBoxCustomers;
    private JLabel customerNameLAbel;
    private JButton xButton;
    private int rowSelection;
    private double ammountAcc;

    private Employee employee = new Employee();
    private HashMap<String, Product> productList = new HashMap<>(); // lista Productos
    private HashMap<String, Product> shopList = new HashMap<>(); // lista Carrito
    private ArrayList<Sell> sellsList = new ArrayList<>(); // lista ventas Concretadas
    private HashSet<Supplier> suppliersList = new HashSet<>(); // lista proveedores
    private ArrayList<Customer> customerList = new ArrayList<>(); // lista clientes
    private Collection<Product> mapTolist;
    private ArrayList<Product> finalProductPDF = new ArrayList<>();


    public Inventory(JFrame parent) {
        super(parent);

        hardCode();
        tableStyle();
        setMinimumSize(new Dimension(800, 700));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(products1);
        setUndecorated(true);
        setLocationRelativeTo(null);
        listingCollections();


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
                listProducts();
                listClientProducts();
                clearTextFields();
                productsTable.setRowSelectionAllowed(true);
                productsTable.setColumnSelectionAllowed(false);

            }
        });
        sellTable.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (!employee.isAdmin()) {
                    sellTable.remove(adminPanel);
                    sellTable.remove(addProducts);
                    sellTable.remove(addSells);
                    sellTable.remove(supplierTab);
                }
            }
        });
        productsTable.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

            }
        });
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rowSelection = productsTable.getSelectedRow();
                if (rowSelection != -1) {
                    rowData();
                } else {
                    JOptionPane.showMessageDialog(null, "Select a row");
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProductFromList();
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();

            }
        });
        deleteCartElement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProductFromCart();
            }
        });
        CONFIRMPURCHASEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmPruchase();
                setTotalDay();
            }
        });
        addSupplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSupplier();
            }
        });
        DELETEELEMENTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSupplierFromList();
            }
        });
        GENERARFACTURAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateInvoice();
            }
        });
        deskClosing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deskClosing();
            }
        });
        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void deskClosing() {
        double total = 0;
        int invoiceAmount = 0;
        if (allInvoiced()) {
            total = totalCaja();
            invoiceAmount = sellsList.size();
            listStadisticTable(total, invoiceAmount);
            sellsList.clear();
            listaVentas();
            setAmountDay.setText("Total");
        } else {
            JOptionPane.showMessageDialog(null, "No invoices");
        }
    }

    private void generateInvoice() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        rowSelection = sellsTable.getSelectedRow();
        if (rowSelection != -1) {
            dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                createInvoice((Double) sellsTable.getValueAt(rowSelection, 0), (String) sellsTable.getValueAt(rowSelection, 1), (Double) sellsTable.getValueAt(rowSelection, 2), (String) sellsTable.getValueAt(rowSelection, 3));
                isInvoiced((Double) sellsTable.getValueAt(rowSelection, 0));
                listaVentas();
            }
        }
    }

    private double totalCaja() {
        double acum = 0;
        for (Sell sell : sellsList) {
            acum += sell.getTotalAmmount();
        }
        return acum;
    }

    private boolean allInvoiced() {
        boolean flag = true;
        for (Sell sell : sellsList) {
            if (!sell.isInvoiced()) {
                flag = false;
            }
        }
        return flag;
    }

    private void isInvoiced(Double paymentProof) {
        for (Sell sell : sellsList) {
            if (sell.getOperationNumber().equals(paymentProof)) {
                sell.setInvoiced(true);
            }
        }
    }

    private boolean checkSupplierRequirements() {
        return !supplierIDField.getText().equals("") && !supplierNameField.getText().equals("") && !supplierPhoneField.getText().equals("") && !supplierWorkingArea.getText().equals("");
    }

    private void addCustomer() {
        Customer aux = new Customer();
        if (!chekCustomerFields()) {
            JOptionPane.showMessageDialog(null, "Please fill all the required fields");
        } else {
            aux.setName(CnameCustomer.getText());
            aux.setTaxpayerID(CtaxPayerIDCustomer.getText());
            aux.setPhoneNumber(CphoeNumberCustomer.getText());
            aux.setCategory(CcategoryCustomer.getText());
            customerList.add(aux);
            customerList();
            clearCustomerFields();
        }
        loadCustomerCombobox();
        listSuppliers();
    }

    private boolean chekCustomerFields() {
        return !CnameCustomer.getText().equals("") && !CtaxPayerIDCustomer.getText().equals("") && !CphoeNumberCustomer.getText().equals("") && !CcategoryCustomer.getText().equals("");
    }

    private void addSupplier() {
        Supplier aux = new Supplier();
        if (!checkSupplierRequirements()) {
            JOptionPane.showMessageDialog(null, "Please fill all the required fields");
        } else {
            aux.setName(supplierNameField.getText());
            aux.setTaxpayerID(supplierIDField.getText());
            aux.setPhoneNumber(supplierPhoneField.getText());
            aux.setWorkingArea(supplierWorkingArea.getText());
            suppliersList.add(aux);
            setComboBoxConfig();
            clearCustomerFields();
        }
        listSuppliers();
    }

    private void clearCustomerFields() {
        CnameCustomer.setText("");
        CtaxPayerIDCustomer.setText("");
        CphoeNumberCustomer.setText("");
        CcategoryCustomer.setText("");
    }

    private void setComboBoxConfig() {
        comboBox1.removeAllItems();
        Object[] arr = new Object[suppliersList.size()];
        arr = suppliersList.toArray();
        for (Object o : arr) {
            comboBox1.addItem(o);
        }
    }

    private void loadCustomerCombobox(){
        comboBoxCustomers.removeAllItems();
        Object[]arr = customerList.toArray();
        for (Object o:arr) {
            comboBoxCustomers.addItem(o);
        }
    }

    private void confirmPruchase() {
        Sell newSell = new Sell();
        if (tableHaveData()) {
            double amount = ammountAcc;
            Customer aux = (Customer) comboBoxCustomers.getSelectedItem();
            assert aux != null;
            String nameAux = aux.getName().toString();
            newSell = new Sell(nameAux, amount);
            if (!sellExist(newSell)) {
                sellsList.add(newSell);
                textFinalPrice.setText("Total Price");
                mapTolist = shopList.values();
                finalProductPDF = new ArrayList<>(mapTolist);
                shopList.clear();
            }
        }
        listCart();
        listaVentas();
    }

    private boolean tableHaveData() {
        return shopList.size() > 0;
    }

    private boolean sellExist(Sell aux) {
        boolean flag = false;
        for (Sell sell : sellsList) {
            if (sell.getOperationNumber().equals(aux.getOperationNumber()) && !flag) {
                flag = true;
            }
        }
        return flag;
    }

    protected void setEmployee(Employee employee) {
        this.employee = employee;
    }

    private void deleteProductFromList() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        String id = updateID.getText();
        if (!id.equals("")) {
            dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                deleteProduct(id);
                listProducts();
                listClientProducts();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a product");
        }
    }

    private void addToCart() {
        rowSelection = clientProductList.getSelectedRow();
        if (rowSelection != -1 && !ammountValueLabel.getText().equals("")) {
            cartProductsData();
            listCart();
            listProducts();
            listClientProducts();
            userAmount.setText("");
            setTotalPrice();
        } else {
            JOptionPane.showMessageDialog(null, "Select a row");
        }

    }

    private void setTotalPrice() {
        double totalprice = 0;
        for (int i = 0; i < cartTable.getRowCount(); i++) {
            totalprice = totalprice + Double.parseDouble(String.valueOf(cartTable.getValueAt(i, 5)));
        }
        textFinalPrice.setVisible(true);
        textFinalPrice.setText("" + totalprice);
        textFinalPrice.setForeground(Color.GREEN);
        ammountAcc = totalprice;
    }

    private void setTotalDay() {
        double totalprice = 0;
        for (int i = 0; i < sellsTable.getRowCount(); i++) {
            totalprice = totalprice + Double.parseDouble(String.valueOf(sellsTable.getValueAt(i, 2)));
        }
        setAmountDay.setVisible(true);
        setAmountDay.setText(""+totalprice);
        setAmountDay.setForeground(Color.GREEN);
    }

    private void deleteProductFromCart() {
        int row = 0;
        row = cartTable.getSelectedRow();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int newStock = 0;
        if (row != -1) {
            dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                String id = String.valueOf(cartTable.getValueAt(row, 0));
                String supplier = String.valueOf(cartTable.getValueAt(row, 1));
                String name = String.valueOf(cartTable.getValueAt(row, 2));
                int stock = Integer.parseInt(String.valueOf(cartTable.getValueAt(row, 3)));
                double price = Double.parseDouble(String.valueOf(cartTable.getValueAt(row, 4)));
                double sellPrice = Double.parseDouble(String.valueOf(cartTable.getValueAt(row, 4)));
                newStock = productStock(id) + stock;
                Product aux = new Product(id, supplier, name, newStock, price, sellPrice);
                deleteProductShop(id);
                productList.put(aux.getId(), aux);
                listProducts();
                listClientProducts();
                listCart();
                setTotalPrice();
            }
        }
    }

    private void deleteSupplierFromList() {
        int row = 0;
        row = supplierTable.getSelectedRow();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        if (row != -1) {
            dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                String name = String.valueOf(supplierTable.getValueAt(row, 0));
                String taxpayerID = String.valueOf(supplierTable.getValueAt(row, 1));
                String phoneNumber = String.valueOf(supplierTable.getValueAt(row, 2));
                String workingArea = String.valueOf(supplierTable.getValueAt(row, 3));
                Supplier aux = new Supplier(name, taxpayerID, phoneNumber, workingArea);
                deleteSupplier(aux.getName());
                listSuppliers();
                setComboBoxConfig();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a row");
        }
    }

    private int productStockShopList(String id) {
        int stock = 0;
        for (Map.Entry<String, Product> entry : shopList.entrySet()) {
            if (shopList.containsKey(id)) {
                stock = shopList.get(id).getStock();
            }
        }
        return stock;
    }

    private int productStock(String id) {
        int stock = 0;
        for (Map.Entry<String, Product> entry : productList.entrySet()) {
            if (productList.containsKey(id)) {
                stock = productList.get(id).getStock();
            }
        }
        return stock;
    }

    private void clearTextFields() {
        codeField.setText("");
        nameField.setText("");
        stockField.setText("");
        priceField.setText("");
        sellPriceField.setText("");
    }

    private void clearSupplierFields() {
        supplierNameField.setText("");
        supplierIDField.setText("");
        supplierPhoneField.setText("");
        supplierWorkingArea.setText("");
    }

    private void labelStyle() {
        textFinalPrice.setForeground(Color.WHITE);
        nameText.setForeground(Color.WHITE);
        codetxt.setForeground(Color.WHITE);
        pricetxt.setForeground(Color.WHITE);
        stockLabel.setForeground(Color.WHITE);
        ammountValueLabel.setForeground(Color.WHITE);
        supplierText.setForeground(Color.WHITE);
        nameLabel.setForeground(Color.WHITE);
        taxPayerLabel.setForeground(Color.WHITE);
        phoneLabel.setForeground(Color.WHITE);
        WorkigAreaLabel.setForeground(Color.WHITE);
        sellPriceLabel.setForeground(Color.WHITE);
        CnameLabel.setForeground(Color.WHITE);
        CphoneLabel.setForeground(Color.WHITE);
        CcategoryLabel.setForeground(Color.WHITE);
        CtaxLabel.setForeground(Color.WHITE);
        customerNameLAbel.setForeground(Color.WHITE);
        setAmountDay.setForeground(Color.WHITE);
    }

    private void tableStyle() {
        clientProductList.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        cartTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        productsTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        sellsTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        supplierTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        customerTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        statisticsTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        statisticsTable.getTableHeader().setBackground(Color.GRAY);
        supplierTable.getTableHeader().setBackground(Color.GRAY);
        customerTable.getTableHeader().setBackground(Color.GRAY);
        sellsTable.getTableHeader().setBackground(Color.GRAY);
        productsTable.getTableHeader().setBackground(Color.GRAY);
        cartTable.getTableHeader().setBackground(Color.GRAY);
        clientProductList.getTableHeader().setBackground(Color.GRAY);
    }

    private void deleteProductShop(String id) {
        if (shopList.containsKey(id)) {
            shopList.remove(id);
        }
    }

    private void deleteProduct(String id) {
        if (productList.containsKey(id)) {
            productList.remove(id);
            cleanLabels();
        } else {
            JOptionPane.showMessageDialog(null, "Select a product");
        }
    }

    private void deleteSupplier(String name) {
        Supplier aux = searchSupplier(name);
        if (suppliersList.contains(aux)) {
            suppliersList.remove(aux);
        } else {
            JOptionPane.showMessageDialog(null, "Select a product");
        }
    }

    private Supplier searchSupplier(String name) {
        for (Supplier s : suppliersList) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    private void cartProductsData() {
        int auxStock = 0;

        String id = String.valueOf(clientProductList.getValueAt(rowSelection, 0));
        String supplier = String.valueOf(clientProductList.getValueAt(rowSelection, 1));
        String name = String.valueOf(clientProductList.getValueAt(rowSelection, 2));
        int stock = Integer.parseInt(String.valueOf(clientProductList.getValueAt(rowSelection, 3))); // 7
        double price = Double.parseDouble(String.valueOf(clientProductList.getValueAt(rowSelection, 4)));
        double sellingPrice = Double.parseDouble(String.valueOf(clientProductList.getValueAt(rowSelection, 5)));
        int newStock = Integer.parseInt(userAmount.getText()); // 5

        if (newStock > 0 && newStock <= stock) {
            auxStock = stock - newStock;
            Product aux = new Product(id, supplier, name, newStock, price, sellingPrice);
            Product aux2 = new Product(id, supplier, name, auxStock, price, sellingPrice);
            if (shopList.containsKey(id)) {
                int stockAux = productStockShopList(id) + newStock;
                Product aux3 = new Product(id, supplier, name, stockAux, price, sellingPrice);
                shopList.put(aux3.getId(), aux3);
                productList.put(aux2.getId(), aux2);
            } else {
                shopList.put(aux.getId(), aux);
                productList.put(aux2.getId(), aux2);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Unavailable stock for this operation");
        }

    }

    private void rowData() {
        String id = String.valueOf(productsTable.getValueAt(rowSelection, 0));
        String supplier = String.valueOf(productsTable.getValueAt(rowSelection, 1));
        String name = String.valueOf(productsTable.getValueAt(rowSelection, 2));
        int stock = Integer.parseInt(String.valueOf(productsTable.getValueAt(rowSelection, 3)));
        double price = Double.parseDouble(String.valueOf(productsTable.getValueAt(rowSelection, 4)));
        double sellingPrice = Double.parseDouble(String.valueOf(productsTable.getValueAt(rowSelection, 5)));
        updateID.setText(id);
        updateSupplier.setText(supplier);
        updateName.setText(name);
        updateStock.setText(String.valueOf(stock));
        updatePrice.setText(String.valueOf(price));
        updateSellPrice.setText(String.valueOf(sellingPrice));
    }

    private void updateProduct() {
        String id = updateID.getText();
        if (!id.equals("")) {
            String name = updateName.getText();
            String supplier = updateSupplier.getText();
            int stock = Integer.parseInt(updateStock.getText());
            Double price = Double.parseDouble(updatePrice.getText());
            Double sellingPrice = Double.parseDouble(updateSellPrice.getText());
            Product aux = new Product(id, supplier, name, stock, price, sellingPrice);
            productList.put(aux.getId(), aux);
        } else {
            JOptionPane.showMessageDialog(null, "Select a product you want to modify");
        }
        cleanLabels();
        listClientProducts();
        listProducts();
    }

    private void cleanLabels() {
        updateID.setText("");
        updateName.setText("");
        updateStock.setText("");
        updatePrice.setText("");
    }

    private void addProduct() {
        try {
            Product data = new Product();
            data.setId(codeField.getText());
            Supplier aux = (Supplier) comboBox1.getSelectedItem();
            data.setSupplierName(aux.getName());
            data.setName(nameField.getText());
            data.setStock(Integer.parseInt(stockField.getText()));
            data.setPrice(Double.parseDouble(priceField.getText()));
            data.setSellingPrice(Double.parseDouble(sellPriceField.getText()));
            productList.put(data.getId(), data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void listSuppliers() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Name", "Taxpayer ID", "Phone", "Area"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Supplier s : suppliersList) {
            model.addRow(new Object[]{s.getName(), s.getTaxpayerID(), s.getPhoneNumber(), s.getWorkingArea()});
        }
        supplierTable.setModel(model);
    }

    private void listProducts() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Code", "Supplier", "Name", "Stock", "Price", "Sell Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Map.Entry<String, Product> entry : productList.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue().getSupplierName(), entry.getValue().getName(), entry.getValue().getStock(), +entry.getValue().getPrice(), entry.getValue().getSellingPrice()});
        }
        productsTable.setModel(model);
    }

    private void listStadisticTable(double total, int cantVentas) { //Borra las estadisticas si se genera una nueva factura.
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Total/Day", "N° Sales"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addRow(new Object[]{total, cantVentas});
        statisticsTable.setModel(model);
    }


    private void listCart() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Code", "Customer", "Name", "Ammount", "Unity price", "Total price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Map.Entry<String, Product> entry : shopList.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue().getSupplierName(), entry.getValue().getName(), entry.getValue().getStock(), entry.getValue().getSellingPrice(), entry.getValue().getSellingPrice() * entry.getValue().getStock()});
        }
        cartTable.setModel(model);
    }

    private void listClientProducts() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Code", "Supplier", "Name", "Stock", "Price", "Sell Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Map.Entry<String, Product> entry : productList.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue().getSupplierName(), entry.getValue().getName(), entry.getValue().getStock(), entry.getValue().getPrice(), entry.getValue().getSellingPrice()});
        }
        clientProductList.setModel(model);
    }

    private void listaVentas() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Operation Nº", "Client Name", "Total Ammount", "Date", "Facturado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (int i = 0; i < sellsList.size(); i++) {
            model.addRow(new Object[]{sellsList.get(i).getOperationNumber(), sellsList.get(i).getCustomerName(), sellsList.get(i).getTotalAmmount(), sellsList.get(i).getDateFormatted(), sellsList.get(i).isInvoiced()});
        }
        sellsTable.setModel(model);
    }

    private void customerList() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Name", "ID", "Phone Number", "Category"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (int i = 0; i < customerList.size(); i++) {
            model.addRow(new Object[]{customerList.get(i).getName(), customerList.get(i).getTaxpayerID(), customerList.get(i).getPhoneNumber(), customerList.get(i).getCategory()});
        }
        customerTable.setModel(model);
    }

    private void listingCollections() {
        labelStyle();
        tableStyle();
        setLocationRelativeTo(null);
        listProducts();
        listClientProducts();
        listCart();
        listSuppliers();
        listaVentas();
        customerList();
    }

    public void createInvoice(double operation, String customer, double price, String formattedDate) {
        PDDocument doc = new PDDocument();
        PDPage firstPage = new PDPage(PDRectangle.A4);
        doc.addPage(firstPage);
        String name = "Empresa S.A";
        String number = "223456789";
        String time = LocalDateTime.now().toString();
        int pagewidth = (int) firstPage.getTrimBox().getWidth();
        int pageHeight = (int) firstPage.getTrimBox().getHeight();

        try {
            PDPageContentStream contentStream = new PDPageContentStream(doc, firstPage);
            PDFTextClass pdfTextClass = new PDFTextClass(doc, contentStream);
            PDFont font = PDType1Font.COURIER;
            String[] contactInfo = new String[]{"nazarenoorodriguez@gmail.com", "ignaciopavone@gmail.com", "talliercioluis1@gmail.com"};
            pdfTextClass.addLineOfText("MYCOMPANY S.A", 250, pageHeight - 50, font, 20, Color.GREEN);
            pdfTextClass.addLineOfText("OPERATION N°: " + operation, 25, pageHeight - 100, font, 14, Color.BLACK);
            pdfTextClass.addLineOfText("CUSTOMER: " + customer, 25, pageHeight - 125, font, 14, Color.BLACK);
            pdfTextClass.addLineOfText("DATE: " + formattedDate, 25, pageHeight - 200, font, 14, Color.BLACK);
            pdfTextClass.addLineOfText("FINAL PRICE: $" + price, 25, pageHeight - 225, font, 14, Color.BLACK);
            PDFTableClass table = new PDFTableClass(doc, contentStream);

            int[] cellWidth = {130, 130, 130, 130};
            table.setTable(cellWidth, 30, 25, pageHeight - 350);
            table.setTableFont(font, 14, Color.BLACK);
            Color tableBodyColor = new Color(187, 187, 187);
            Color tableHeadColor = new Color(39, 114, 30);

            table.addCell("Item name", tableHeadColor);
            table.addCell("Item ammount", tableHeadColor);
            table.addCell("Unit price", tableHeadColor);
            table.addCell("Total price", tableHeadColor);


            for (Product product : finalProductPDF) {
                table.addCell(product.getName(), tableBodyColor);
                table.addCell(String.valueOf(product.getStock()), tableBodyColor);
                table.addCell(String.valueOf(product.getSellingPrice()), tableBodyColor);
                table.addCell(String.valueOf(product.getSellingPrice() * product.getStock()), tableBodyColor);
            }
            contentStream.close();
            String idConcat = "Operation N° "+operation +" " +customer;
            String namePDF = idConcat.concat(".pdf");
            doc.save(namePDF);
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hardCode() {
        Supplier aux = new Supplier("Fravega", "3333333", "155757575", "IT");
        Supplier aux1 = new Supplier("Compumundo", "6555555", "22333333", "IT");
        Supplier aux2 = new Supplier("Ribeiro", "11111111", "44444444", "IT");
        Supplier aux3 = new Supplier("Delta", "22222222", "2222222", "IT");
        suppliersList.add(aux);
        suppliersList.add(aux1);
        suppliersList.add(aux2);
        suppliersList.add(aux3);

        Product new1 = new Product("1", aux.getName(), "PC", 200, 70000.00, 100000.00);
        Product new2 = new Product("2", aux1.getName(), "KEYBOARD", 150, 5000.00, 5000.00);
        Product new3 = new Product("3", aux2.getName(), "MOUSE", 500, 3000.00, 4000.00);
        Product new4 = new Product("4", aux3.getName(), "HEADPHONES", 100, 6000.00, 8000.00);
        productList.put(new1.getId(), new1);
        productList.put(new2.getId(), new2);
        productList.put(new3.getId(), new3);
        productList.put(new4.getId(), new4);

        Customer auxC1 = new Customer("Juan","22233333","15550000","IT");
        Customer auxC2 = new Customer("Pedro","111111111","222222222","IT");
        Customer auxC3 = new Customer("Ignacio","555555555","3333333","IT");
        Customer auxC4 = new Customer("Naza","66666666","99999999","IT");
        customerList.add(auxC1);
        customerList.add(auxC2);
        customerList.add(auxC3);
        customerList.add(auxC4);
        setComboBoxConfig();
        loadCustomerCombobox();

    }
}






