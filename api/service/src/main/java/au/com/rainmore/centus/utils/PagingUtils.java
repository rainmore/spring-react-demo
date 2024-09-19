package au.com.rainmore.centus.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public class PagingUtils {

    private static final Integer DEFAULT_PAGE_SIZE = 20;
    private static final Integer DEFAULT_PAGE_NUMBER = 0;

    public static final Set<Integer> DEFAULT_PAGE_SIZE_OPTIONS = Set.of(DEFAULT_PAGE_SIZE, 50, 100);

    public static final Pageable DEFAULT_PAGEABLE = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
}
