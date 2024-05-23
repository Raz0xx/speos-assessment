package com.devaleriola.speos_assessment.models;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetBasedPageRequest implements Pageable {
    private final int size;
    private final int from;

    // Constructor could be expanded if sorting is needed
    private final Sort sort = Sort.by(Sort.Direction.DESC, "id");

    public OffsetBasedPageRequest(int size, int from) {
        if (size < 1) {
            throw new IllegalArgumentException("size must not be less than one!");
        }
        if (from < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }
        this.size = size;
        this.from = from;
    }

    @Override
    public int getPageNumber() {
        return from / size;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public long getOffset() {
        return from;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetBasedPageRequest(getPageSize(), (int) (getOffset() + getPageSize()));
    }

    public Pageable previous() {
        return hasPrevious() ?
                new OffsetBasedPageRequest(getPageSize(), (int) (getOffset() - getPageSize())) : this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(getPageSize(), 0);
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return from > size;
    }
}