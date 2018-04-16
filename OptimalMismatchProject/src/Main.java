import com.OptimalMismatch;
import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;


public class Main{





    public static void main(String args[]) throws Exception{

        OptimalMismatch.Result result = null;

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

            OptimalMismatch om = new OptimalMismatch(needle,article);

            result = om.findAll();

            showResult(result.indexOfFoundString, result.comp, needle);


        }

        final long duration = (System.nanoTime() - begin);
        final double seconds = ((double)duration / 1000000000);

        System.out.println("\n\n" + "Optimal Mismatch Solution Time : " + new DecimalFormat("#.#######").format(seconds) + " Seconds");




    }

    public static void showResult(List<Integer> result,int j, String needle){
        System.out.println("'" + needle + "'" + " Found Positions:".concat(result.toString()));

    }

}