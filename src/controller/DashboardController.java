package controller;

import dao.IDashboard;
import services.DashboardService;

public class DashboardController implements IDashboard {

    IDashboard iDashboard = new DashboardService();

    @Override
    public int getUserCount() {
        return iDashboard.getUserCount();
    }

    @Override
    public int getBookCount() {
        return iDashboard.getBookCount();
    }
}
