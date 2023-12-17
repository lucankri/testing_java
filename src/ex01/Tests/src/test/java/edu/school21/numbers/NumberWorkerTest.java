package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;


public class NumberWorkerTest {
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 7, 11, 13, 19, 23, 29, 47, 181, 10091})
    public void isPrimeForPrimes(int number) {
        Assertions.assertTrue(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 10, 12, 20, 24, 25, 26, 27, 28, 30, 180, 1024})
    public void isPrimeForNotPrimes(int number) {
        Assertions.assertFalse(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 0, -10, -2, -1000, -5, -134})
    public void isPrimeForIncorrectNumbers(int number) {
        Assertions.assertThrows(IllegalNumberException.class, () ->
                numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/data.csv"}, delimiter = ',')
    public void digitsSumTest(int number, int sum) {
        Assertions.assertEquals(numberWorker.digitsSum(number), sum);
    }

    private final NumberWorker numberWorker = new NumberWorker();

}
