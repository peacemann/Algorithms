import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

public class BruteForceSort {

    public static void main(String[] args) throws Exception {


        Scanner s = new Scanner(new File("sort_data.txt"));

        long begin = System.nanoTime();


        while(s.hasNextLine()){

            String lineWithBrackets = s.nextLine();
            String line=lineWithBrackets.substring(1,lineWithBrackets.length()-1);
            String[] parts = line.split(", ");
            int[] array = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();
            sort(array);

        }


        final long duration = (System.nanoTime() - begin);
        final double seconds = ((double)duration / 1000000000);

        System.out.println("\n" + "Geçen Süre : " + new DecimalFormat("#.######").format(seconds) + " Saniye");

    }


    public static void sort(int[] veri){


        int i,gecici,bayrak=1;
        while(bayrak==1){
            for(i=0;i<veri.length-1;i++){
                if(veri[i]>veri[i+1]){
                    gecici=veri[i];
                    veri[i]=veri[i+1];
                    veri[i+1]=gecici;
                }
            }
            bayrak=0;
            for(i=0;i<veri.length-1;i++){
                if(veri[i]>veri[i+1]){
                    bayrak=1;
                }
            }
        }

        System.out.print("Sıralanmış Dizi : ");
        for(i=0;i<veri.length;i++){
            System.out.print(veri[i]+" ");
        }

        System.out.println("\n");
    }
}
