package com.my.Closet.DTO;

import com.my.Closet.model.Jersey;
import com.my.Closet.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class WishClosetDTO {
    private UUID id;
    final String name = "Wish Closet";
    private Boolean deleted;
    private List<Jersey> jerseys = new ArrayList<>();
    private User user;

    public boolean isEmpty() {
        Stream.of(/* list all fields */)
                .allMatch(field -> field == null);
        return true;
    }
}
