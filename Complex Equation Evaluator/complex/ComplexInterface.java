package complex;

public interface ComplexInterface {
    Complex add(Complex other);
    Complex subtract(Complex other);
    Complex multiply(Complex other);
    Complex multiply(double number);
    Complex divide(Complex other);
    double mod();
    double arg();
    Complex conjugate();
    Complex pow(double power);
    Complex pow(Complex power);
    Complex reciprocal();
    Complex exp();
    Complex sin();
    Complex cos();
    Complex log(double base);
}
