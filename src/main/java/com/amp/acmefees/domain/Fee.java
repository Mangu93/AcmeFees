package com.amp.acmefees.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document
public class Fee {
    public String category;

    public List<String> fees;

    public Fee(String category, List<String> fees) {
        this.category = category;
        this.fees = fees;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getFees() {
        return fees;
    }

    public void setFees(List<String> fees) {
        this.fees = fees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fee fee = (Fee) o;
        return category.equals(fee.category) &&
            fees.equals(fee.fees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, fees);
    }
}
