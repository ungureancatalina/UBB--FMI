package com.example.lab6fx.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Message extends Entity<Long> {
    private Utilizator from;
    private List<Utilizator> to;
    private String message;
    private LocalDateTime data;
    private Message reply;

    public Message(Utilizator from, List<Utilizator> to, String message, LocalDateTime data) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.data = data;
        this.reply = null;
    }

    public Utilizator getFrom() {
        return from;
    }

    public void setFrom(Utilizator from) {
        this.from = from;
    }

    public List<Utilizator> getTo() {
        return to;
    }

    public void setTo(List<Utilizator> to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Message getReply() {
        return reply;
    }

    public void setReply(Message reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from=" + from.getFirstName() + " " + from.getLastName() +
                ", to=" + to.stream().map(Utilizator::getFirstName).collect(Collectors.joining(", ")) +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", reply=" + (reply != null ? reply.getId() : "No reply") +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(id, message1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
