package ru.croc.javaschool2024.Klychkova.task4;

public class Task4 {
    public static void main(String[] args) {
        String source = "..."; // test data
        String noComments = removeJavaComments(source);
        System.out.println(noComments);
    }


    public static String removeJavaComments(String input) {
        //  Solution with regular expressions (works only on test 1):
        //  Pattern pattern = Pattern.compile("(/\\*[\\s\\S]*?\\*/)|(//.*)");
        //  Matcher matcher = pattern.matcher(input);
        //  return matcher.replaceAll("");

        // Flag to indicate that string have started or not.
        int quotes = 0;
        // Flags to indicate that single line and multiple line comments have started or not.
        boolean s_cmt = false;
        boolean m_cmt = false;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            if (input.charAt(i) == '\"') {
                quotes = (quotes + 1) % 2;
            }
            // If single line comment flag is on, then check for end of it.
            if (s_cmt && input.charAt(i) == '\n') {
                s_cmt = false;
                result.append(input.charAt(i));
            }
            // If multiple line comment is on, then check for end of it.
            else if (m_cmt && input.charAt(i) == '*' && input.charAt(i+1) == '/') {
                m_cmt = false;
                i++;
            }
            // If this character is in a comment, ignore it.
            else if (s_cmt || m_cmt) {
                continue;
            }
            // Check for beginning of comments and set the appropriate flags.
            else if (input.charAt(i) == '/' && input.charAt(i+1) == '/' && quotes == 0) {
                s_cmt = true;
                i++;
            }
            else if (input.charAt(i) == '/' && input.charAt(i+1) == '*' && quotes == 0) {
                m_cmt = true;
                i++;
            }
            // If current character is a non-comment character, append it to res.
            else result.append(input.charAt(i));
        }
        return result.toString();
    }
}
