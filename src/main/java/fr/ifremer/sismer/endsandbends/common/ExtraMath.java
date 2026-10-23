package fr.ifremer.sismer.endsandbends.common;

/**
 * The extra math library. Provides extra functions not in java.lang.Math class.
 * This class cannot be subclassed or instantiated because all methods are
 * static.
 * 
 * @version 1.1
 * @author Mark Hale
 */
public final class ExtraMath
{
    private ExtraMath()
    {
    }

    public final static double log10 = StrictMath.log(10.0);

    /**
     * Method log10, logarithme en base 10, absent de la JRE
     * 
     * @param x
     * @return double
     */
    public static double log10(double x)
    {
        return StrictMath.log(x) / log10;
    }

    /**
     * Returns sqrt(x<sup>2</sup>+y<sup>2</sup>).
     */
    public static double hypot(final double x, final double y)
    {
        final double xAbs = StrictMath.abs(x);
        final double yAbs = StrictMath.abs(y);
        if (xAbs == 0.0 && yAbs == 0.0)
            return 0.0;
        else if (xAbs < yAbs)
            return yAbs * StrictMath.sqrt(1.0 + (x / y) * (x / y));
        else
            return xAbs * StrictMath.sqrt(1.0 + (y / x) * (y / x));
    }

    /**
     * Returns a<sup>b</sup>.
     */
    public static int pow(int a, int b)
    {
        for (int i = 0; i < b; i++)
            a *= a;
        return a;
    }

    /**
     * Returns the factorial. (Wrapper for the gamma function).
     * 
     * @param x
     *            a double.
     */
    public static double factorial(double x)
    {
        return gamma(x + 1.0);
    }

    /**
     * Returns the natural logarithm of the factorial. (Wrapper for the log
     * gamma function).
     * 
     * @param x
     *            a double.
     */
    public static double logFactorial(double x)
    {
        return logGamma(x + 1.0);
    }

    /**
     * Returns the binomial coefficient (n k). Uses Pascal's recursion formula.
     * 
     * @param n
     *            an integer.
     * @param k
     *            an integer.
     */
    public static int binomial(int n, int k)
    {
        if (k == n || k == 0)
            return 1;
        else if (n == 0)
            return 1;
        else
            return binomial(n - 1, k - 1) + binomial(n - 1, k);
    }

    /**
     * Returns the binomial coefficient (n k). Uses gamma functions.
     * 
     * @param n
     *            a double.
     * @param k
     *            a double.
     */
    public static double binomial(double n, double k)
    {
        return StrictMath.exp(logGamma(n + 1.0) - logGamma(k + 1.0)
                - logGamma(n - k + 1.0));
    }

    /**
     * Returns the hyperbolic sine of a double.
     * 
     * @param x
     *            a double.
     */
    public static double sinh(double x)
    {
        return (StrictMath.exp(x) - StrictMath.exp(-x)) / 2.0;
    }

    /**
     * Returns the hyperbolic cosine of a double.
     * 
     * @param x
     *            a double.
     */
    public static double cosh(double x)
    {
        return (StrictMath.exp(x) + StrictMath.exp(-x)) / 2.0;
    }

    /**
     * Returns the hyperbolic tangent of a double.
     * 
     * @param x
     *            a double.
     */
    public static double tanh(double x)
    {
        return sinh(x) / cosh(x);
    }

    /**
     * Returns the arc hyperbolic sine of a double, in the range of -<img
     * border=0 alt="infinity" src="doc-files/infinity.gif"> through <img
     * border=0 alt="infinity" src="doc-files/infinity.gif">.
     * 
     * @param x
     *            a double.
     */
    public static double asinh(double x)
    {
        return StrictMath.log(x + StrictMath.sqrt(x * x + 1.0));
    }

    /**
     * Returns the arc hyperbolic cosine of a double, in the range of 0.0
     * through <img border=0 alt="infinity" src="doc-files/infinity.gif">.
     * 
     * @param x
     *            a double.
     */
    public static double acosh(double x)
    {
        return StrictMath.log(x + StrictMath.sqrt(x * x - 1.0));
    }

    /**
     * Returns the arc hyperbolic tangent of a double, in the range of -<img
     * border=0 alt="infinity" src="doc-files/infinity.gif"> through <img
     * border=0 alt="infinity" src="doc-files/infinity.gif">.
     * 
     * @param x
     *            a double.
     */
    public static double atanh(double x)
    {
        return StrictMath.log((1.0 + x) / (1.0 - x)) / 2.0;
    }

