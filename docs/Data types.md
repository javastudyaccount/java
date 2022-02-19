# [Java Data Types](https://www.w3schools.com/java/java_data_types.asp)

[The programmer has a child, the eldest is Lingling, the second is Lingyi, and the third is...](https://blog.actorsfit.com/a?ID=01450-66a3dc3b-4b8c-495a-93eb-81a0cb23a00e)
00
01
10
11
Overflow

## Primitive data types

<table>
<tr>
<th>Data Type</th><th>Size</th><th>Description</th><th>Power of 2</th><th>Java Code</th>
</tr>
<tr>
<td>byte</td><td>1 byte</td><td>Stores whole numbers from -128 to 127</td><td>2**8=256</td><td>
<pre>
public class Main {
    public static void main(String[] args) {
        System.out.println("2**8=" + (int)Math.pow(2, 8));
    }
}
</pre></td>
</tr>
<tr>
<td>short</td><td>2 byte</td><td>Stores whole numbers from -32,768 to 32,767</td><td>2**16=65536</td><td>
<pre>
public class Main {
    public static void main(String[] args) {
        System.out.println("2**16=" + (int)Math.pow(2, 16));
    }
}
</pre></td>
</tr>
<tr>
<td>int</td><td>4 byte</td><td>Stores whole numbers from -2,147,483,648 to 2,147,483,647</td><td>2**32=4294967296</td><td>
<pre>
public class Main {
    public static void main(String[] args) {
        System.out.println("2**32=" + <span style="color:red">(long)</span>Math.pow(2, 32));
    }
}
</pre>
</td>
</tr>
<tr>
<td>long</td><td>8 byte</td><td>Stores whole numbers from -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807</td><td>2**64=18446744073709552000</td><td>
<pre>
import java.math.BigInteger;
import java.math.BigDecimal;
public class Main {
    public static void main(String[] args) {
        System.out.println("2**64=" + BigDecimal.valueOf(Math.pow(2, 64)).toBigInteger());
    }
}
</pre>
<pre>
The process of converting a Double -> BigDecimal -> BigInteger is intensive. I propose the below: (about 500% faster)

BigInteger m = DoubleToBigInteger(doublevalue);

static BigInteger DoubleToBigInteger(double testVal) {
    long bits = Double.doubleToLongBits(testVal); 
    int exp = ((int)(bits >> 52) & 0x7ff) - 1075;
    BigInteger m = BigInteger.valueOf((bits & ((1L << 52)) - 1) | (1L << 52)).shiftLeft(exp);
    return  (bits >= 0)? m : m.negate();
}
</pre>
</td>
</tr>
<tr>
<td>float</td><td>4 byte</td><td>Stores fractional numbers. Sufficient for storing 6 to 7 decimal digits</td><td>--</td><td>
<pre>
float fAmount = 5.75f;
System.out.println(fAmount);
</pre>
</td>
</tr>
<tr>
<td>double</td><td>8 byte</td><td>Stores fractional numbers. Sufficient for storing 15 decimal digits</td><td>--</td><td>
<pre>
double dYen = 87654321.99<span style="color:red">d</span>;
System.out.println(dYen);
</pre>
<strong>Try without <span style="color:red">d</span>.</strong>
</td>
</tr>
<tr>
<td><a href="https://www.w3schools.com/java/java_booleans.asp">boolean</a></td><td>1 bit</td><td>Stores true or false values</td><td>--</td><td>--</td>
</tr>
<tr>
<td>char</td><td>2 byte</td><td>Stores a single character/letter or ASCII values</td><td>--</td><td>--</td>
</table>

## Non-primitive data types

[String](https://www.w3schools.com/java/java_strings.asp)

BigDecimal

Arrays

List

Colletions

Map

Classes

| difference                   | primitive                | non-primitive                                                |
| ---------------------------- | ------------------------ | ------------------------------------------------------------ |
| Defined by?                  | Java                     | Programmer``Except `<span style="color:red">`String |
| can be used to call methods? | no                       | yes                                                          |
| can be null?                 | no                       | yes                                                          |
| Naming rule                  | begin with lowercase     | begin with Uppercase                                         |
| Data size                    | depends on the data type | all the same size                                            |

## [Java Type Casting](https://www.w3schools.com/java/java_type_casting.asp)

- Widening Casting (automatically) - converting a smaller type to a larger type size
  byte -> short -> char -> int -> long -> float -> double
- Narrowing Casting (manually) - converting a larger type to a smaller size type
  double -> float -> long -> int -> char -> short -> byte

[Mutable vs Immutable Objects](https://www.interviewcake.com/concept/java/mutable)

Generic Types
