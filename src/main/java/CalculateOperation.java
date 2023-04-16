import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CalculateOperation extends Thread{

    public static int j = 0;

    CalculateOperation(String name){
        super(name);
    }

    public void run(){
        Scanner in = new Scanner(System.in);
        String i = in.nextLine();
        System.out.println(StartCalculations(i));
    }

    private String StartCalculations(String arg){

        int result = 0;
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        int dataType = 0;
        String[] operands = arg.split("\\s*(-|\\+|\\*|/)\\s*");
        //operandFirst

        if(operands.length != 2){
            return "throws Exception : формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)";
        }

        if(pattern.matcher(operands[0]).matches() == false){
            dataType++;
            if (Stream.of(operands[0].toCharArray()).anyMatch(e -> e.toString().split("(^I*|^X*|^V*)*").length == 0)){
                dataType = dataType + 10;
            }
        }

        if(pattern.matcher(operands[1]).matches() == false){
            dataType++;
            if (Stream.of(operands[1].toCharArray()).anyMatch(e -> e.toString().split("(^I*|^X*|^V*)*").length == 0)){
                dataType = dataType + 10;
            }
        }

        if(dataType == 2){
            operands[0] = String.valueOf(romanToArabic(operands[0]));
            operands[1] = String.valueOf(romanToArabic(operands[1]));
            dataType = 100;
        }

        if(Integer.parseInt(operands[0]) > 10 || Integer.parseInt(operands[1]) > 10){
            return "throws Exception : Калькулятор не может принимать на вход числа больше 10";
        }

        if(dataType == 0 || dataType == 100){
            boolean ty = false;
            if (arg.split("(\\d|\\w|\\s)*")[2].equalsIgnoreCase("+")){
                result = Integer.parseInt(operands[0]) + Integer.parseInt(operands[1]);
                ty = true;
            }
            if (arg.split("(\\d|\\w|\\s)*")[2].equalsIgnoreCase("-")){
                result = Integer.parseInt(operands[0]) - Integer.parseInt(operands[1]);
                if(result <= 0 && dataType == 100){
                    return "throws Exception : В римской системе нет отрицательных чисел";
                }
                ty = true;
            }
            if (arg.split("(\\d|\\w|\\s)*")[2].equalsIgnoreCase("*")){
                result = Integer.parseInt(operands[0]) * Integer.parseInt(operands[1]);
                ty = true;
            }
            if (arg.split("(\\d|\\w|\\s)*")[2].equalsIgnoreCase("/")){
                result = Integer.parseInt(operands[0]) / Integer.parseInt(operands[1]);
                ty = true;
            }
            if(ty == false) {
                return "throws Exception : Строка не является математической операцией";
            }
        }

        if(dataType == 100){
            return arabicToRoman(result);
        }

        if(dataType >= 11){
            return "throws Exception : используются одновременно разные системы ";
        }

        return String.valueOf(result);
    }





    public static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " cannot be converted to a Roman Numeral");
        }

        return result;
    }


    public static String arabicToRoman(int number) {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " is not in range (0,4000]");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }

}
