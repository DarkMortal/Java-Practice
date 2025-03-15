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
    public Complex pow(Complex power){
        if(power.real == 0 && power.imaginary == 0)
            return new Complex(1, 0);
        if(power.imaginary == 0)
            return this.pow(power.real);
        return this.log(Math.E).multiply(power).exp();
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

    public String toString(int precision) {
        if(real == 0.0 && imaginary == 0.0) return "0.00";
        if(real == 0.0) return String.format("%." + precision + "fi", imaginary);
        if(imaginary == 0.0) return String.format("%." + precision + "f", real);
        if(imaginary == 1.0) return "(" + real + " + i)";
        String real_part = String.format("%." + precision + "f", real);
        String imaginary_part = imaginary < 0.0 ?
                String.format("- %.2fi", -imaginary) : String.format("+ %." + precision + "fi", imaginary);
        return "("+ real_part + " " + imaginary_part +")";
    }

    @Override
    public String toString() {
        return toString(2);
    }
}
