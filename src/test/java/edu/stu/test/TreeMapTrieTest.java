package edu.stu.test;

import com.github.stuxuhai.jpinyin.TestPinyinHelper;
import edu.stu.vpinyin.TreeMapTrie;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class TreeMapTrieTest {

    String[] words = new String[]{"北京", "海淀区", "中关村", "中国人民", "程序员", "梦想"};
    //String[] words = new String[]{"是", "不是"};

    @Test
    public void testSplit() {
        TreeMapTrie treeMapTrie = TreeMapTrie.getInstance(words);
        String str = readFromFile("/tmp/mytmp/test.txt");
        String[] word = treeMapTrie.split(str, false);
        TestPinyinHelper.print(word);

    }

    @Test
    public void testTokenize() {
        //String str1 = "我们中国人民不全都是北京人，北京的中关村属于海淀区。";
        //String str2 = "没有梦想，何必北京。不想去中关村的中国程序员，不是好程序员。陈小姐在海淀区的中关村，陈小姐是一名地道的中国式程序媛。";

        TreeMapTrie treeMapTrie = TreeMapTrie.getInstance(words);
        String str = readFromFile("/tmp/mytmp/test.txt");
        Map<String, Integer> map = treeMapTrie.tokenize(str);
        System.out.println(map);
        System.out.println();
        string2Array(str, map);

    }

    private void string2Array(String str, Map<String, Integer> map) {
        final String WITH_DELIMITER = "(?<=%1$s)|(?=%1$s)";
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        Iterator<String> it = map.keySet().iterator();
        if (it.hasNext()) {
            String firstKey = it.next();
            String regex = String.format(WITH_DELIMITER, firstKey);
            sb.append(regex);
        }
        while (it.hasNext()) {
            String regex = "|" + String.format(WITH_DELIMITER, it.next());
            sb.append(regex);
        }
        sb.append(")");

        final String REGEX = sb.toString();
        String[] strings = str.split(REGEX);

        TestPinyinHelper.print(strings);

    }

    private String readFromFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("#") || line.equals("")) {
                    continue;
                }
                sb.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

}
