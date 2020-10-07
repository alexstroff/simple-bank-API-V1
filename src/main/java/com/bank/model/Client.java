package com.bank.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"accounts"})
public class Client{

    private Integer id;
    private String name;
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date registered = new Date();
    private List<Account> accounts;

    public Client(Client client) {
        this.id = client.id;
        this.name = client.name;
        this.email = client.email;
        this.registered = client.registered;
        this.accounts = client.accounts;
    }

    @JsonIgnore
    public boolean isNew() {
        return getId() == null;
    }
}
