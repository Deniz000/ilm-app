package com.ilim.app.business.validationhelper;


public interface Validator<T> {
    boolean validateById(Long id);
    boolean validateByName(String name);
    T getIfExistsById(Long id);
    T getIfExistsByName(String name);
}
