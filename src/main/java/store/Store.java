package store;

import models.Account;
import models.Ticket;

import java.sql.SQLException;
import java.util.Collection;

/**
 * @author ArvikV
 * @version 1.0
 * @since 28.12.2021
 */
public interface Store {
    Collection<Integer> findAllTicket();
    boolean saveTicket(Ticket ticket) throws SQLException;
    void saveAccount(Account account);
    int findIdAccountByPhone(String phone);
    int checkFreePlace(int row, int seat);
    Ticket findTicketById(int id);
    Ticket createTicket(Ticket ticket) throws SQLException;
}
