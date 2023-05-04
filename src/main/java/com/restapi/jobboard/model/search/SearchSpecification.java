package com.restapi.jobboard.model.search;

import com.restapi.jobboard.model.payload.request.FilterRequest;
import com.restapi.jobboard.model.payload.request.SearchRequest;
import com.restapi.jobboard.model.payload.request.SortRequest;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {

    @Serial
    private static final long serialVersionUID = 745345343320750644L;
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 100;

    private final transient SearchRequest request;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.equal(cb.literal(Boolean.TRUE), Boolean.TRUE);

        for (FilterRequest filter : this.request.getFilters()) {
            log.info("Filter: {} {} {}", filter.getKey(), filter.getOperator().toString(),
                    filter.getValue());
            predicate = filter.getOperator().build(root, cb, filter, predicate);
        }

        List<Order> orders = new ArrayList<>();
        for (SortRequest sort : this.request.getSorts()) {
            orders.add(sort.getDirection().build(root, cb, sort));
        }

        query.orderBy(orders);
        return predicate;
    }

    public static Pageable getPageable(Integer page, Integer size) {
        return PageRequest.of(Objects.requireNonNullElse(page, DEFAULT_PAGE),
                Objects.requireNonNullElse(size, DEFAULT_SIZE));
    }

}