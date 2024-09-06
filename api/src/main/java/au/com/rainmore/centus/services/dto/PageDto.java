package au.com.rainmore.centus.services.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.beans.Transient;
import java.util.List;
import java.util.Objects;

public record PageDto<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long total
) {
    public PageDto {
        Objects.requireNonNull(content);
    }

    public PageDto(List<T> content, Pageable pageable, long total) {
        this(content, pageable.getPageNumber(), pageable.getPageSize(), content.size());
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
