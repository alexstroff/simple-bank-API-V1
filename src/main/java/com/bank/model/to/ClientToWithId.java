package com.bank.model.to;

import com.bank.model.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientToWithId {
    private Integer id;
    private String name;
    private String email;

    public ClientToWithId(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.email = client.getEmail();
    }
}
