package com.c2s.dsif.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ControllerReadFile {

    public BufferedReader loadFile() throws IOException {
        Resource resource = new ClassPathResource("enwiki_20180420_100d.txt");
        InputStream inputStream = resource.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return bufferedReader;
    }

    @GetMapping("/similarity")
    public Double calculateSimilarity() throws IOException {
        List<String> sentence1Words = getCleanedWords("was #The /Is THAT éALSO@");
        List<String> sentence2Words = getCleanedWords("in of a this a is");
        List<Double> sentence1Vector = calculateSentenceVector(sentence1Words);
        List<Double> sentence2Vector = calculateSentenceVector(sentence2Words);
        return calculateCosineSimilarity(sentence1Vector, sentence2Vector);
    }

    private List<String> getCleanedWords(String sentence) {
        String cleanedSentence = Pattern.compile("[^A-Za-z0-9\\s]").matcher(sentence.toLowerCase()).replaceAll("");
        return Arrays.stream(cleanedSentence.split(" "))
                .filter(word -> word != null && !word.isEmpty())
                .collect(Collectors.toList());
    }

    
    private List<Double> calculateSentenceVector(List<String> words) throws IOException {
        BufferedReader file = loadFile();
        List<List<Double>> wordVectors = file.lines()
        		.limit(20)
                .filter(line -> words.contains(line.split(" ")[0]))
                .map(line -> Arrays.stream(line.split(" "))
                        .skip(1)
                        .map(Double::parseDouble)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        return IntStream.range(0, wordVectors.get(0).size())
                .mapToDouble(i -> wordVectors.stream()
                        .mapToDouble(v -> v.get(i))
                        .average()
                        .orElse(0))
                .boxed()
                .collect(Collectors.toList());
    }

    private Double calculateCosineSimilarity(List<Double> vector1, List<Double> vector2) {
        double dotProduct = IntStream.range(0, vector1.size())
                .mapToDouble(i -> vector1.get(i) * vector2.get(i))
                .sum();

        double magnitude1 = Math.sqrt(vector1.stream()
                .mapToDouble(Double::doubleValue)
                .map(d -> d * d)
                .sum());

        double magnitude2 = Math.sqrt(vector2.stream()
                .mapToDouble(Double::doubleValue)
                .map(d -> d * d)
                .sum());

        if (magnitude1 == 0 || magnitude2 == 0) {
            return 0.0;
        } else {
            return dotProduct / (magnitude1 * magnitude2);
        }
    }
}









