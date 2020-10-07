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
public class ClientTo {
    private String name;
    private String email;

    public ClientTo(Client client) {
        this.name = client.getName();
        this.email = client.getEmail();
    }
}
