package cxf.util;




import org.apache.cxf.common.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * Spel表达式提取工具
 * <p>
 * 抽取替换 @[] 中得值
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-12-04 11:36 上午
 */
public class SpelExtractUtil {

    private SpelExtractUtil() {
    }

    /**
     * @[] 不能嵌套出现
     *
     * @param str
     * @return
     */
    public static List<IndexPair> findIndexPair(String str) {
        if (StringUtils.isEmpty(str)) {
            return Collections.emptyList();
        }
        char[] chars = str.toCharArray();
        List<IndexPair> list = new ArrayList<>();
        LinkedList<Character> stack = new LinkedList<>();
        IndexPair index = null;
        boolean isBegin = false;
        for (int i = 0; i < chars.length; i++) {
            // 判断是否spel表达式开始
            if (i != chars.length - 1 && chars[i] == '@' && chars[i + 1] == '[') {
                isBegin = true;
                index = new IndexPair(i + 1);
            }
            // spel 表达式判断开始后 若为 [  则入栈
            if (isBegin && chars[i] == '[') {
                stack.push('[');
            }
            // 当栈不为空 且 为 ] 时 则出栈
            if (!stack.isEmpty() && chars[i] == ']') {
                stack.pop();
                // 当栈为空时 表达式寻找完成
                if (stack.isEmpty()) {
                    index.setEndIndex(i);
                    list.add(index);
                    isBegin = false;
                }
            }
        }
        return list;
    }

    /**
     * 找出字符串中全部 SPEL 表达式
     *
     * @param str
     * @return
     */
    public static List<String> findSpel(String str) {
        List<IndexPair> indexPairs = findIndexPair(str);
        List<String> spelList = new ArrayList<>();
        for (IndexPair indexPair : indexPairs) {
            spelList.add(str.substring(indexPair.getBeginIndex() + 1, indexPair.getEndIndex()));
        }
        return spelList;
    }



    static class IndexPair {

        public IndexPair(int beginIndex) {
            this.beginIndex = beginIndex;
        }

        private int beginIndex;

        private int endIndex;

        public int getBeginIndex() {
            return beginIndex;
        }

        public void setBeginIndex(int beginIndex) {
            this.beginIndex = beginIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public void setEndIndex(int endIndex) {
            this.endIndex = endIndex;
        }

        @Override
        public String toString() {
            return "IndexPair{" +
                    "beginIndex=" + beginIndex +
                    ", endIndex=" + endIndex +
                    '}';
        }
    }

}
