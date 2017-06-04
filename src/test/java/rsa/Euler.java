package rsa;

/**
 * Created by nbsaw on 2017/6/4.
 */
public class Euler {

    public static int phi(int n)
    {
        int ans,i,k;
        if(n == 1)
            ans = 0;
        else{
            ans = n;
            k=1;
            for(i = 2 ;n != 1;i += k){
                if(n % i == 0){
                    ans *= (i-1);
                    ans /= i;
                    while(n % i ==0 )
                        n /= i;
                    i = k;
                }
            }
        }
        return ans;
    }

    public static long[] extend_gcd(long a,long b){
        long ans;
        long[] result=new long[3];
        if(b==0)
        {
            result[0]=a;
            result[1]=1;
            result[2]=0;
            return result;
        }
        long [] temp=extend_gcd(b,a%b);
        ans = temp[0];
        result[0]=ans;
        result[1]=temp[2];
        result[2]=temp[1]-(a/b)*temp[2];
        return result;
    }



    public static void main(String[] args) {
        int n = 47*71;
        int p = phi(n);
        int e = 79;
        long d  = n + extend_gcd(e, p)[1];
        System.out.println(d);
//        List<Integer> list = new LinkedList<Integer>();
//        list.add(1);
//        int e = 72 ;
//        for (int i = 1 ;i < e ; i++) {
//            if (isPrime(i) && e % i !=0)
//                list.add(i);
//        }
//        System.out.println(list);
//        System.out.println(list.size());
    }
}
