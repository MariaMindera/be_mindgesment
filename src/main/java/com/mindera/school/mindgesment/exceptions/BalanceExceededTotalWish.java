package com.mindera.school.mindgesment.exceptions;

/**
 * BalanceExceededTotalWish class extends RuntimeException and
 * exists to be used when the balance exceeds the total of a wish.
 */
public class BalanceExceededTotalWish extends RuntimeException {

    public BalanceExceededTotalWish(Long total, Long missingAmount) {
        super(String.format("The balance exceeded the total (%d). Enter a value less than or equal to %d.", total, missingAmount));
    }
}
