import java.util.SplittableRandom;

public class a72480 {
    static SplittableRandom rand = new SplittableRandom();
    public static void main(String[] args) {
        String line = args[0];

        switch(line) {
            case "random":
                System.out.println("Preferred form: " + generateIPv6() + "\n" + "Compressed form: " + compressIPv6(generateIPv6()));
                break;
            case "eui-64":
                String a = eui(args[1]);
                System.out.println("Preferred form: " + a + "\n" + "Compressed form: " + compressIPv6(a));
                break;
        }
    }

    private static String generateIPv6(){
        String result = "";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                result += Integer.toHexString(rand.nextInt(15));
            }
            if(i == 0) result += "::";
            else if(i < 4)  result += ":";
        }
        return result;
    }

    private static String compressIPv6(String address){
        return address.replaceFirst("^(?=(?:[^:]++:)++(?!:))(.*?)(^|:)0++(:0++):", "$1::").replaceAll("(^|:)0{1,3}(\\p{XDigit}++:)", "$1$2");
    }

    private static String eui(String address) {
        address = address.replace(":", "");
        String init = address.substring(0, 2);
        int num = Integer.parseInt(init, 16);
        init = Integer.toBinaryString(num);
        String substring = init.substring(0, init.length() - 2);
        if(init.charAt(init.length() - 2) == '0')
            init = substring + "1" + init.charAt(init.length() - 1);
        else
            init = substring + "0" + init.charAt(init.length() - 1);
        num = Integer.parseInt(init, 2);
        init = "fe80::" + Integer.toString(num, 16) + address.substring(2, 4) + ":" + address.substring(4, 6) + "ff:fe" + address.substring(6, 8) + ":" + address.substring(8, 12);
        return init;
    }
}
