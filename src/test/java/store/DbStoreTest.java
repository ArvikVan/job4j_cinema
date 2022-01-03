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
    public void whenCreateAccount() {
        Store store = DbStore.instOf();
        Account account = new Account(1, "Arv", "555-555-5655");
        Account account2 = new Account(2, "Mavr", "555-555-4444");
        store.saveAccount(account);
        store.saveAccount(account2);
        int accountInDb = store.findIdAccountByPhone(account.getPhone());
        assertThat(accountInDb, is(account.getId()));
    }

}