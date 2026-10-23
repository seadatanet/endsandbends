package fr.ifremer.sismer.endsandbends.common;

/**
 * @author ATLANTIDE - ABT Classe d�crivant un nombre complexe
 */
public class Complex
{
    // Statiques des nombre sp�ciaux :
    public static Complex ZERO = new Complex(0.0, 0.0);
    public static Complex I = new Complex(0.0, 1.0);
    public static Complex ONE = new Complex(1.0, 0.0);
    public static Complex MINUS_ONE = new Complex(-1.0, 0.0);
    public static Complex MINUS_I = new Complex(0.0, -1.0);
    public static Complex HALF = new Complex(0.5, 0.0);
    public static Complex MINUS_HALF = new Complex(-0.5, 0.0);
    public static Complex HALF_I = new Complex(0.0, 0.5);
    public static Complex MINUS_HALF_I = new Complex(0.0, -0.5);
    public static Complex TWO = new Complex(2.0, 0.0);
    public static Complex MINUS_TWO = new Complex(-2.0, 0.0);
    public static Complex SQRT_HALF = new Complex(StrictMath.sqrt(0.5), 0.0);
    public static Complex SQRT_HALF_I = new Complex(0.0, StrictMath.sqrt(0.5));
    public static Complex MINUS_SQRT_HALF_I =
        new Complex(0.0, -StrictMath.sqrt(0.5));
    public static Complex PI = new Complex(StrictMath.PI, 0.0);
    public static Complex PI_I = new Complex(0.0, StrictMath.PI);
    public static Complex PI_2 = new Complex(StrictMath.PI / 2.0, 0.0);
    public static Complex MINUS_PI_2 = new Complex(-StrictMath.PI / 2.0, 0.0);
    public static Complex PI_2_I = new Complex(0.0, StrictMath.PI / 2.0);
    public static Complex MINUS_PI_2_I = new Complex(0.0, -StrictMath.PI / 2.0);
    public static double ZERO_TOL = 0.0;

    // donn�es membres :
    private double re = 0;
    private double im = 0;

    /**
     * Constructeur par d�faut
     */
    public Complex()
    {
    }

    /**
     * Constructeur par d�faut
     */
    public Complex(Complex other)
    {
        re = other.re;
        im = other.im;
    }

    /**
     * Constructs the complex number x+iy.
     * 
     * @param x
     *            the real value of a complex number.
     * @param y
     *            the imaginary value of a complex number.
     */
    public Complex(double x, double y)
    {
        re = x;
        im = y;
    }

    /**
     * Returns a string representing the value of this complex number.
     */
    public static String toString(double real, double imag)
    {
        StringBuffer buf = new StringBuffer(10);
        buf.append(real);
        if (imag >= 0.0)
            buf.append("+");
        buf.append(imag);
        buf.append("i");
        return buf.toString();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return toString(re, im);
    }

    /**
     * Constructs the complex number represented by a string.
     * 
     * @param s
     *            a string representing a complex number.
     * @exception NumberFormatException
     *                if the string does not contain a parsable number.
     */
    public Complex(String s) throws NumberFormatException
    {
        int iPos = s.indexOf('i');
        if (iPos == -1)
        {
            re = Double.parseDouble(s);
            im = 0.0;
        }
        else
        {
            String imStr;
            int signPos = s.indexOf('+', 1);
            if (signPos == -1)
                signPos = s.indexOf('-', 1);
            if (signPos == -1)
            {
                re = 0.0;
                imStr = s;
            }
            else
            {
                if (iPos < signPos)
                {
                    imStr = s.substring(0, signPos);
                    re = Double.parseDouble(s.substring(signPos + 1));
                }
                else
                {
                    re = Double.parseDouble(s.substring(0, signPos));
                    imStr = s.substring(signPos + 1);
                }
            }
            if (imStr.startsWith("i"))
                im = Double.parseDouble(imStr.substring(1));
            else if (imStr.endsWith("i"))
                im = Double.parseDouble(imStr.substring(0, imStr.length() - 1));
        }
    }

    /**
     * Creates a complex number with the given modulus and argument.
     * 
     * @param mod
     *            the modulus of a complex number.
     * @param arg
     *            the argument of a complex number.
     */
    public static Complex polar(double mod, double arg)
    {
        Complex z =
            new Complex(mod * StrictMath.cos(arg), mod * StrictMath.sin(arg));
        return z;
    }

