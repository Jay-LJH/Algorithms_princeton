
public class RandomWord {
    public static void main(String[] args) {
        string top=null;
        int count=0;
        while(!StdIn.isEmpty()){
            count++;
            string temp=StdIn.readString();
            if(StdRandom.bernoulli(1/count))
            {
                top=temp;
            }
        }
        System.out.println(temp);
    }
}
