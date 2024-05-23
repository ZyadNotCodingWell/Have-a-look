package cyBooks;

import java.util.Set;
import java.sql.SQLException;

// Here's the whole update code, can someone please check the syntax, as I have doubts when it came to creating that instance of DataUpdater, if all goo, just put a trigger to it for each successful login
public class UpdateData {
    public static void updateDataMain() {
        try {
            DataParser dp = new DataParser();
            DatabaseUpdater du = new DatabaseUpdater();
            Set<Borrows> borrowsData = dp.parseBorrows(); // Set of all the borrows, casted into borrows instances
            for (Borrows borrow : borrowsData) {             // Loops through the set of borrows
                borrow.updateStatus();                      // Applies the class method to update all the necessary attributes
                du.updateBorrow(borrow);                    // Updates the concerned rows in the database to the new data
            }
        } catch (SQLException | BadStockArgumentException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }
}
