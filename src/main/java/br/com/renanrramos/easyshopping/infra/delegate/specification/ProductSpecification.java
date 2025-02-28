package br.com.renanrramos.easyshopping.infra.delegate.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
