package jtools;

import java.util.List;

/**
 *
 * @author FINDarkside
 */
public class XmlTools {

    public static List<String> getElement(List<String> doc, int i) { //Returns the xml element(+stuff inside it) (document,start index of the elements) 
        int indent = 0;
        for (int i2 = i; i2 < doc.size(); i2++) {
            String s = doc.get(i2);
            
            int starts = 0;
            int ends = 0;

            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(s.indexOf(">") - 1) == '/') {
                    starts = 0;
                    ends = 0;
                    break;
                }
                starts += s.charAt(j) == '<' ? 1 : 0;
                if (s.charAt(j) == '/' && s.charAt(j - 1) == '<') {
                    ends++;
                }
            }
            
            if(starts == 1 && ends == 1){
                indent--;
            }else if (starts == 1 && ends == 0) {
                indent++;
            }
            if(indent == 0){
                return doc.subList(i, i2+1);
            }
        }
        return doc.subList(i, doc.size());
    }
}
