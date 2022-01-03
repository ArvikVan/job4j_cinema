package store;

import models.Account;
import models.Ticket;
import org.junit.Test;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * @author ArvikV
 * @version 1.0
 * @since 03.01.2022
 */
public class DbStoreTest {
    @Test
    public void whenCreateTicket() throws SQLException {
        Store store = DbStore.instOf();
        Ticket ticket = new Ticket(1, 1, 1, 1);
        store.saveTicket(ticket);
        Ticket ticketInDb = store.findTicketById(ticket.getAccountId());
        assertThat(ticketInDb.getAccountId(), is(ticket.getAccountId()));
    }

    @Test
    public void whenCreateAccount() {
        Store store = DbStore.instOf();
        Account account = new Account("Arv", "555-555-5655");
        Account account2 = new Account("Mavr", "555-555-4444");
        store.saveAccount(account);
        store.saveAccount(account2);
        int accountInDb = store.findIdAccountByPhone(account.getPhone());
        assertThat(accountInDb, is(account.getId()));
    }

}