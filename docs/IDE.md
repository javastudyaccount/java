IDE:

ni: vs code
shao: vs code
wenjing: Eclipse
xiangyang: Eclipse
tei: IntelliJ IDEA

1. auto generation
2. syntac checking
3. refactoring
4. debug
5. source control
6. sharing
7. source review

```
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // Your code here!
      
        System.out.println("Hello world.");
    }
}
```

# Java program

1. A runnable computer program coded java
2. A file with .java extension

   1. have method``public static void main(String[] args)``
   2. no``main`` method
3. 一
4. 二
5. 三
6. 万

   1. 一
   2. 一
   3. 一

```
//import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // Your code here!
      
        //String[] worlds = new ArrayList(){"hello", "world"}.asArray();
        String[] worlds = new String[5];
        worlds[0] = "hello";
        worlds[1] = "world1";
        worlds[2] = "world2";
        worlds[3] = "world3";
        worlds[4] = "world4";
      
        //System.out.println(worlds[0] + " " + worlds[1] + " " + worlds[2] + " " + worlds[3] + " " + worlds[4]);
        System.out.println(String.join(" ", worlds));
    }
  
}
```

Homework:
Initialize a string array with one line

## Trouble shooting

### final

```
 private final String[] target
```
When we use application.yml to initialize target, it says "No setter found for target"

