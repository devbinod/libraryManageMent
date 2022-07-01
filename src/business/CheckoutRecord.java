package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable {

    private static final long serialVersionUID = -3459882501123050153L;

    private List<CheckoutEntry> entries;
    private LibraryMember member;

    public List<CheckoutEntry> getEntries() {
        return entries;
    }

    CheckoutRecord(LibraryMember member) {
        this.member = member;
        entries = new ArrayList<>();
    }

    public void addCheckoutEntry(CheckoutEntry entry) {
        this.entries.add(entry);
    }

    public void removeEntry(CheckoutEntry entry) {
        this.entries.remove(entry);
    }
}
