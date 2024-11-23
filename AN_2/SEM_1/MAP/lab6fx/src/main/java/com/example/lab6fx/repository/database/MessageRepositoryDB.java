package com.example.lab6fx.repository.database;

import com.example.lab6fx.domain.Message;
import com.example.lab6fx.domain.Prietenie;
import com.example.lab6fx.domain.Utilizator;
import com.example.lab6fx.domain.validator.UtilizatorValidator;
import com.example.lab6fx.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.List;

public class MessageRepositoryDB {
    private final String url;
    private final String username;
    private final String password;
    private UtilizatorRepositoryDB repository = new UtilizatorRepositoryDB(new UtilizatorValidator(), "jdbc:postgresql://localhost:5432/lab6", "postgres", "catalina");

    public MessageRepositoryDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void saveMessage(Message message) throws SQLException {
        String sql = "INSERT INTO messages (from_user_id, to_user_ids, message, data, reply_to_message_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, message.getFrom().getId());
            String toUserIds = String.join(",",
                    message.getTo().stream().map(u -> String.valueOf(u.getId())).toArray(String[]::new));
            statement.setString(2, toUserIds);

            statement.setString(3, message.getMessage());
            statement.setTimestamp(4, Timestamp.valueOf(message.getData()));

            if (message.getReply() != null) {
                statement.setLong(5, message.getReply().getId());
            } else {
                statement.setNull(5, Types.BIGINT);
            }

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Nu s-a putut salva mesajul " + e.getMessage(), e);
        }
    }

    public Iterable<Message> findAll() {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages;");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long fromUserId = resultSet.getLong("from_user_id");
                String toUserIds = resultSet.getString("to_user_ids");
                String messageText = resultSet.getString("message");
                LocalDateTime timestamp = resultSet.getTimestamp("data").toLocalDateTime();

                Utilizator from = repository.findById(fromUserId);
                List<Utilizator> to = Arrays.stream(toUserIds.split(","))
                        .map(Long::parseLong)
                        .map(id -> {
                            try {
                                return repository.findById(id);
                            } catch (SQLException e) {
                                throw new RuntimeException("Eroare la baza de date " + id, e);
                            }
                        })
                        .collect(Collectors.toList());

                Message message = new Message(from, to, messageText, timestamp);
                messages.add(message);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la încărcarea mesajelor: " + e.getMessage(), e);
        }
        return messages;
    }


}
