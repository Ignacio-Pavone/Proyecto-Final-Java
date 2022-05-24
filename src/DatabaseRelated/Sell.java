package DatabaseRelated;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sell {
    private double operationNumber;
    private String customerName;
    private double totalAmmount;
    private LocalDateTime date;
    private String dateFormatted;
    private boolean invoiced;

    public Sell(){
        this.invoiced = false;
    }


    public Sell(String customerName, double totalAmmount) {

        this.operationNumber = Math.floor((Math.random() * 500 + 1));
        this.customerName = customerName;
        this.totalAmmount = totalAmmount;
        date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.dateFormatted = date.format(formatter);
    }

    public boolean isInvoiced() {
        return invoiced;
    }

    public void setInvoiced(boolean invoice) {
        this.invoiced = invoice;
    }

    public String getDateFormatted() {
        return dateFormatted;
    }

    public Double getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(Double operationNumber) {
        this.operationNumber = operationNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotalAmmount() {
        return totalAmmount;
    }

    public void setTotalAmmount(double totalAmmount) {
        this.totalAmmount = totalAmmount;
    }


    @Override
    public String toString() {
        return "Sell{" +
                "operationNumber=" + operationNumber +
                ", customerName='" + customerName + '\'' +
                ", totalAmmount=" + totalAmmount +
                ", date=" + date +
                ", dateFormatted='" + dateFormatted + '\'' +
                '}';
    }
}