    /**
     * Relative machine precision.
     */
    private final static double EPS = 2.22e-16;
    /**
     * The smallest positive floating-point number such that 1/xminin is machine
     * representable.
     */
    private final static double XMININ = 2.23e-308;
    /**
     * Square root of 2.
     */
    private final static double SQRT2 =
        1.4142135623730950488016887242096980785696718753769;
    /**
     * Two times <img border=0 alt="pi" src="doc-files/pi.gif">.
     */
    private final static double TWO_PI =
        6.2831853071795864769252867665590057683943387987502;
    /**
     * Square root of 2<img border=0 alt="pi" src="doc-files/pi.gif">.
     */
    private final static double SQRT2PI =
        2.5066282746310005024157652848110452530069867406099;
    /**
     * Euler's gamma constant.
     */
    private final static double GAMMA =
        0.57721566490153286060651209008240243104215933593992;
    private final static double LOGSQRT2PI = StrictMath.log(SQRT2PI);

    /**
     * Gamma function. Based on public domain NETLIB (Fortran) code by W. J.
     * Cody and L. Stoltz<BR>
     * Applied Mathematics Division<BR>
     * Argonne National Laboratory<BR>
     * Argonne, IL 60439<BR>
     * <P>
     * References:
     * <OL>
     * <LI>"An Overview of Software Development for Special Functions", W. J.
     * Cody, Lecture Notes in Mathematics, 506, Numerical Analysis Dundee, 1975,
     * G. A. Watson (ed.), Springer Verlag, Berlin, 1976.
     * <LI>Computer Approximations, Hart, Et. Al., Wiley and sons, New York,
     * 1968.
     * </OL>
     * </P>
     * <P>
     * From the original documentation:
     * </P>
     * <P>
     * This routine calculates the GAMMA function for a real argument X.
     * Computation is based on an algorithm outlined in reference 1. The program
     * uses rational functions that approximate the GAMMA function to at least
     * 20 significant decimal digits. Coefficients for the approximation over
     * the interval (1,2) are unpublished. Those for the approximation for X
     * .GE. 12 are from reference 2. The accuracy achieved depends on the
     * arithmetic system, the compiler, the intrinsic functions, and proper
     * selection of the machine-dependent constants.
     * </P>
     * <P>
     * Error returns:<BR>
     * The program returns the value XINF for singularities or when overflow
     * would occur. The computation is believed to be free of underflow and
     * overflow.
     * </P>
     * 
     * @return Double.MAX_VALUE if overflow would occur, i.e. if abs(x) >
     *         171.624
     */
    public static double gamma(double x)
    {
// Gamma function related constants
        final double g_p[] =
            {-1.71618513886549492533811, 24.7656508055759199108314,
                    -379.804256470945635097577, 629.331155312818442661052,
                    866.966202790413211295064, -31451.2729688483675254357,
                    -36144.4134186911729807069, 66456.1438202405440627855};
        final double g_q[] =
            {-30.8402300119738975254353, 315.350626979604161529144,
                    -1015.15636749021914166146, -3107.77167157231109440444,
                    22538.1184209801510330112, 4755.84627752788110767815,
                    -134659.959864969306392456, -115132.259675553483497211};
        final double g_c[] =
            {-.001910444077728, 8.4171387781295e-4, -5.952379913043012e-4,
                    7.93650793500350248e-4, -.002777777777777681622553,
                    .08333333333333333331554247, .0057083835261};
        final double g_xbig = 171.624;

        double fact = 1.0, xden, xnum;
        int i, n = 0;
        double y = x, z, y1;
        boolean parity = false;
        double res, sum, ysq;

        if (y <= 0.0)
        {
// ----------------------------------------------------------------------
// Argument is negative
// ----------------------------------------------------------------------
            y = -(x);
            y1 = (int) y;
            res = y - y1;
            if (res != 0.0)
            {
                if (y1 != (((int) (y1 * 0.5)) * 2.0))
                    parity = true;
                fact = -StrictMath.PI / StrictMath.sin(StrictMath.PI * res);
                y++;
            }
            else
                return Double.MAX_VALUE;
        }
// ----------------------------------------------------------------------
// Argument is positive
// ----------------------------------------------------------------------
        if (y < EPS)
        {
// ----------------------------------------------------------------------
// Argument .LT. EPS
// ----------------------------------------------------------------------
            if (y >= XMININ)
                res = 1.0 / y;
            else
                return Double.MAX_VALUE;
        }
        else if (y < 12.0)
        {
            y1 = y;
            if (y < 1.0)
            {
// ----------------------------------------------------------------------
// 0.0 .LT. argument .LT. 1.0
// ----------------------------------------------------------------------
                z = y;
                y++;
            }
            else
            {
// ----------------------------------------------------------------------
// 1.0 .LT. argument .LT. 12.0, reduce argument if necessary
// ----------------------------------------------------------------------
                n = (int) y - 1;
                y -= n;
                z = y - 1.0;
            }
// ----------------------------------------------------------------------
// Evaluate approximation for 1.0 .LT. argument .LT. 2.0
// ----------------------------------------------------------------------
            xnum = 0.0;
            xden = 1.0;
            for (i = 0; i < 8; ++i)
            {
                xnum = (xnum + g_p[i]) * z;
                xden = xden * z + g_q[i];
            }
            res = xnum / xden + 1.0;
            if (y1 < y)
// ----------------------------------------------------------------------
// Adjust result for case 0.0 .LT. argument .LT. 1.0
// ----------------------------------------------------------------------
                res /= y1;
            else if (y1 > y)
            {
// ----------------------------------------------------------------------
// Adjust result for case 2.0 .LT. argument .LT. 12.0
// ----------------------------------------------------------------------
                for (i = 0; i < n; ++i)
                {
                    res *= y;
                    y++;
                }
            }
        }
        else
        {
// ----------------------------------------------------------------------
// Evaluate for argument .GE. 12.0
// ----------------------------------------------------------------------
            if (y <= g_xbig)
            {
                ysq = y * y;
                sum = g_c[6];
                for (i = 0; i < 6; ++i)
                    sum = sum / ysq + g_c[i];
                sum = sum / y - y + LOGSQRT2PI;
                sum += (y - 0.5) * StrictMath.log(y);
                res = StrictMath.exp(sum);
            }
            else
                return Double.MAX_VALUE;
        }
// ----------------------------------------------------------------------
// Final adjustments and return
// ----------------------------------------------------------------------
        if (parity)
            res = -res;
        if (fact != 1.0)
            res = fact / res;
        return res;
    }

