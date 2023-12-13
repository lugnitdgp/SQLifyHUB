package tdoc_java;

import javax.annotation.Nullable;
import lombok.Data;

/**
 *
 * @author ghostoo7
 */

@Data
public class Cells {
    
    @Nullable
    String a;
    
    @Nullable
    String b;
    
    @Nullable
    String c;
    
    @Nullable
    String d;
    
    @Nullable
    String e;
    
    @Nullable
    String f;
    
    @Nullable
    String g;
    
    @Nullable
    String h;
    
    @Nullable
    String i;
    
    @Nullable
    String j;
    
    @Nullable
    String k;
    
    @Nullable
    String l;

    
    public Cells(String a,String b,String c,String d,String e,String f,String g,String h,String i,String j,String k,String l){
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.e = e;
            this.f = f;
            this.g = g;
            this.h = h;
            this.i = i;
            this.j = j;
            this.k = k;
            this.l = l;
    }
    
    public String getA(){
        return a;
    }
    public String getB(){
        return b;
    }
    public String getC(){
        return c;
    }
    public String getD(){
        return d;
    }
    public String getE(){
        return e;
    }
    public String getF(){
        return f;
    }
    public String getG(){
        return g;
    }
    public String getH(){
        return h;
    }
    public String getI(){
        return i;
    }
    public String getJ(){
        return j;
    }
    public String getK(){
        return k;
    }
    public String getL(){
        return l;
    }
}