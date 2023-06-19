package org.polymul.service;

import org.polymul.domain.Polynomial;

public interface IProductMethod {
    Polynomial multiply(Polynomial lhs, Polynomial rhs);
}