    /**
     * The largest argument for which logGamma(x) is representable in the
     * machine.
     */
    private final static double logGamma_xBig = 2.55e305;

// Function cache for logGamma
    private static double logGammaCache_res = 0.0;
    private static double logGammaCache_x = 0.0;

    /**
     * The natural logarithm of the gamma function. Based on public domain
     * NETLIB (Fortran) code by W. J. Cody and L. Stoltz<BR>
     * Applied Mathematics Division<BR>
     * Argonne National Laboratory<BR>
     * Argonne, IL 60439<BR>
     * <P>
     * References:
     * <OL>
     * <LI>W. J. Cody and K. E. Hillstrom, 'Chebyshev Approximations for the
     * Natural Logarithm of the Gamma Function,' Math. Comp. 21, 1967, pp.
     * 198-203.
     * <LI>K. E. Hillstrom, ANL/AMD Program ANLC366S, DGAMMA/DLGAMA, May, 1969.
     * <LI>Hart, Et. Al., Computer Approximations, Wiley and sons, New York,
     * 1968.
     * </OL>
     * </P>
     * <P>
     * From the original documentation:
     * </P>
     * <P>
     * This routine calculates the LOG(GAMMA) function for a positive real
     * argument X. Computation is based on an algorithm outlined in references 1
     * and 2. The program uses rational functions that theoretically approximate
     * LOG(GAMMA) to at least 18 significant decimal digits. The approximation
     * for X > 12 is from reference 3, while approximations for X < 12.0 are
     * similar to those in reference 1, but are unpublished. The accuracy
     * achieved depends on the arithmetic system, the compiler, the intrinsic
     * functions, and proper selection of the machine-dependent constants.
     * </P>
     * <P>
     * Error returns:<BR>
     * The program returns the value XINF for X .LE. 0.0 or when overflow would
     * occur. The computation is believed to be free of underflow and overflow.
     * </P>
     * 
     * @return Double.MAX_VALUE for x < 0.0 or when overflow would occur, i.e. x
     *         > 2.55E305
     */
    public static double logGamma(double x)
    {
// Log Gamma related constants
        final double lg_d1 = -.5772156649015328605195174, lg_d2 =
            .4227843350984671393993777, lg_d4 = 1.791759469228055000094023;
        final double lg_p1[] =
            {4.945235359296727046734888, 201.8112620856775083915565,
                    2290.838373831346393026739, 11319.67205903380828685045,
                    28557.24635671635335736389, 38484.96228443793359990269,
                    26377.48787624195437963534, 7225.813979700288197698961};
        final double lg_q1[] =
            {67.48212550303777196073036, 1113.332393857199323513008,
                    7738.757056935398733233834, 27639.87074403340708898585,
                    54993.10206226157329794414, 61611.22180066002127833352,
                    36351.27591501940507276287, 8785.536302431013170870835};
        final double lg_p2[] =
            {4.974607845568932035012064, 542.4138599891070494101986,
                    15506.93864978364947665077, 184793.2904445632425417223,
                    1088204.76946882876749847, 3338152.967987029735917223,
                    5106661.678927352456275255, 3074109.054850539556250927};
        final double lg_q2[] =
            {183.0328399370592604055942, 7765.049321445005871323047,
                    133190.3827966074194402448, 1136705.821321969608938755,
                    5267964.117437946917577538, 13467014.54311101692290052,
                    17827365.30353274213975932, 9533095.591844353613395747};
        final double lg_p4[] =
            {14745.02166059939948905062, 2426813.369486704502836312,
                    121475557.4045093227939592, 2663432449.630976949898078,
                    29403789566.34553899906876, 170266573776.5398868392998,
                    492612579337.743088758812, 560625185622.3951465078242};
        final double lg_q4[] =
            {2690.530175870899333379843, 639388.5654300092398984238,
                    41355999.30241388052042842, 1120872109.61614794137657,
                    14886137286.78813811542398, 101680358627.2438228077304,
                    341747634550.7377132798597, 446315818741.9713286462081};
        final double lg_c[] =
            {-0.001910444077728, 8.4171387781295e-4, -5.952379913043012e-4,
                    7.93650793500350248e-4, -0.002777777777777681622553,
                    0.08333333333333333331554247, 0.0057083835261};
// Rough estimate of the fourth root of logGamma_xBig
        final double lg_frtbig = 2.25e76;

        final double pnt68 = 0.6796875;

        double xden, corr, xnum;
        int i;
        double y, xm1, xm2, xm4, res, ysq;

        if (x == logGammaCache_x)
            return logGammaCache_res;
        y = x;
        if (y > 0.0 && y <= logGamma_xBig)
        {
            if (y <= EPS)
            {
                res = -StrictMath.log(y);
            }
            else if (y <= 1.5)
            {
// ----------------------------------------------------------------------
// EPS .LT. X .LE. 1.5
// ----------------------------------------------------------------------
                if (y < pnt68)
                {
                    corr = -StrictMath.log(y);
                    xm1 = y;
                }
                else
                {
                    corr = 0.0;
                    xm1 = y - 1.0;
                }
                if (y <= 0.5 || y >= pnt68)
                {
                    xden = 1.0;
                    xnum = 0.0;
                    for (i = 0; i < 8; i++)
                    {
                        xnum = xnum * xm1 + lg_p1[i];
                        xden = xden * xm1 + lg_q1[i];
                    }
                    res = corr + xm1 * (lg_d1 + xm1 * (xnum / xden));
                }
                else
                {
                    xm2 = y - 1.0;
                    xden = 1.0;
                    xnum = 0.0;
                    for (i = 0; i < 8; i++)
                    {
                        xnum = xnum * xm2 + lg_p2[i];
                        xden = xden * xm2 + lg_q2[i];
                    }
                    res = corr + xm2 * (lg_d2 + xm2 * (xnum / xden));
                }
            }
            else if (y <= 4.0)
            {
// ----------------------------------------------------------------------
// 1.5 .LT. X .LE. 4.0
// ----------------------------------------------------------------------
                xm2 = y - 2.0;
                xden = 1.0;
                xnum = 0.0;
                for (i = 0; i < 8; i++)
                {
                    xnum = xnum * xm2 + lg_p2[i];
                    xden = xden * xm2 + lg_q2[i];
                }
                res = xm2 * (lg_d2 + xm2 * (xnum / xden));
            }
            else if (y <= 12.0)
            {
// ----------------------------------------------------------------------
// 4.0 .LT. X .LE. 12.0
// ----------------------------------------------------------------------
                xm4 = y - 4.0;
                xden = -1.0;
                xnum = 0.0;
                for (i = 0; i < 8; i++)
                {
                    xnum = xnum * xm4 + lg_p4[i];
                    xden = xden * xm4 + lg_q4[i];
                }
                res = lg_d4 + xm4 * (xnum / xden);
            }
            else
            {
// ----------------------------------------------------------------------
// Evaluate for argument .GE. 12.0
// ----------------------------------------------------------------------
                res = 0.0;
                if (y <= lg_frtbig)
                {
                    res = lg_c[6];
                    ysq = y * y;
                    for (i = 0; i < 6; i++)
                        res = res / ysq + lg_c[i];
                }
                res /= y;
                corr = StrictMath.log(y);
                res = res + LOGSQRT2PI - 0.5 * corr;
                res += y * (corr - 1.0);
            }
        }
        else
        {
// ----------------------------------------------------------------------
// Return for bad arguments
// ----------------------------------------------------------------------
            res = Double.MAX_VALUE;
        }
// ----------------------------------------------------------------------
// Final adjustments and return
// ----------------------------------------------------------------------
        logGammaCache_x = x;
        logGammaCache_res = res;
        return res;
    }
}
