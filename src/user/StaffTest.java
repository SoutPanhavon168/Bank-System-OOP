package user;

import java.time.LocalDate;

public class StaffTest {
    public static void main(String[] args) {
        Staff x = new Staff("S","S","S@g.c","s","s","1234567890",LocalDate.of(2003,03,03),"123456789012",12,"Manager");
        System.out.println(x.getFirstName());
        x.viewSpecificbankAccount(1);
    }
    
}

