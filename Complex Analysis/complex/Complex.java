package complex;

public record Complex(double real, double imaginary) implements complex.ComplexInterface {
    public Complex add(Complex other) {
        return new Complex(real + other.real, imaginary + other.imaginary);
    }
    public Complex subtract(Complex other) {
        return new Complex(real - other.real, imaginary - other.imaginary);
    }
    public Complex multiply(Complex other) {
        return new Complex(real * other.real - imaginary * other.imaginary,
                real * other.imaginary + imaginary * other.real);
    }
    public Complex divide(Complex other){
        double denominator = other.real * other.real + other.imaginary * other.imaginary;
        return new Complex((real * other.real + imaginary * other.imaginary) / denominator,
                (imaginary * other.real - real * other.imaginary) / denominator);
    }
    public double mod(){
        return Math.sqrt(real * real + imaginary * imaginary);
    }
    public double arg(){
        return Math.atan2(imaginary, real);
    }
    public Complex conjugate(){
        return new Complex(real, -imaginary);
    }
    public Complex pow(double power){
        double modulus = Math.pow(mod(), power);
        double argument = power * arg();
        return new Complex(modulus * Math.cos(argument), modulus * Math.sin(argument));
    }
    public Complex reciprocal(){
        double denominator = real * real + imaginary * imaginary;
        return new Complex(real / denominator, -imaginary / denominator);
    }
    public Complex exp(){
        double expReal = Math.exp(real);
        return new Complex( expReal * Math.cos(imaginary), expReal * Math.sin(imaginary));
    }
    public Complex sin(){
        return new Complex(Math.sin(real) * Math.cosh(imaginary), Math.cos(real) * Math.sinh(imaginary));
    }
    public Complex cos(){
        return new Complex(Math.cos(real) * Math.cosh(imaginary), -Math.sin(real) * Math.sinh(imaginary));
    }
    public Complex log(double base){
        return new Complex(Math.log(mod()) / Math.log(base), arg() / Math.log(base));
    }

    @Override
    public String toString() {
        if(real == 0 && imaginary == 0) return "0.00";
        if(real == 0) return String.format("%.2fi", imaginary);
        if(imaginary == 0) return String.format("%.2f", real);
        String real_part = String.format("%.2f", real);
        String imaginary_part = imaginary < 0 ?
                String.format("- %.2fi", -imaginary) : String.format("+ %.2fi", imaginary);
        return real_part + " " + imaginary_part;
    }
}
