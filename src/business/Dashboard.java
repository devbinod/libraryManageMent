package business;

public class Dashboard {

    private int totalNumberOfBook;

    private int totalNumberOfUser;

    public Dashboard(int totalNumberOfBook, int totalNumberOfUser) {
        this.totalNumberOfBook = totalNumberOfBook;
        this.totalNumberOfUser = totalNumberOfUser;
    }

    public int getTotalNumberOfBook() {
        return totalNumberOfBook;
    }

    public void setTotalNumberOfBook(int totalNumberOfBook) {
        this.totalNumberOfBook = totalNumberOfBook;
    }

    public int getTotalNumberOfUser() {
        return totalNumberOfUser;
    }

    public void setTotalNumberOfUser(int totalNumberOfUser) {
        this.totalNumberOfUser = totalNumberOfUser;
    }
}
