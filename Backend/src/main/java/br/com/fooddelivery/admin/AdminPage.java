package br.com.fooddelivery.admin;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/** Stable API envelope, independent of Spring's Page serialization. */
public record AdminPage<T>(List<T> content, int number, int size, long totalElements, int totalPages) {
    public static PageRequest request(int page, int size, String field) {
        if (page < 0 || page > 100000 || size < 1 || size > 50)
            throw new IllegalArgumentException("Página inválida. Solicite entre 1 e 50 registros por página.");
        return PageRequest.of(page, size, Sort.by(field).ascending().and(Sort.by("id")));
    }
    public static <T> AdminPage<T> from(Page<T> page) {
        return new AdminPage<>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
