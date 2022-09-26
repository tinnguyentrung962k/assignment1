/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;
import tools.MyTool;
import java.text.ParseException;
/**
 *
 * @author Administrator
 */
public class Product implements Comparable<Product> {
    public static final String PRODUCT_FORMAT = "[a-zA-Z0-9\" \"]{5,100}";
    public static final String ID_FORMAT = "P\\d{3}";
    String productID;
    String name;
    double price;
    int quantity;
    String status;
    public static final char SEPARATOR = ',';

    public Product() {
        this.productID = "";
        this.name = "";
        this.price = 0.0;
        this.quantity = 0;
        this.status = "";
    }

    public Product(String productID, String name, double price, int quantity, String status) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }
    public Product(String line) throws ParseException{
        String[] parts = line.split(""+this.SEPARATOR);
        productID = parts[0].trim();
        name = parts[1].trim();
        price = MyTool.parseDouble(parts[2]);
        quantity = MyTool.parseInt(parts[3]);
        status = parts[4].trim();     
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString(){
        return productID + SEPARATOR + name + SEPARATOR + price + SEPARATOR + quantity + SEPARATOR + status;
    }

    @Override
    public int compareTo(Product p) {
        if(this.getQuantity()== p.getQuantity()){
            if (this.getPrice() > p.getPrice())
                return -1;
            else if (this.getPrice() == p.getPrice()){
                return 0;
            }
            else{
                return 1;
            }    
        } else if(this.getQuantity() > p.getQuantity()){
            return -1;
        }
        else{
            return 1;
        }
    }
}
