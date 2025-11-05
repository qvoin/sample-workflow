import java.util.Arrays;

/**
 * Name: Aaron Ling
 * Your own abc123:qbc627
 * IMPORTANT: Rename both the filename AND class name to match your UTSA ID (abc123).
 *
 * Keep comments as is, but when implementing the functions,
 * add your own comments with your initials like // [AL]: .....
 * Also check for possible errors and handle them appropriately.
 */


// testing for github 
public class Main {

    //Git Testing
    // Is it working??
    /*
    *
    *
    *              =(^._.^)=
    *
    *
    * */

    // PART 1: Core Counting Functions

    // 1. Factorial
    public static long factorial(int n) {
        // [AL]: Handle negative input
        if (n < 0) {
            System.err.println("Error: Factorial is not defined for negative numbers.");
            return -1; // Error code
        }
        // [AL]: Implement the rest of the factorial logic
        // HINT: Remember that 0! =1
        long res = 1L;
        for (int i = 2; i <= n; i++) {
            res *= i; // [AL]
        }
        return res;
    }

    // 2. Permutations P(n, r)
    public static long permutations(int n, int r) {
        // [AL]: Implement P(n, r) = n! / (n-r)!
        if (n < 0 || r < 0 || r > n) {
            System.err.println("Error: require 0 <= r <= n and n >= 0.");
            return -1;
        }
        long res = 1L;
        for (int i = 0; i < r; i++) res *= (n - i); // [AL]
        return res;
    }

    // 3. Combinations C(n, r)
    public static long combinations(int n, int r) {
        // [AL]: Implement C(n, r) = n! / (r! * (n-r)!)
        // HINT: For efficiency and to avoid overflow with large intermediate numbers,
        // calculate C(n, r) as (n * (n-1) * ... * (n-r+1)) / r!
        //
        // NOTE: Avoid recursive Pascal’s identity for performance reasons.
        if (n < 0 || r < 0 || r > n) {
            System.err.println("Error: require 0 <= r <= n and n >= 0.");
            return -1;
        }
        r = Math.min(r, n - r);
        long num = 1L, den = 1L;
        for (int i = 1; i <= r; i++) {
            num *= (n - r + i);
            den *= i;
            long g = gcd(num, den);
            if (g > 1) { num /= g; den /= g; }
        }
        return num / den;
    }
    

    // 4. Permutations with Indistinguishable Items
    public static long permutationsWithIndistinguishable(int[] counts) {
        if (counts == null || counts.length == 0) return 0;
        // [AL]: Implement n! / (n1! * n2! * ... * nk!)
        // where n is the sum of counts.
        long total = 0;
        for (int c : counts) {
            if (c < 0) {
                System.err.println("Error: counts must be non-negative.");
                return -1;
            }
            total += c;
        }
        long res = factorial((int) total);
        for (int c : counts) res /= factorial(c);
        return res;
    }

    // PART 2: Generation Functions

    // 5. Next Lexicographic Permutation
    public static boolean nextPermutation(int[] current) {
        // [AL]: Implement the algorithm from the Self-Study Guide (m2-Lecture 6).
        // Remember to modify the array in-place.
        // [AL]: Standard next-permutation
        if (current == null || current.length < 2) return false;
        int i = current.length - 2;
        while (i >= 0 && current[i] >= current[i+1]) i--;
        if (i < 0) return false;
        int j = current.length - 1;
        while (current[j] <= current[i]) j--;
        swap(current, i, j);
        reverse(current, i+1, current.length - 1);
        return true;
    }

    // 6. Next Subset as a Bit String
    public static boolean nextSubsetBitString(boolean[] current) {
        // [AL]: Implement the binary counting algorithm.
        // Remember to modify the array in-place. true=1, false=0.
        // [AL]: Binary +1 over bitstring (true=1)
        if (current == null || current.length == 0) return false;
        boolean allTrue = true;
        for (boolean b : current) if (!b) { allTrue = false; break; }
        if (allTrue) return false;
        for (int i = current.length - 1; i >= 0; i--) {
            if (!current[i]) {
                current[i] = true;
                for (int j=i+1;j<current.length;j++) current[j] = false;
                return true;
            }
        }
        return false;
    }

