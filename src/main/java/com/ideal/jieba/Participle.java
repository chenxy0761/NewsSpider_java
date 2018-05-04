package com.ideal.jieba;

import com.huaban.analysis.jieba.JiebaSegmenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Participle {
    public static void main(String[] args) throws IOException {
        String text = "这样的一个开满了白花的下午,总觉得似曾相识,总觉得是一场可以放进任何一种时空里的聚合.可以放进诗经,可以放进楚辞,可以放进古典主义也同时可以放进";
        JiebaSegmenter segmenter = new JiebaSegmenter();
        Map<String, Integer> map = new HashMap<String, Integer>();
        List<String> keys = segmenter.sentenceProcess(text);
        for (String key : keys) {
            if (map.containsKey(key)) {
                int num = map.get(key);
                map.put(key, ++num);
            } else {
                map.put(key, 1);
            }
        }
        System.out.println(map);
    }

    public static Map<String, Integer> analyeWord(String text) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        Map<String, Integer> map = new HashMap<String, Integer>();
        List<String> keys = segmenter.sentenceProcess(text);
        for (String key : keys) {
            if (map.containsKey(key)) {
                int num = map.get(key);
                map.put(key, ++num);
            } else {
                map.put(key, 1);
            }
        }
        return map;
    }
}
