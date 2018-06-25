package edu.stu.test;

import java.util.Map;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        String[] words = new String[]{"北京", "海淀区", "中关村", "中国人民"};
        SplitChineseWordsTest splitChineseWords = new SplitChineseWordsTest(words);

        Scanner in = new Scanner(System.in);
        String str;
        while (in.hasNextLine()) {
            str = in.nextLine();
            Map<String, Integer> map = splitChineseWords.tokenize(str);
            System.out.println(map);
        }
    }
}
