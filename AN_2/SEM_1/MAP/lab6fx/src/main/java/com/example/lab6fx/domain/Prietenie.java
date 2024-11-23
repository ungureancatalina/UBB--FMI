package com.example.lab6fx.domain;

import java.time.LocalDateTime;

public class Prietenie extends Entity<Tuple<Long,Long>> {


    Long idUser1;
    Long idUser2;
    LocalDateTime friendsfrom;

    public Prietenie()
    {
    }

    public void setFriendsfrom(LocalDateTime friendsfrom) {
        this.friendsfrom = friendsfrom;
    }

    public LocalDateTime getFriendsfrom()
    {
        return friendsfrom;
    }

}
