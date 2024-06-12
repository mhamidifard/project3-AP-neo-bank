package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OtherDataBase {
    private static List<OtherBanks> accounts=new ArrayList<>();

    public static OtherBanks findByAccNum(Long accNum){
        if(accNum==0){
            return null;
        }
        for (OtherBanks account : accounts){
            if(Objects.equals(account.getAccountNumber(), accNum)){
                return account;
            }
        }
        return null;
    }

    public static OtherBanks findByCardNum(Long cardNum){
        if(cardNum==0){
            return null;
        }
        for (OtherBanks account : accounts){
            if(Objects.equals(account.getCardNumber(), cardNum)){
                return account;
            }
        }
        return null;
    }

    public static List<OtherBanks> getAccounts() {
        return accounts;
    }

    public static void setAccounts() {
        accounts.add(new OtherBanks(12345L,1234582L,"amir","mohammadi","pasargad"));
        accounts.add(new OtherBanks(23456L,2345682L,"sam","nori","meli"));
        new OtherBanks(23456L,2345682L,"sam","nori","meli");
    }

}