    /**
     * Compares two complex numbers for equality.
     * 
     * @param obj
     *            a complex number.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj != null && (obj instanceof Complex))
        {
            Complex z = (Complex) obj;
            return equals(z.re, z.im);
        }

        return false;
    }

    // [TNU] Si la m�thode equals() est surcharg�e, il faut aussi surcharger la
    // m�thode hashCode() car 2 objets qui sont �gaux doivent avoir le m�me
    // hashcode
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        // on met le complexe sous forme de chaine "x + y i" et on retourne le
        // hashCode de cette cha�ne
        String stringValue =
            new StringBuilder().append(re).append("+").append(im).append("i")
                .toString();

        return stringValue.hashCode();
    }

    // [/TNU]

    /**
     * Compares two complex numbers for equality.
     */
    public boolean equals(double real, double imag)
    {
        return StrictMath.abs(re - real) <= ZERO_TOL
                && StrictMath.abs(im - imag) <= ZERO_TOL;
    }

    /**
     * Returns true if either the real or imaginary part is NaN.
     */
    public boolean isNaN()
    {
        // [TNU] FindBugs : This code checks to see if a floating point value is
        // equal to the special Not A Number value (e.g., if (x == Double.NaN)).
        // However, because of the special semantics of NaN, no value is equal
        // to Nan, including NaN. Thus, x == Double.NaN always evaluates to
        // false. To check to see if a value contained in x is the special Not A
        // Number value, use Double.isNaN(x) (or Float.isNaN(x) if x is floating
        // point precision).

// return (re == Double.NaN) || (im == Double.NaN);

        return (Double.isNaN(re)) || (Double.isNaN(im));
        // [/TNU]
    }

    /**
     * Returns true if either the real or imaginary part is infinite.
     */
    public boolean isInfinite()
    {
        return (re == Double.POSITIVE_INFINITY)
                || (re == Double.NEGATIVE_INFINITY)
                || (im == Double.POSITIVE_INFINITY)
                || (im == Double.NEGATIVE_INFINITY);
    }

    /**
     * Returns the real part of this complex number.
     */
    public double real()
    {
        return re;
    }

    /**
     * Returns the imaginary part of this complex number.
     */
    public double imag()
    {
        return im;
    }

    public void setReal(double real)
    {
        re = real;
    }

    public void setImag(double imag)
    {
        im = imag;
    }

    public void set(double real, double imag)
    {
        re = real;
        im = imag;
    }

    public void set(Complex o)
    {
        re = o.real();
        im = o.imag();
    }

    /**
     * Returns the modulus of this complex number.
     */
    public double mod()
    {
        return mod(re, im);
    }

    public static double mod(double real, double imag)
    {
        double reAbs = StrictMath.abs(real);
        double imAbs = StrictMath.abs(imag);
        if (reAbs == 0.0 && imAbs == 0.0)
            return 0.0;
        else if (reAbs < imAbs)
            return imAbs * StrictMath.sqrt(1.0 + (real / imag) * (real / imag));
        else
            return reAbs * StrictMath.sqrt(1.0 + (imag / real) * (imag / real));
    }

    /**
     * Returns the argument of this complex number.
     */
    public double arg()
    {
        return arg(re, im);
    }

    private static double arg(double real, double imag)
    {
        return StrictMath.atan2(imag, real);
    }

    /**
     * Returns the C<sup>*</sup> norm.
     */
    public double norm()
    {
        return mod();
    }

    // ============
    // OPERATIONS
    // ============

    /**
     * Returns the negative of this complex number.
     */
    public Complex negate()
    {
        return new Complex(-re, -im);
    }

    /**
     * Returns the inverse of this complex number.
     */
    public Complex inverse()
    {
        double denominator, real, imag;
        if (StrictMath.abs(re) < StrictMath.abs(im))
        {
            real = re / im;
            imag = -1.0;
            denominator = re * real + im;
        }
        else
        {
            real = 1.0;
            imag = -im / re;
            denominator = re - im * imag;
        }
        return new Complex(real / denominator, imag / denominator);
    }

    /**
     * Returns the involution of this complex number.
     */
    public Complex involution()
    {
        return conjugate();
    }

    /**
     * Returns the complex conjugate of this complex number.
     */
    public Complex conjugate()
    {
        return new Complex(re, -im);
    }