    // 7. Next r-Combination
    public static boolean nextRCombination(int[] current, int n) {
        // [AL]: Implement the r-combination generation algorithm.
        // Remember to modify the array in-place.
        // [AL]: Next r-combination in-place
        if (current == null || current.length == 0) return false;
        int r = current.length;
        for (int i = r - 1; i >= 0; i--) {
            if (current[i] < n - (r - 1 - i)) {
                current[i]++;
                for (int j = i + 1; j < r; j++) current[j] = current[j-1] + 1;
                return true;
            }
        }
        return false;
    }

    // =====================END of CODING ======================================
    // YOU DON'T NEED TO CHANGE ANYTHING IN THE REST OF THE CODE! Just study them to understand what they do!
    //
    // BUT YOU NEED TO ANSWER REFLECTION QUESTIONS IN COMMENTS AT THE END OF THIS FILE
    // So, AFTER TESTING YOUR WORKING PROGRAM, GO THERE AND RESPOND TO REFLECTION QUESTIONS


    // Generating and printing all r-combinations of {1, 2, ..., n}
    // using nextRCombination() iteratively.
    public static void generateAllRCombinations(int n, int r) {
        // [AL]: Validate input
        if (r > n || n <= 0 || r <= 0) {
            System.err.println("Error: Invalid values for n or r.");
            return;
        }

        // [AL]: Step 1 – Initialize the first combination [1, 2, ..., r]
        int[] comb = new int[r];
        for (int i = 0; i < r; i++) {
            comb[i] = i + 1;
        }

        // [AL]: Step 2 – Print the first combination
        System.out.println(Arrays.toString(comb));

        // [AL]: Step 3 – Repeatedly generate the next combination until none left
        while (nextRCombination(comb, n)) {
            System.out.println(Arrays.toString(comb));
        }

        // [AL]: Step 4 – Done
        System.out.println("All " + combinations(n, r) + " combinations generated.");
    }

    // Main method with test cases
    public static void main(String[] args) {
        System.out.println("--- Part 1: Testing Core Counting Functions ---");
        testCountingFunctions();
        System.out.println("\n--- Part 2: Testing Generation Functions ---");
        testGenerationFunctions();

        System.out.println("\n=== Reminder ===");
        System.out.println("After verifying your outputs, scroll to the end of your java file \r\n" +
                "   and answer the reflection questions in the comment block.\r\n");

    }

    public static void testCountingFunctions() {
        //edge cases
        System.out.println("Factorial(0) = " + factorial(0) + " (Expected: 1)");
        System.out.println("Permutations(5, 0) = " + permutations(5, 0) + " (Expected: 1)");
        System.out.println("Permutations(5, 5) = " + permutations(5, 5) + " (Expected: 120)");
        System.out.println("Combinations(5, 0) = " + combinations(5, 0) + " (Expected: 1)");
        System.out.println("Combinations(5, 5) = " + combinations(5, 5) + " (Expected: 1)");

        System.out.println("Factorial(10) = " + factorial(10) + " (Expected: 3628800)");
        System.out.println("Permutations(10, 3) = " + permutations(10, 3) + " (Expected: 720)");
        System.out.println("Combinations(10, 3) = " + combinations(10, 3) + " (Expected: 120)");

        // Test for MISSISSIPPI: M=1, I=4, S=4, P=2
        int[] mississippiCounts = {1, 4, 4, 2};
        System.out.println("Permutations of MISSISSIPPI = " + permutationsWithIndistinguishable(mississippiCounts) + " (Expected: 34650)");
    }

