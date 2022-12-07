package canard.intern.post.following.backend.enums;


import org.testng.annotations.Test;


public class GenderTest
{
    @Test
    void testGender(){
        Gender gender  = Gender.F;
        int o = gender.ordinal();
        String s =gender.toString();

        System.out.println("Ordinal "+  o);
        System.out.println("text "+  s);
        System.out.println("Name "+  gender.name());
    }
}
