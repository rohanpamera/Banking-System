package bankerr123;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Bankjdbc {

	public static void main(String[] args)  throws Exception{
		// TODO Auto-generated method stub
		Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Driver is loaded");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "tiger");
        System.out.println("Connection is established");
        int c;
do {
        Scanner sc = new Scanner(System.in);
        int a;
        System.out.println("Enter the option\n1.Create an account\n2.Withdraw\n3.Deposit\n4.Balance Inquiry\n");
        a = sc.nextInt();

        if (a == 1) {
            PreparedStatement st = con.prepareStatement("INSERT INTO bank VALUES(?,?,?,?,?)");
            System.out.println("Enter the account number");
            int acc = sc.nextInt();
            System.out.println("Enter the password");
            int pass = sc.nextInt();
            System.out.println("Enter the name");
            String name = sc.next();
            System.out.println("Enter the balance");
            int balance = sc.nextInt();
            System.out.println("Enter the bank name");
            String bank_nam = sc.next();

            st.setInt(1, acc);
            st.setInt(2, pass);
            st.setString(3, name);
            st.setInt(4, balance);
            st.setString(5, bank_nam);
            st.execute();
            System.out.println("The account is created");
        }

        if (a == 2) {
            System.out.println("You have selected the withdrawal section\n");
            int b;
            System.out.println("Enter the money to be withdrawn");
            b = sc.nextInt();
            if (b < 0) {
                System.out.println("The money cannot be negative\n");
                b = sc.nextInt();
            }
            
            int accid;
            System.out.println("Enter the account id ");
            accid = sc.nextInt();
            PreparedStatement stm1 = con.prepareStatement("SELECT * FROM bank WHERE accno =?");
            stm1.setInt(1, accid);
            ResultSet res = stm1.executeQuery();
            if (res.next()) {
                int curbal = res.getInt("balance");
                System.out.println("The balance is " + curbal);
                if (curbal >= b) {
                    int resbal = curbal - b;
                    int pass = res.getInt("password");
                    System.out.println("Enter the password\n");
                    int pass1 = sc.nextInt();
                    if (pass == pass1) {
                        PreparedStatement stm2 = con.prepareStatement("UPDATE bank SET balance=? WHERE accno = ?");
                        stm2.setInt(1, resbal);
                        stm2.setInt(2, accid);
                        stm2.executeUpdate();
                        System.out.println("The money is withdrawn successfully");
                    } else {
                        System.out.println("The entered password is incorrect\n");
                    }
                } else {
                    System.out.println("The amount withdrawn is insufficient");
                }
            }
        }

        if (a == 3) {
            System.out.println("You have selected the deposit section\n");
            System.out.println("Enter the amount to be deposited\n");
            int depo = sc.nextInt();
            if(depo<0) {
            	System.out.println("The amount can not be negative so enter again\n");
            	 depo=sc.nextInt();
            }
            int accid;
            System.out.println("Enter the account id ");
            accid = sc.nextInt();
            PreparedStatement stm3 = con.prepareStatement("UPDATE bank SET balance=balance + ? WHERE accno=?");
            stm3.setInt(1, depo);
            stm3.setInt(2, accid);
            System.out.println("The amount is deposited");
            stm3.executeUpdate();
        }
if (a==4) {
	System.out.println("You have selected the BALANCE VIEW option\n");
	PreparedStatement stm4=con.prepareStatement("SELECT * FROM bank WHERE accno=?");
	System.out.println("Enter the account id ");
    int accid = sc.nextInt();
    stm4.setInt(1,accid);
    ResultSet res1=stm4.executeQuery();
    if(res1.next()) {
    	int pass1=res1.getInt("password");
    	System.out.println("Enter the password\n");
    	int pass=sc.nextInt();
    	if(pass1==pass) {
    	int bal1=res1.getInt("balance");
    	System.out.println("The balance is "+bal1);
    }
    	else {
    		System.out.println("The password is incorrect\n");
    	}
    }
}
System.out.println("Do you want to continue if yes type 1 no then type 0");
 c=sc.nextInt();
}while(c==1);
        con.close();
        System.out.println("The connection is closed\n");
    }

	}

}
