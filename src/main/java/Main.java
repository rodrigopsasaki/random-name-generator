import name.generator.NameGenerator;
import name.generator.NameType;

public class Main {

    public static void main(String[] args) throws Exception {

        NameGenerator generator = NameGenerator.getInstance(NameType.ROMANS);
        generator.changeType(NameType.FANTASY);

        for(int i = 0 ; i < 10 ; i++){
            System.out.println(generator.generate());
        }

    }

}
