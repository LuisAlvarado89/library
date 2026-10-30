package org.ies.tierno.library.model;

import java.math.BigDecimal;

public record Book(int id, String isbn, String title, String author, int year, BigDecimal purchasePrice) {

    public Object registrationDate() {
    }
}