    public static void testGenerationFunctions() {
        // Test nextPermutation
        System.out.println("\nTesting nextPermutation:");
        int[] perm = {1, 3, 4, 2};
        System.out.println("Start: " + Arrays.toString(perm));
        if (nextPermutation(perm)) {
            System.out.println("Next:  " + Arrays.toString(perm) + " (Expected: [1, 4, 2, 3])");
        }
        int[] lastPerm = {4, 3, 2, 1};
        System.out.println("Start: " + Arrays.toString(lastPerm));
        if (!nextPermutation(lastPerm)) {
            System.out.println("Next:  null (Correctly identified last permutation)");
        }

        // Test nextSubsetBitString
        System.out.println("\nTesting nextSubsetBitString:");
        boolean[] subset = {true, false, false, true, true}; // 10011
        System.out.println("Start: " + Arrays.toString(subset).replace("true", "1").replace("false", "0"));
        if (nextSubsetBitString(subset)) {
            System.out.println("Next:  " + Arrays.toString(subset).replace("true", "1").replace("false", "0") + " (Expected: [1, 0, 1, 0, 0])");
        }

        // Test nextRCombination
        System.out.println("\nTesting nextRCombination:");
        int[] rComb = {1, 3, 4, 6}; // A 4-combination from {1...6}
        int n = 6;
        System.out.println("Start: " + Arrays.toString(rComb) + " (n=6, r=4)");
        if (nextRCombination(rComb, n)) {
            System.out.println("Next:  " + Arrays.toString(rComb) + " (Expected: [1, 3, 5, 6])");
        }
        int[] lastRComb = {3, 4, 5, 6};
        System.out.println("Start: " + Arrays.toString(lastRComb));
        if (!nextRCombination(lastRComb, n)) {
            System.out.println("Next:  null (Correctly identified last r-combination)");
        }

        System.out.println("\nTesting generateAllRCombinations: 3-combinations from 1, 2, 3, 4, 5");
        generateAllRCombinations(5, 3);
        System.out.println("Expected:\r\n" +
                "          [1, 2, 3]\r\n" + //
                "          [1, 2, 4]\r\n" + //
                "          [1, 2, 5]\r\n" + //
                "          [1, 3, 4]\r\n" + //
                "          [1, 3, 5]\r\n" + //
                "          [1, 4, 5]\r\n" + //
                "          [2, 3, 4]\r\n" + //
                "          [2, 3, 5]\r\n" + //
                "          [2, 4, 5]\r\n" + //
                "          [3, 4, 5]\r\n" + //
                "          All 10 combinations generated.\r\n");
    }

    // [AL]: Helper methods
    private static long gcd(long a, long b) {
        a = Math.abs(a); b = Math.abs(b);
        while (b != 0) { long t = a % b; a = b; b = t; }
        return a;
    }
    private static void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }
    private static void reverse(int[] a, int l, int r) { while (l < r) swap(a, l++, r--); }
}

/*
===================== Reflection (qbc627 / Aaron Ling) =====================

A. What happens at factorial(21) and why?
- long overflows: long max is 9,223,372,036,854,775,807. 21! ≈ 5.1091e19, which
  exceeds long’s range, causing wrap-around and incorrect values due to fixed width.

Fundamental limitation:
- Primitive integral types have finite, fixed bit-width; arithmetic is modulo 2^64 for long.

B. What Java class to handle larger integers and why?
- java.math.BigInteger. It supports arbitrary-precision integers and exact arithmetic.
  Using BigInteger for factorial/combinations avoids overflow by expanding storage as needed.

C. Why not print all permutations of {1..20}?
- There are 20! ≈ 2.43×10^18 permutations.
  At 1,000,000 permutations/sec, time ≈ 2.43×10^12 seconds.
  Convert to years: 2.43×10^12 / (60*60*24*365) ≈ 77,000 years.
  So it’s utterly infeasible in practice.

==========================================================================
*/