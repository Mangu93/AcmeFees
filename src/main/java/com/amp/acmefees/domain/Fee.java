package com.amp.acmefees.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Document
public class Fee {
    @Id
    private CompositeKey id;

    public Fee(String category, List<String> fees) {
        this.id = new CompositeKey(category, fees);
    }

    public CompositeKey getId() {
        return id;
    }

    public void setId(CompositeKey id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fee fee = (Fee) o;
        return id.equals(fee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    static class CompositeKey implements Serializable {
        private String category;

        private List<String> fees;

        public CompositeKey(String category, List<String> fees) {
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
            CompositeKey that = (CompositeKey) o;
            return category.equals(that.category) &&
                fees.equals(that.fees);
        }

        @Override
        public int hashCode() {
            return Objects.hash(category, fees);
        }
    }
}
