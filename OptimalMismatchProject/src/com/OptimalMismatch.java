package com;
import java.util.ArrayList;
import java.util.List;


public class OptimalMismatch {
    public char[] x,y;
    public int m,n;
    public int[] adaptedGs,qsBc,freq;
    public Pattern[] pat;

    public OptimalMismatch(String pattern, String source) {
        x = pattern.toCharArray();
        y = source.toCharArray();
        m = x.length;
        n = y.length;
        adaptedGs = new int[m + 1];
        qsBc = new int[65536];
        freq = calculateCharFreq(x, y, 65536);
        pat = new Pattern[m];
    }

    /* Construct an ordered pattern from a string. */
    private static void orderPattern(char[] x, Pattern[] pat, int[] freq) {

        for (int i = 0; i < x.length; ++i) {
            Pattern ptrn = new Pattern();
            ptrn.loc = i;
            ptrn.c = x[i];
            pat[i] = ptrn;
        }

        qsortPtrn(pat, 0, x.length - 1, freq);
    }

    private static void qsortPtrn(Pattern[] pat, int low, int n, int[] freq) {
        int lo = low;
        int hi = n;
        if (lo >= n) {
            return;
        }
        Pattern mid = pat[(lo + hi) / 2];
        while (lo < hi) {
            while (lo < hi && optimalPcmp(pat[lo], mid, freq) < 0) {
                lo++;
            }
            while (lo < hi && optimalPcmp(pat[hi], mid, freq) > 0) {
                hi--;
            }
            if (lo < hi) {
                Pattern T = pat[lo];
                pat[lo] = pat[hi];
                pat[hi] = T;
            }
        }
        if (hi < lo) {
            int T = hi;
            hi = lo;
            lo = T;
        }
        qsortPtrn(pat, low, lo, freq);
        qsortPtrn(pat, lo == low ? lo + 1 : lo, n, freq);
    }

    /* Optimal Mismatch pattern comparison function. */
    private static int optimalPcmp(Pattern pat1, Pattern pat2, int[] freq) {
        int fx;

        fx = freq[pat1.c] - freq[pat2.c];
        return (fx != 0 ? (fx > 0 ? 1 : -1) : (pat2.loc - pat1.loc));
    }

    /*
     * Find the next leftward matching shift for the first ploc pattern elements
     * after a current shift or lshift.
     */
    private static int matchShift(char[] x, int ploc, int lshift,
                                  Pattern[] pat) {
        int i, j;

        for (; lshift < x.length; ++lshift) {
            i = ploc;
            while (--i >= 0) {
                if ((j = (pat[i].loc - lshift)) < 0)
                    continue;
                if (pat[i].c != x[j])
                    break;
            }
            if (i < 0)
                break;
        }
        return (lshift);
    }

    /*
     * Constructs the good-suffix shift table from an ordered string.
     */
    private static void preAdaptedGs(char[] x, int adaptedGs[],
                                     Pattern[] pat) {
        int lshift, i, ploc;

        adaptedGs[0] = lshift = 1;
        for (ploc = 1; ploc <= x.length; ++ploc) {
            lshift = matchShift(x, ploc, lshift, pat);
            adaptedGs[ploc] = lshift;
        }
        for (ploc = 0; ploc < x.length; ++ploc) {
            lshift = adaptedGs[ploc];
            while (lshift < x.length) {
                i = pat[ploc].loc - lshift;
                if (i < 0 || pat[ploc].c != x[i])
                    break;
                ++lshift;
                lshift = matchShift(x, ploc, lshift, pat);
            }
            adaptedGs[ploc] = lshift;
        }
    }

    private static int[] calculateCharFreq(char[] x, char[] y, int z) {
        int i;
        int[] freq = new int[z];
        for (i = 0; i < x.length; i++)
            freq[x[i]]++;
        for (i = 0; i < y.length; i++)
            freq[y[i]]++;
        return freq;
    }

    private static void preQsBc(char[] x, int qsBc[]) {
        int i, m = x.length;

        for (i = 0; i < qsBc.length; ++i)
            qsBc[i] = m + 1;
        for (i = 0; i < m; ++i)
            qsBc[x[i]] = m - i;
    }
    public void setupOM(){
        /* Preprocessing */
        orderPattern(this.x, this.pat, this.freq);
        preQsBc(this.x, this.qsBc);
        preAdaptedGs(this.x, this.adaptedGs, this.pat);
    }

    /* Optimal Mismatch string matching algorithm. */
    public Result findAll() {
        int i,j;
        List<Integer> result = new ArrayList<Integer>();
        setupOM();

		/* Searching */
        j = 0;
        int j_old=0;
        while (j <= n - m) {
            i = 0;
            while (i < m && pat[i].c == y[j + pat[i].loc])
                ++i;
            if (i >= m)
                result.add(j);

            j_old = j;
            if (j < n - m)
                j += Math.max(adaptedGs[i], qsBc[y[j + m]]);
            else
                j += adaptedGs[i];
        }

        /*return comparison number and result */

        return new Result(j_old,result);
    }

    public  static class Result{
        public int comp;
        public List<Integer> indexOfFoundString;

        public Result(int comp, List<Integer> indexOfFoundString) {
            this.comp = comp;
            this.indexOfFoundString = indexOfFoundString;
        }


    }

    public static class Pattern {
        public int loc;
        public char c;
    }
}