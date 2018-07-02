package edu.stu.test;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

import java.util.Scanner;

public class Test2 {
    public static void main(String[] args) throws PinyinException {
        String str;
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            str = in.nextLine();
            String pinyin = PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_NUMBER);
            System.out.println(pinyin);

        }
    }
}
