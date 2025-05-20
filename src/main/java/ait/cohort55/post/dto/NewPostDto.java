package ait.cohort55.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
//@NoArgsConstructor - можно не писать, если нет своего конструктора - по дефолту пустой
public class NewPostDto {
    private String title;
    private String content;
    private Set<String> tags;
}
