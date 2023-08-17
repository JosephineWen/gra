package com.it.shop.englishShoping.utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.shop.englishShoping.domain.Keywords;
import com.it.shop.englishShoping.mapper.KeyWordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
public class WordFilter {
    private Map<Character, Map> wordDict;

    public WordFilter(List<Keywords> keywords) {
        wordDict = new HashMap<>();
        loadKeywordsFromDatabase(keywords);
    }

    private void loadKeywordsFromDatabase(List<Keywords> keywords) {
        for (Keywords str : keywords) {
            addWord(str.getText().toLowerCase()); // Convert sensitive words to lowercase
        }
    }

    public void addWord(String word) {
        Map<Character, Map> node = wordDict;
        for (char c : word.toCharArray()) {
            if (!node.containsKey(Character.toLowerCase(c))) { // Convert characters to lowercase
                node.put(Character.toLowerCase(c), new HashMap<>());
            }
            node = node.get(Character.toLowerCase(c));
        }
        node.put(')', new HashMap<>()); // Adding an end marker
    }

    public String filterWords(String text) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            Map<Character, Map> node = wordDict;
            int j = i;
            int end = -1; // Record the end position of the last successfully matched keyword
            while (j < text.length() && node.containsKey(Character.toLowerCase(text.charAt(j)))) { // Convert characters to lowercase
                node = node.get(Character.toLowerCase(text.charAt(j)));
                if (node.containsKey(')')) {
                    end = j; // Record the end position of successfully matched keywords
                }
                j++;
            }
            if (end != -1) {
                for (int k = i; k <= end; k++) {
                    result.append("*");
                }
                i = end + 1;
            } else {
                result.append(text.charAt(i));
                i++;
            }
        }
        return result.toString();
    }
}