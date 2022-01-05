package store;

import models.Account;
import models.Ticket;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

/**
 * @author ArvikV
 * @version 1.0
 * @since 02.01.2022
 */
public class DbStore implements Store {
    private static final Logger LOG = LoggerFactory.getLogger(DbStore.class);
    private final BasicDataSource pool = new BasicDataSource();

private DbStore() {
    Properties cfg = new Properties();
    try (InputStream in = getClass().getClassLoader().getResourceAsStream("dbstore.properties")) {
        cfg.load(in);
    } catch (Exception e) {
        throw new IllegalStateException(e);
    }
    try {
        Class.forName(cfg.getProperty("jdbc.driver"));
    } catch (Exception e) {
        throw new IllegalStateException(e);
    }
    pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
    pool.setUrl(cfg.getProperty("jdbc.url"));
    pool.setUsername(cfg.getProperty("jdbc.username"));
    pool.setPassword(cfg.getProperty("jdbc.password"));
    pool.setMinIdle(5);
    pool.setMaxIdle(10);
    pool.setMaxOpenPreparedStatements(100);
        }

private static final class Lazy {
    private static final Store INST = new DbStore();
}

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Integer> findAllTicket() {
        Collection<Integer> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM ticket")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(it.getInt("row_ticket") * 10 + it.getInt("cell"));
                }
            }
        } catch (Exception e) {
            LOG.error("Error in FIND ALL TICKET method", e);
        }
        return tickets;
    }

    @Override
    public boolean saveTicket(Ticket ticket) throws SQLException {
        if (ticket.getId() == 0) {
            createTicket(ticket);
        }
        return false;
    }

    @Override
    public Ticket createTicket(Ticket ticket) throws SQLException {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO ticket (row_ticket, cell, account_id) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getRow());
            ps.setInt(2, ticket.getCell());
            ps.setInt(3, ticket.getAccountId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error("public Ticket createTicket(Ticket ticket)", e);
            throw e;
        }
        return ticket;
    }

    /*private void update(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE ticket SET row_ticket=?, cell=?, account_id=? WHERE id=?"
             )) {
            ps.setInt(1, ticket.getRow());
            ps.setInt(2, ticket.getCell());
            ps.setInt(3, ticket.getAccountId());
            ps.setInt(4, ticket.getId());

            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in TICKET UPDATE method.", e);
        }
    }*/

    @Override
    public void saveAccount(Account account) {
        if (account.getId() == 0) {
            create(account);
        } else {
            update(account);
        }
    }

    private Account create(Account account) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO account (username, phone) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            ps.setString(1, account.getName());
            ps.setString(2, account.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    account.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in ACCOUNT CREATE method.", e);
        }
        return account;
    }

    private void update(Account account) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE account SET username=?, phone=? WHERE id=?"
             )) {
            ps.setString(1, account.getName());
            ps.setString(2, account.getPhone());
            ps.setInt(3, account.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in ACCOUNT UPDATE method.", e);
        }
    }

    public int findIdAccountByPhone(String phone) {
        int result = 0;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM account WHERE phone=?"
             )) {
            ps.setString(1, phone);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = it.getInt("id");
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in FIND ID ACCOUNT BY PHONE.", e);
        }
        return result;
    }

    @Override
    public int checkFreePlace(int row, int seat) {
        int result = 0;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM ticket WHERE row_ticket=? and seat=?"
             )) {
            ps.setInt(1, row);
            ps.setInt(2, seat);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = it.getInt("id");
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in FIND ID ACCOUNT BY PHONE.", e);
        }
        return result;
    }

    @Override
    public Ticket findTicketById(int accountId) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM ticket WHERE account_id = ?")
        ) {
            ps.setInt(1, accountId);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Ticket(
                            it.getInt("id"),
                            it.getInt("row"),
                            it.getInt("cell"),
                            it.getInt("accountId")
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("public Candidate findByIdCandidate(int id)", e);
        }
        return null;
    }
}