    /**
     * Returns the addition of this complex number and another.
     * 
     * @param z
     *            a complex number.
     */
    public Complex add(Complex z)
    {
        return new Complex(re + z.re, im + z.im);
    }

    /**
     * Returns the addition of this complex number with a real part.
     * 
     * @param real
     *            a real part.
     */
    public Complex addReal(double real)
    {
        return new Complex(re + real, im);
    }

    /**
     * Returns the addition of this complex number with an imaginary part.
     * 
     * @param imag
     *            an imaginary part.
     */
    public Complex addImag(double imag)
    {
        return new Complex(re, im + imag);
    }

    /**
     * Returns the subtraction of this complex number by another.
     * 
     * @param z
     *            a complex number.
     */
    public Complex subtract(Complex z)
    {
        return new Complex(re - z.re, im - z.im);
    }

    /**
     * Returns the subtraction of this complex number by a real part.
     * 
     * @param real
     *            a real part.
     */
    public Complex subtractReal(double real)
    {
        return new Complex(re - real, im);
    }

    /**
     * Returns the subtraction of this complex number by an imaginary part.
     * 
     * @param imag
     *            an imaginary part.
     */
    public Complex subtractImag(double imag)
    {
        return new Complex(re, im - imag);
    }

    /**
     * Returns the multiplication of this complex number and another.
     * 
     * @param z
     *            a complex number.
     */
    public Complex multiply(Complex z)
    {
        return new Complex(re * z.re - im * z.im, re * z.im + im * z.re);
    }

    /**
     * Returns the multiplication of this complex number by a scalar.
     * 
     * @param x
     *            a real number.
     */
    public Complex multiply(double x)
    {
        return new Complex(x * re, x * im);
    }

    /**
     * Returns the division of this complex number by another.
     * 
     * @param z
     *            a complex number.
     * @exception ArithmeticException
     *                If divide by zero.
     */
    public Complex divide(Complex z)
    {
        double denominator, real, imag, a;
        if (StrictMath.abs(z.re) < StrictMath.abs(z.im))
        {
            a = z.re / z.im;
            denominator = z.re * a + z.im;
            real = re * a + im;
            imag = im * a - re;
        }
        else
        {
            a = z.im / z.re;
            denominator = z.re + z.im * a;
            real = re + im * a;
            imag = im - re * a;
        }
        return new Complex(real / denominator, imag / denominator);
    }

    /**
     * Returns the division of this complex number by a scalar.
     * 
     * @param x
     *            a real number.
     * @exception ArithmeticException
     *                If divide by zero.
     */
    public Complex divide(double x)
    {
        return new Complex(re / x, im / x);
    }

    /**
     * Returns this complex number raised to the power of another.
     * 
     * @param z
     *            a complex number.
     */
    public Complex pow(Complex z)
    {
        double thisMod = mod();
        double thisArg = arg();
        double r =
            StrictMath.pow(thisMod, z.re) / StrictMath.exp(thisArg * z.im);
        double a = thisArg * z.re + StrictMath.log(thisMod) * z.im;
        return polar(r, a);
    }

    /**
     * Returns this complex number raised to the power of a scalar.
     * 
     * @param x
     *            a real number.
     */
    public Complex pow(double x)
    {
        return polar(StrictMath.pow(mod(), x), arg() * x);
    }

    /**
     * Returns the square of this complex number.
     */
    public Complex sqr()
    {
        return new Complex(re * re - im * im, 2.0 * re * im);
    }

    /**
     * Returns the square root of this complex number.
     */
    public Complex sqrt()
    {
        return polar(StrictMath.sqrt(mod()), arg() / 2.0);
    }

    private static Complex sqrt(double real, double imag)
    {
        return polar(StrictMath.sqrt(mod(real, imag)), arg(real, imag) / 2.0);
    }

    /**
     * Returns the exponential number e (2.718...) raised to the power of a
     * complex number.
     * 
     * @param z
     *            a complex number.
     */
    public static Complex exp(Complex z)
    {
        return new Complex(StrictMath.exp(z.re) * StrictMath.cos(z.im),
            StrictMath.exp(z.re) * StrictMath.sin(z.im));
    }

    /**
     * Returns the natural logarithm (base e) of a complex number.
     * 
     * @param z
     *            a complex number.
     */
    public static Complex log(Complex z)
    {
        return new Complex(StrictMath.log(z.mod()), z.arg());
    }

