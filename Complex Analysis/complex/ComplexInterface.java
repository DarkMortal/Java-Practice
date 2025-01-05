package complex;

public interface ComplexInterface {
    Complex add(Complex other);
    Complex subtract(Complex other);
    Complex multiply(Complex other);
    Complex divide(Complex other);
    double mod();
    double arg();
    Complex conjugate();
    Complex pow(double power);
    Complex reciprocal();
    Complex exp();
    Complex sin();
    Complex cos();
    Complex log(double base);
}
