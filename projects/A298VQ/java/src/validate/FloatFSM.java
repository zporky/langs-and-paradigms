package validate;

public class FloatFSM {

    private final String symbol_p = "\\.";
    private final String symbol_s = "[+|-]";
    private final String symbol_d = "[1-9]";
    private final String symbol_z = "0";
    private final String symbol_e = "e";

    private String inputStr;
    private StringBuilder floatValue;
    private int position;

    public String result;

    public boolean validate(String str) {
        inputStr = str;
        position = 0;
        floatValue = new StringBuilder();
        return sate_start();
    }

    public boolean terminate(boolean res) {
        result = (res) ? "OK " + floatValue : "FAIL";
        return res;
    }

    private boolean sate_start() {
    	
    	if (position == inputStr.length()) {
            return terminate(false);
        }

        String s = String.valueOf(inputStr.charAt(position));

        if (s.matches(symbol_s)) {
        	if ( s.equals("-"))
        		floatValue.append(s);
            position++;
        }

        return state_start_();
    }

    private boolean state_start_() {
        
        if (position == inputStr.length()) {
            return terminate(false);
        }

        String s = String.valueOf(inputStr.charAt(position));
               
        if (s.matches(symbol_p)) {
            floatValue.append("0.");
            return state_i_0();
        } else if (s.matches(symbol_z)) {
            floatValue.append(s);
            return state_t_2();
        } else if (s.matches(symbol_d)) {
            floatValue.append(s);
            return state_t_4();
        }
        return terminate(false);
    }

    private boolean state_i_0() {
        position++;
        
        if (position == inputStr.length()) {
            return terminate(false);
        }
        
        String s = String.valueOf(inputStr.charAt(position));
        
        if (s.matches(symbol_d) || s.matches(symbol_z)) {
            floatValue.append(s);
            return state_t_1();
        }

        return terminate(false);
    }

    private boolean state_i_1() {
        position++;
        
        if (position == inputStr.length()) {
            return terminate(false);
        }
        
        String s = String.valueOf(inputStr.charAt(position));
        
        if (s.matches(symbol_s)) {
            floatValue.append(s);
            return state_i_2();
        }

        return terminate(false);
    }

    private boolean state_i_2() {
        position++;
        
        if (position == inputStr.length()) {
            return terminate(false);
        }
        
        String s = String.valueOf(inputStr.charAt(position));
        
        if (s.matches(symbol_d)) {
            floatValue.append(s);
            return state_t_6();
        }

        return terminate(false);
    }

    private boolean state_t_1() {
        position++;
        
        if (position == inputStr.length()) {
            return terminate(true);
        }

        String s = String.valueOf(inputStr.charAt(position));
        
        if (s.matches(symbol_d) || s.matches(symbol_z)) {
            floatValue.append(s);
            return state_t_1();
        }
        else if(s.matches(symbol_e)) {
            floatValue.append(s);
            return state_i_1();
        }

        return terminate(false);
    }

    private boolean state_t_2() {
        position++;
        
        if (position == inputStr.length()) {
            return terminate(true);
        }

        String s = String.valueOf(inputStr.charAt(position));
        
        if (s.matches(symbol_p) || s.matches(symbol_z)) {
            floatValue.append(s);
            return state_t_3();
        }
        else if(s.matches(symbol_e)) {
            floatValue.append(s);
            return state_i_1();
        }

        return terminate(false);
    }

    private boolean state_t_3() {
        position++;
        
        if (position == inputStr.length()) {
            return terminate(true);
        }

        String s = String.valueOf(inputStr.charAt(position));

        if (s.matches(symbol_d) || s.matches(symbol_z)) {
            floatValue.append(s);
            return state_t_3();
        }
        else if(s.matches(symbol_e)) {
            floatValue.append(s);
            return state_i_1();
        }

        return terminate(false);
    }

    private boolean state_t_4() {
        position++;
        
        if (position == inputStr.length()) {
            floatValue.append(".0");
            return terminate(true);
        }

        String s = String.valueOf(inputStr.charAt(position));
    
        if (s.matches(symbol_d) || s.matches(symbol_z)) {
            floatValue.append(s);
            return state_t_4();
        }
        else if (s.matches(symbol_p)) {
            floatValue.append(s);
            return state_t_5();
        }
        else if(s.matches(symbol_e)) {
            floatValue.append(s);
            return state_i_1();
        }

        return terminate(false);
    }

    private boolean state_t_5() {
        position++;
        
        if (position == inputStr.length()) {
        	if(floatValue.charAt(floatValue.length()-1) == '.')
        		floatValue.append("0");
            return terminate(true);
        }

        String s = String.valueOf(inputStr.charAt(position));
        
        if (s.matches(symbol_d) || s.matches(symbol_z)) {
            floatValue.append(s);
            return state_t_5();
        }
        else if(s.matches(symbol_e)) {
            floatValue.append(s);
            return state_i_1();
        }

        return terminate(false);
    }
    
    private boolean state_t_6() {
        position++;
        
        if (position == inputStr.length()) {
            return terminate(true);
        }

        String s = String.valueOf(inputStr.charAt(position));
        
        if (s.matches(symbol_d) || s.matches(symbol_z)) {
            floatValue.append(s);
            return state_t_6();
        }

        return terminate(false);
    }
}