    private static Complex log(double real, double imag)
    {
        return new Complex(StrictMath.log(mod(real, imag)), arg(real, imag));
    }

    private static Complex log_2(double real, double imag)
    {
        return new Complex(StrictMath.log(mod(real, imag)) / 2.0, arg(real,
            imag) / 2.0);
    }

    private static Complex log_2I(double real, double imag)
    {
        return new Complex(arg(real, imag) / 2.0, -StrictMath.log(mod(real,
            imag)) / 2.0);
    }

    private static Complex log_2IplusPI_2(double real, double imag)
    {
        return new Complex((arg(real, imag) + StrictMath.PI) / 2.0, -StrictMath
            .log(mod(real, imag)) / 2.0);
    }

    /**
     * Returns the trigonometric sine of a complex angle.
     * 
     * @param z
     *            an angle that is measured in radians.
     */
    public static Complex sin(Complex z)
    {
        return new Complex(StrictMath.sin(z.re) * ExtraMath.cosh(z.im),
            StrictMath.cos(z.re) * ExtraMath.sinh(z.im));
    }

    /**
     * Returns the trigonometric cosine of a complex angle.
     * 
     * @param z
     *            an angle that is measured in radians.
     */
    public static Complex cos(Complex z)
    {
        return new Complex(StrictMath.cos(z.re) * ExtraMath.cosh(z.im),
            -StrictMath.sin(z.re) * ExtraMath.sinh(z.im));
    }

    /**
     * Returns the trigonometric tangent of a complex angle.
     * 
     * @param z
     *            an angle that is measured in radians.
     */
    public static Complex tan(Complex z)
    {
        double sinRe = StrictMath.sin(z.re);
        double cosRe = StrictMath.cos(z.re);
        double sinhIm = ExtraMath.sinh(z.im);
        double coshIm = ExtraMath.cosh(z.im);
        double denom =
            cosRe * cosRe * coshIm * coshIm + sinRe * sinRe * sinhIm * sinhIm;
        return new Complex(sinRe * cosRe / denom, sinhIm * coshIm / denom);
    }

    /**
     * Returns the hyperbolic sine of a complex number.
     * 
     * @param z
     *            a complex number.
     */
    public static Complex sinh(Complex z)
    {
        return new Complex(ExtraMath.sinh(z.re) * StrictMath.cos(z.im),
            ExtraMath.cosh(z.re) * StrictMath.sin(z.im));
    }

    /**
     * Returns the hyperbolic cosine of a complex number.
     * 
     * @param z
     *            a complex number.
     */
    public static Complex cosh(Complex z)
    {
        return new Complex(ExtraMath.cosh(z.re) * StrictMath.cos(z.im),
            ExtraMath.sinh(z.re) * StrictMath.sin(z.im));
    }

    /**
     * Returns the hyperbolic tangent of a complex number.
     * 
     * @param z
     *            a complex number.
     */
    public static Complex tanh(Complex z)
    {
        double sinhRe = ExtraMath.sinh(z.re);
        double coshRe = ExtraMath.cosh(z.re);
        double sinIm = StrictMath.sin(z.im);
        double cosIm = StrictMath.cos(z.im);
        double denom =
            coshRe * coshRe * cosIm * cosIm + sinhRe * sinhRe * sinIm * sinIm;
        return new Complex(sinhRe * coshRe / denom, sinIm * cosIm / denom);
    }

    /**
     * Returns the arc sine of a complex number, in the range of (-<img border=0
     * alt="pi" src="doc-files/pi.gif">/2 through <img border=0 alt="pi"
     * src="doc-files/pi.gif">/2, -<img border=0 alt="infinity"
     * src="doc-files/infinity.gif"> through <img border=0 alt="infinity"
     * src="doc-files/infinity.gif">).
     * 
     * @param z
     *            a complex number.
     */
    public static Complex asin(Complex z)
    {
        if (z.equals(ONE))
            return PI_2;
        else if (z.equals(MINUS_ONE))
            return MINUS_PI_2;
        else
        {
            // atan(z/sqrt(1-z*z))
            Complex root =
                sqrt(1.0 - z.re * z.re + z.im * z.im, -2.0 * z.re * z.im);
            double zModSqr = z.re * z.re + z.im * z.im;
            double rootModSqr = root.re * root.re + root.im * root.im;
            double denom =
                rootModSqr + zModSqr + 2.0 * (root.re * z.im - root.im * z.re);
            return log_2I((rootModSqr - zModSqr) / denom, 2.0
                    * (root.re * z.re + root.im * z.im) / denom);
        }
    }

