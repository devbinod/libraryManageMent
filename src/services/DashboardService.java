package services;

import model.Book;
import services.dao.IDashboard;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.util.HashMap;
import java.util.Map;

public class DashboardService implements IDashboard {

    @Override
    public int getUserCount() {
        DataAccess dataAccess = new DataAccessFacade();
        return dataAccess.readUserMap().size();
    }

    @Override
    public int getBookCount() {
        DataAccess dataAccess = new DataAccessFacade();

        int count = 0;
        HashMap<String, Book> bookHashMap = dataAccess.readBooksMap();
        for (Map.Entry<String, Book> bookEntry : bookHashMap.entrySet()) {
            count++;
            count += bookEntry.getValue().getNumCopies();
        }
        return count;
    }

}
