package edu.stu.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SplitChineseWordsTest {

    public static class ChineseWordsTrie {
        public char c; //当前节点的字符
        public boolean isEnd; //判断当前节点是否为一个词语的结尾
        public List<ChineseWordsTrie> childList;

        private ChineseWordsTrie(char c) {
            this.c = c;
            this.isEnd = false;
            childList = new LinkedList<>();
        }

        /**
         * 查找当前子节点中是否包含字符c
         *
         * @param c 需要判断的字符
         * @return 指定字符对应的节点
         */
        public ChineseWordsTrie findChild(char c) {
            for (ChineseWordsTrie child : childList) {
                if (child.c == c) {
                    return child;
                }
            }

            return null;
        }

    }

    public SplitChineseWordsTest(String[] words) {
        buildChineseWordsTrie(words);
    }

    private static final ChineseWordsTrie root = new ChineseWordsTrie('0');

    public ChineseWordsTrie getRoot() {
        return root;
    }

    /**
     * 构建一颗中文分词树
     *
     * @param words 中文词组
     */
    public void buildChineseWordsTrie(String[] words) {
        for (String word : words) {
            insertWord(word);
        }
    }

    /**
     * 将指定的词语插入到中文分词树中
     *
     * @param word 中文词语
     */
    private void insertWord(String word) {
        char[] arr = word.toCharArray();
        ChineseWordsTrie currentNode = root;
        for (char c : arr) {
            ChineseWordsTrie child = currentNode.findChild(c);
            if (null == child) {
                child = new ChineseWordsTrie(c);
                currentNode.childList.add(child);
            }
            currentNode = child;
        }

        currentNode.isEnd = true;
    }

    /**
     * 将中文字符串str中包含的词语分割出来
     *
     * @param str 中文字符串
     * @return Map&lt;String, Integer&gt; key:分割出来的词语 value:该词语在str中出现的次数
     */
    public Map<String, Integer> tokenize(String str) {
        char[] arr = str.toCharArray();
        Map<String, Integer> map = new HashMap<>();

        //记录Trie从root开始匹配所有字符
        ChineseWordsTrie currentNode = root;
        StringBuilder sb = new StringBuilder();

        //最后一次匹配到的词语
        String word = "";

        //最后一次匹配时的坐标
        int idx = 0;

        //TODO need to check the validation...
        for (int i = 0, len = arr.length; i < len; i++) {
            ChineseWordsTrie child = currentNode.findChild(arr[i]);
            if (null != child) {
                sb.append(arr[i]);

                currentNode = child;
                if (currentNode.isEnd) {
                    word = sb.toString();
                    idx = i;
                }
            } else if (!word.equals("")) {
                Integer count = map.get(word) == null ? 1 : map.get(word) + 1;
                map.put(word, count);
                i = idx;
                currentNode = root;
                word = "";
                sb = new StringBuilder();
            }
        }

        if (!word.equals("")) {
            Integer count = map.get(word) == null ? 1 : map.get(word) + 1;
            map.put(word, count);
        }

        return map;
    }

}
