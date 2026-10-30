package org.ies.tierno.library.model;

import java.math.BigDecimal;

public record Book(long id, String isbn, String title, String author, int year, BigDecimal purchasePrice) {


}
