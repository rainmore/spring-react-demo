package au.com.rainmore.centus.services.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;

public record PageDto<T>(
        List<T> content,
        Pageable pageable,
        long total
) {
    public PageDto {
        Objects.requireNonNull(content);
        Objects.requireNonNull(pageable);
    }

    public PageDto(Page<T> page) {
        this(page.getContent(), page.getPageable(), page.getTotalElements());
    }

    public PageDto(List<T> content) {
        this(content, Pageable.ofSize(content.size()), content.size());
    }

    public PageDto(Pageable pageable) {
        this(Page.empty(pageable));
    }

}
