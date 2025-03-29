package com.example.msauserservice.global.messaging;

import com.example.msauserservice.global.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEventDto extends Timestamped {
    private String userId; // PK = id
    private String username;
    private String email;
    private String eventType;
}
