package com.newcoder.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    //替换符
    private static final String REPLACEMENT = "***";
    //根节点
    private TrieNode rootNode = new TrieNode();

    @PostConstruct
    public void init() {
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while ((keyword = reader.readLine()) != null) {
                //敏感词加入
                this.addKeyWord(keyword);
            }
        }  catch (IOException e) {
            logger.error("加载敏感词文件失败" + e.getMessage());
        }

    }

    /**
     * @Description 将敏感词加入到前缀树中
     * @author GuoZihan
     * @date 10:41 2024/3/4
     */
    private void addKeyWord(String keyword) {
        TrieNode tempNode = rootNode;
        for (int i =0; i<keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.subNodes.get(c);
            if(subNode == null) {
                subNode = new TrieNode();
                tempNode.addSubNode(c,subNode);
            }
            tempNode = subNode;
            if(i == keyword.length() - 1) {
                tempNode.setKeyWordEnd(true);
            }
        }
    }

    /**
     * @Description 过滤敏感词
     * @author GuoZihan
     * @date 10:53 2024/3/4
     */
    public String filter(String text) {
        if(StringUtils.isBlank(text)) {
            return null;
        }
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;
        StringBuilder sb = new StringBuilder();
        while (position < text.length()) {
            char c = text.charAt(position);
            //跳过符号
            if (isSymbol(c)) {
                //若指针1处于根节点,将此符号计入结果,让指针2向下走一步
                if(tempNode == rootNode) {
                    sb.append(c);
                    begin++;
                }
                //指针3向下走一步
                position++;
                continue;
            }
            //检查下级节点
            tempNode = tempNode.subNodes.get(c);
            if(tempNode == null) {
                sb.append(text.charAt(begin));
                position = ++begin;
                tempNode = rootNode;
            } else if (tempNode.isKeyWordEnd()) {
                sb.append(REPLACEMENT);
                begin = ++position;
            } else {
                position++;
            }
        }
        //将最后一批结果计入结果
        sb.append(text.substring(begin));
        return sb.toString();
    }

    /**
     * @Description 判断是否为符号
     * @author GuoZihan
     * @date 10:58 2024/3/4
     */
    private boolean isSymbol(Character c) {
        //0x2E80~0x9FFF为东亚文字范围
        return !CharUtils.isAsciiAlphaLower(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    /**
     * @Description 前缀树
     * @author GuoZihan
     * @date 10:28 2024/3/4
     */
    private class TrieNode {
        //关键词结束标识
        private boolean isKeyWordEnd = false;

        //子节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public boolean isKeyWordEnd() {
            return isKeyWordEnd;
        }

        public void setKeyWordEnd(boolean keyWordEnd) {
            isKeyWordEnd = keyWordEnd;
        }

         /**
          * @Description 添加子节点
          * @author GuoZihan
          * @date 10:26 2024/3/4
          */
         public void addSubNode(Character c, TrieNode node) {
             subNodes.put(c, node);
         }
    }
}
