package br.com.renanrramos.easyshopping.infra.delegate.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProductSpecification implements Specification<ProductFilter> {

    private final ProductFilter productFilter;

    @Override
    public Predicate toPredicate(final Root<ProductFilter> root,
                                 final CriteriaQuery<?> criteriaQuery, final CriteriaBuilder criteriaBuilder) {

        final List<Predicate> predicates = new ArrayList<>();

        if (productFilter.getStoreId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("storeId"), productFilter.getStoreId()));
        }
        if (productFilter.getName() != null && !productFilter.getName().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                    "%" + productFilter.getName().toLowerCase() + "%"));
        }
        if (productFilter.getCompanyId() != null && !productFilter.getCompanyId().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("companyId"), productFilter.getCompanyId()));
        }
        if (productFilter.getSubCategoryId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("subCategoryId"), productFilter.getSubCategoryId()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
