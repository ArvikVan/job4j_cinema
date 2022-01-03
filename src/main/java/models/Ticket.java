package models;

import java.util.Objects;

/**
 * @author ArvikV
 * @version 1.0
 * @since 02.01.2022
 */
public class Ticket {
    private int id;
    private int row;
    private int cell;
    private int accountId;

    public Ticket(int id, int row, int cell, int accountId) {
        this.id = id;
        this.row = row;
        this.cell = cell;
        this.accountId = accountId;
    }

    public Ticket(int row, int cell, int accountId) {
        this.row = row;
        this.cell = cell;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id && row == ticket.row && cell == ticket.cell && accountId == ticket.accountId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, row, cell, accountId);
    }
}