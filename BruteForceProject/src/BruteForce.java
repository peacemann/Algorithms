import java.io.File;
import java.text.DecimalFormat;
import java.util.Scanner;

public class BruteForce {

    public static void main(String[] args) throws Exception{

        int count=0;
        String article="";

        File file=new File("search_data.txt");
        Scanner scanner= new Scanner(file);

        while (scanner.hasNextLine()) {

            scanner.nextLine();
            count++;
        }

        Scanner scanner2= new Scanner(file);

        for (int r=0; r<count; r++){
            article += scanner2.nextLine();
        }



        File searchData=new File("search_test.txt");
        Scanner myScanner= new Scanner(searchData);

        long begin = System.nanoTime();

        while (myScanner.hasNextLine()){


            String needle=myScanner.nextLine();

            System.out.print("\n\n " + "'" + needle + "'" + " Pattern found at index : ");

            findString(article,needle);


        }

        final long duration = (System.nanoTime() - begin);
        final double seconds = ((double)duration / 1000000000);

        System.out.println("\n\n" + "Brute Force Match Solution Time : " + new DecimalFormat("#.#######").format(seconds) + " Seconds");


    }

    private static void findString(String haystack, String needle){


        for (int i=0;i<=haystack.length()-needle.length();i++){


            for (int j=0;j<needle.length();j++){


                if (haystack.charAt(i+j)== needle.charAt(j)){

                    if (needle.length()-1==j)

                        System.out.print( i +", ");
                }

                else
                    break;

            }
        }

    }
}
