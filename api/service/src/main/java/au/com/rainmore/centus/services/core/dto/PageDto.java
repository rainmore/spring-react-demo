package au.com.rainmore.centus.services.core.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.beans.Transient;
import java.util.List;
import java.util.Objects;

public record PageDto<T>(
        List<T> content,
        int size,
        int number,
        long totalPages,
        long totalElements,
        boolean empty,
        boolean first,
        boolean last
) {

    public PageDto {
        Objects.requireNonNull(content);
    }

    public PageDto(Page<T> page) {
        this(page.getContent(),
                page.getSize(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isEmpty(),
                page.isFirst(),
                page.isLast());
    }

    public PageDto(List<T> content, Pageable pageable, long total) {
        this(new PageImpl(content, pageable, total));
    }

    public PageDto(List<T> content) {
        this(new PageImpl(content));
    }

    public PageDto(Pageable pageable) {
        this(Page.empty(pageable));
    }

}
