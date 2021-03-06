package com.cb.examples.jpajsp.geeticket.controller;

import java.util.Date;
import java.util.List;

import com.cb.examples.jpajsp.geeticket.App;
import com.cb.examples.jpajsp.geeticket.model.Comment;
import com.cb.examples.jpajsp.geeticket.model.Ticket;
import com.cb.examples.jpajsp.geeticket.model.User;
import com.cb.examples.jpajsp.geeticket.repository.Comments;
import com.cb.examples.jpajsp.geeticket.repository.Users;
import com.cb.examples.jpajsp.geeticket.service.TicketService;
import com.geemvc.HttpMethod;
import com.geemvc.Results;
import com.geemvc.annotation.Controller;
import com.geemvc.annotation.Request;
import com.geemvc.bind.param.annotation.Data;
import com.geemvc.bind.param.annotation.Param;
import com.geemvc.bind.param.annotation.PathParam;
import com.geemvc.view.bean.Result;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
@Controller
@Request("/tickets")
public class TicketController {
    protected final App app;
    protected final TicketService ticketService;
    protected final Users users;
    protected final Comments comments;

    @Inject
    protected Injector injector;

    @Inject
    private TicketController(App app, TicketService ticketService, Users users, Comments comments) {
        this.app = app;
        this.ticketService = ticketService;
        this.users = users;
        this.comments = comments;
    }

    @Request("/")
    public Result viewTickets() {
        List<Ticket> tickets = ticketService.getTickets();
        return Results.view("ticket/list")
                .bind("tickets", tickets);
    }

    @Request("/details/{id}")
    public Result viewTicketDetails(@PathParam("id") Long ticketId) {
        Ticket ticket = ticketService.getTicket(ticketId);
        return Results.view("ticket/details")
                .bind("ticket", ticket)
                .bind("users", users.all());
    }

    @Request("/edit/{id}")
    public Result editTicketForm(@PathParam("id") Long ticketId) {
        Ticket ticket = ticketService.getTicket(ticketId);

        return Results.view("ticket/edit_form")
                .bind("ticket", ticket)
                .bind("users", users.all());
    }

    @Request(value = "/update/{id}", method = HttpMethod.POST)
    public Result updateTicket(@PathParam("id") Long ticketId, @Data Ticket ticket) {
        Ticket updatedTicket = ticketService.save(ticket);

        return Results.view("ticket/details")
                .bind("ticket", updatedTicket);
    }

    @Request(value = "/new-ticket")
    public Result newTicketForm() {
        return Results.view("ticket/new_form")
                .bind("users", users.all());
    }

    @Request(value = "/create-ticket", method = HttpMethod.POST)
    public Result createTicket(Ticket ticket) {

        if (ticket != null) {
            User assignee = users.havingId(ticket.getAssignee().getId());
            User reporter = users.havingId(ticket.getReporter().getId());
            ticket.setAssignee(assignee);
            ticket.setReporter(reporter);
            ticket.setCreatedOn(new Date());
            ticketService.save(ticket);
        }

        return Results.redirect("/tickets");
    }

    @Request("/delete/{id}")
    public Result deleteTicket(@Data Ticket ticket) {
        ticketService.remove(ticket);
        return Results.redirect("/tickets");
    }

    @Request(value = "/comment/add/{id}", method = HttpMethod.POST)
    public Result addComment(@PathParam("id") Long ticketId, @Param("comment") String comment, @Param("userId") Long userId) {
        Ticket ticket = ticketService.getTicket(ticketId);

        Comment c = injector.getInstance(Comment.class);

        if (userId != null) {
            User author = users.havingId(userId);
            if (author != null) {
                c.setUser(author);
                c.setComment(comment);
                c.setCreatedOn(new Date());
                ticket.addComment(c);
                comments.add(c);
                ticketService.save(ticket);
            }
        }

        return Results.redirect("/tickets/details/" + ticketId);
    }

}
