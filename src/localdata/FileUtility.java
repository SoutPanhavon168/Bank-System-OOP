package localdata;

import com.fasterxml.jackson.databind.ObjectMapper;

import bankaccount.BankAccount;
import transaction.Transaction;
import user.Customer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileUtility {

    private static final String CUSTOMER_FILE_PATH = "src/localdata/customers.json";
    private static final String BANKACCOUNT_FILE_PATH = "src/localdata/bankaccounts.json";
    private static final String TRANSACTION_FILE_PATH = "src/localdata/transactions.json";

    // Write any list to a specified file
    public static <T> void writeToFile(List<T> list, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read any list of objects from a specified file
    public static <T> List<T> readFromFile(String filePath, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Read from file and map it to the list of given type
            return mapper.readValue(new File(filePath),
                    mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Specific methods to handle Customer data
    public static List<Customer> readCustomersFromFile() {
        return readFromFile(CUSTOMER_FILE_PATH, Customer.class);
    }

    public static void writeCustomersToFile(List<Customer> customers) {
        writeToFile(customers, CUSTOMER_FILE_PATH);
    }

    // Specific methods to handle BankAccount data
    public static List<BankAccount> readBankAccountsFromFile() {
        return readFromFile(BANKACCOUNT_FILE_PATH, BankAccount.class);
    }

    public static void writeBankAccountsToFile(List<BankAccount> bankAccounts) {
        writeToFile(bankAccounts, BANKACCOUNT_FILE_PATH);
    }

    // Specific methods to handle Transaction data
    public static List<Transaction> readTransactionsFromFile() {
        return readFromFile(TRANSACTION_FILE_PATH, Transaction.class);
    }

    public static void writeTransactionsToFile(List<Transaction> transactions) {
        writeToFile(transactions, TRANSACTION_FILE_PATH);
    }

}
