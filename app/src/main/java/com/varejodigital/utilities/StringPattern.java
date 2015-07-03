package com.varejodigital.utilities;

/**
 * Created by Tox on 12/17/14.
 */
public class StringPattern {
    public static String format(String pattern, String value)
    {
        StringBuilder out = new StringBuilder();
        int j = 0;
        for (int i = 0; i < pattern.length() ;  i++)
        {
            if (j < value.length())
            {
                if (pattern.charAt(i) == '#') {
                    out.append(value.charAt(j));
                    j++;
                }
                else {
                    out.append(pattern.charAt(i));
                }
            }
        }
        return out.toString();
    }
}
