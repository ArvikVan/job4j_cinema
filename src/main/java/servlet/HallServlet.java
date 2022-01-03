package servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.google.gson.Gson;
import models.Account;
import models.Ticket;
import org.apache.log4j.Logger;
import store.DbStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author ArvikV
 * @version 1.0
 * @since 02.01.2022
 */
@WebServlet(urlPatterns = "/hall")
public class HallServlet extends HttpServlet {
    public final static Logger LOG = Logger.getLogger(HallServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Integer> list = new ArrayList<>(DbStore.instOf().findAllTicket());
        String json = new Gson().toJson(list);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = new PrintWriter(resp.getOutputStream());
        out.println(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        DbStore.instOf().saveAccount(new Account(
                        0, req.getParameter("name"), req.getParameter("phone")));
        int accountId = DbStore.instOf().findIdAccountByPhone(req.getParameter("phone"));
        try {
            boolean rsl = DbStore.instOf().saveTicket(
                    new Ticket(0, Integer.parseInt(req.getParameter("row")),
                            Integer.parseInt(req.getParameter("seat")), accountId));
            if (rsl) {
                resp.sendRedirect(req.getContextPath() + "/job4j_cinema/index.html");
            }
        } catch (SQLException e) {
            LOG.info("Exception doPost Servlet HallServlet " + e);
        }

    }
}