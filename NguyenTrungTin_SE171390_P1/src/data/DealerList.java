/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;
import java.util.List;
import java.util.ArrayList;
import tools.MyTool;
import mng.LogIn;
/**
 *
 * @author ACER
 */
public class DealerList extends ArrayList<Dealer> {
    LogIn loginObj = null;
    private static final String PHONEPATTERN = "\\d{9}|\\d{11}";
    private String dataFile="";
    boolean changed = false;
    
    public DealerList(LogIn loginObj){
        this.loginObj=loginObj;    
    }
    private void loadDealerFromFile(){
        List<String> list = MyTool.readLinesFromFile(dataFile);
        for(String s: list){
            Dealer d = new Dealer(s);
            this.add(d);
        }
    }
    public void initWithFile(){
        Config cR = new Config();
        dataFile = cR.getDealerFile();
        loadDealerFromFile();   
    }
    public DealerList getContinuingList(){
        DealerList result = new DealerList(loginObj);
        for(Dealer d:this){
            if(d.isContinuing()){  
                result.add(d);
            }
        }
        return result; 
    }
    public DealerList getUncontinuingList(){
        DealerList result = new DealerList(loginObj);
        for(Dealer d:this){
            if(!d.isContinuing()){
                result.add(d);
            }
        }
        return result; 
    }
    private int searchDealer(String ID){
        for(int i=0;i<this.size();i++){
            if(this.get(i).getID().equals(ID)) {
                return i;
            }
        }
        return -1;
    }
    public void searchDealer(){
        String ID;
        ID = MyTool.readPattern("Enter ID of dealer to search", Dealer.ID_FORMAT);
        int pos = searchDealer(ID);
        if(pos<0){ 
            System.out.println("Not found!");
        }
        else {
            System.out.format("The %d(th) dealer is in this list!\n",pos);
        }
    }
    public void addDealer(){
        String ID;
        String name;
        String addr;
        String phone;
        boolean continuing;
        int pos;
        do{
            ID = MyTool.readPattern("ID of new dealer", Dealer.ID_FORMAT);
            ID = ID.toUpperCase();
            pos = searchDealer(ID);
            if(pos>=0){ 
                System.out.println("ID is duplicated!");
            }
        }while(pos>=0);
        name = MyTool.readNonBlank("Name of new dealer").toUpperCase();
        addr = MyTool.readNonBlank("Address of new dealer");
        phone = MyTool.readPattern("Phone number", Dealer.PHONE_FORMAT);
        continuing = true;
        Dealer d = new Dealer(ID, name, addr, phone, continuing);
        this.add(d);
        System.out.println("New dealer has been added.");
        changed = true;
    }
    public void removeDealer(){
        String ID;
        System.out.print("Enter the code of removed dealer: ");
        ID=MyTool.SC.nextLine().toUpperCase();
        int pos = searchDealer(ID);
        if(pos<0) System.out.println("Not found!");
        else
        {   this.get(pos).setContinuing(false);
            System.out.println("Removed");
            changed = true;
        }
    }
    public void updateDealer(){
        System.out.print("Dealer's ID needs updating: ");
        String ID = MyTool.SC.nextLine();
        int pos = searchDealer(ID);
        if(pos<0) System.out.println("Dealer " + ID + " not found!");
        else{
            Dealer d = this.get(pos);
            String newName="";
            System.out.print("new name, ENTER for omitting: ");
            newName = MyTool.SC.nextLine().trim().toUpperCase();
            if(!newName.isEmpty()){
                d.setName(newName);
                changed=true;
            }
            String newPhone="";
            System.out.print("new phone, ENTER for omitting: ");
            newPhone = MyTool.SC.nextLine();
            if(!newPhone.isEmpty()){
                if(MyTool.validStr(newPhone, Dealer.PHONE_FORMAT)){
                    d.setPhone(newPhone);
                    changed=true;
                }
            }
            String newAddr="";
            System.out.print("new address, ENTER for omitting: ");
            newAddr = MyTool.SC.nextLine();
            if(!newAddr.isEmpty()){
                d.setAddr(newAddr);
                changed=true;
            }
            
        }
    }
    public void printAllDealers(){
        if(this.isEmpty()){
            System.out.println("Empty List!");
        }
        else{
            System.out.println(this);
        }
    }
    public void printContinuingDealers(){
        this.getContinuingList().printAllDealers();
    }
    public void printUncontinuingDealers(){
        this.getUncontinuingList().printAllDealers();
    }
    public void writeDealerToFile(){
        if(changed){
            MyTool.writeFile(dataFile, this);
            changed = false;
        }
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    
}
