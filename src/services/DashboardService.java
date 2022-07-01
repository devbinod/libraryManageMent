package services;

import services.dao.IDashboard;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class DashboardService implements IDashboard {

    @Override
    public int getUserCount() {
        DataAccess dataAccess = new DataAccessFacade();
        return dataAccess.readUserMap().size();
    }
    @Override
    public int getBookCount() {
        DataAccess dataAccess = new DataAccessFacade();
        return dataAccess.readBooksMap().size();

    }

}