    /**
     * Returns the arc cosine of a complex number, in the range of (0.0 through
     * <img border=0 alt="pi" src="doc-files/pi.gif">, 0.0 through <img border=0
     * alt="infinity" src="doc-files/infinity.gif">).
     * 
     * @param z
     *            a complex number.
     */
    public static Complex acos(Complex z)
    {
        if (z.equals(ONE))
            return ZERO;
        else if (z.equals(MINUS_ONE))
            return PI;
        else
        {
            // atan(-z/sqrt(1-z*z))+PI/2
            Complex root =
                sqrt(1.0 - z.re * z.re + z.im * z.im, -2.0 * z.re * z.im);
            double zModSqr = z.re * z.re + z.im * z.im;
            double rootModSqr = root.re * root.re + root.im * root.im;
            double denom =
                rootModSqr + zModSqr + 2.0 * (root.im * z.re - root.re * z.im);
            return log_2IplusPI_2((rootModSqr - zModSqr) / denom, -2.0
                    * (root.re * z.re + root.im * z.im) / denom);
        }
    }

    /**
     * Returns the arc tangent of a complex number, in the range of (-<img
     * border=0 alt="pi" src="doc-files/pi.gif">/2 through <img border=0
     * alt="pi" src="doc-files/pi.gif">/2, -<img border=0 alt="infinity"
     * src="doc-files/infinity.gif"> through <img border=0 alt="infinity"
     * src="doc-files/infinity.gif">).
     * 
     * @param z
     *            a complex number.
     */
    public static Complex atan(Complex z)
    {
        // -i atanh(iz) = -i/2 log((1+iz)/(1-iz))
        double modSqr = z.re * z.re + z.im * z.im;
        double denom = 1.0 + modSqr + 2.0 * z.im;
        return log_2I((1.0 - modSqr) / denom, 2.0 * z.re / denom);
    }

    /**
     * Returns the arc hyperbolic sine of a complex number, in the range of
     * (-<img border=0 alt="infinity" src="doc-files/infinity.gif"> through <img
     * border=0 alt="infinity" src="doc-files/infinity.gif">, -<img border=0
     * alt="pi" src="doc-files/pi.gif">/2 through <img border=0 alt="pi"
     * src="doc-files/pi.gif">/2).
     * 
     * @param z
     *            a complex number.
     */
    public static Complex asinh(Complex z)
    {
        if (z.equals(I))
            return PI_2_I;
        else if (z.equals(MINUS_I))
            return MINUS_PI_2_I;
        else
        {
            // log(z+sqrt(z*z+1))
            Complex root =
                sqrt(z.re * z.re - z.im * z.im + 1.0, 2.0 * z.re * z.im);
            return log(z.re + root.re, z.im + root.im);
        }
    }

    /**
     * Returns the arc hyperbolic cosine of a complex number, in the range of
     * (0.0 through <img border=0 alt="infinity" src="doc-files/infinity.gif">,
     * 0.0 through <img border=0 alt="pi" src="doc-files/pi.gif">).
     * 
     * @param z
     *            a complex number.
     */
    public static Complex acosh(Complex z)
    {
        if (z.equals(ONE))
            return ZERO;
        else if (z.equals(MINUS_ONE))
            return PI_I;
        else
        {
            // log(z+sqrt(z*z-1))
            Complex root =
                sqrt(z.re * z.re - z.im * z.im - 1.0, 2.0 * z.re * z.im);
            return log(z.re + root.re, z.im + root.im);
        }
    }

    /**
     * Returns the arc hyperbolic tangent of a complex number, in the range of
     * (-<img border=0 alt="infinity" src="doc-files/infinity.gif"> through <img
     * border=0 alt="infinity" src="doc-files/infinity.gif">, -<img border=0
     * alt="pi" src="doc-files/pi.gif">/2 through <img border=0 alt="pi"
     * src="doc-files/pi.gif">/2).
     * 
     * @param z
     *            a complex number.
     */
    public static Complex atanh(Complex z)
    {
        // 1/2 log((1+z)/(1-z))
        double modSqr = z.re * z.re + z.im * z.im;
        double denom = 1.0 + modSqr - 2.0 * z.re;
        return log_2((1.0 - modSqr) / denom, 2.0 * z.im / denom);
    }
}
