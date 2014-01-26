/**
* Adapted from the DamerauLevenshtein implementation at
* https://github.com/KevinStern/software-and-algorithms/blob/master/src/main/java/blogspot/software_and_algorithms/stern_library/string/DamerauLevenshteinAlgorithm.java
*/

package com.infinitescaling;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import java.util.HashMap;
import java.util.Map;


class DamerauLevenshtein extends UDF {

    /**
     * Compute the Damerau-Levenshtein distance between the two strings.
     */
    
    public IntWritable evaluate(Text col1, Text col2) {
        if (col1 == null || col2 == null) {
            return null;
        }
        int insertCost = 1;
        int deleteCost = 1;
        int swapCost = 1;
        int replaceCost = 1;

        String source = col1.toString();
        String target = col2.toString();

        if (source.length() == 0) {
            return new IntWritable(target.length() * insertCost);
        }
        if (target.length() == 0) {
            return new IntWritable(source.length() * deleteCost);
        }
        int[][] table = new int[source.length()][target.length()];
        Map<Character, Integer> sourceIndexByCharacter = new HashMap<Character, Integer>();
        if (source.charAt(0) != target.charAt(0)) {
            table[0][0] = Math.min(replaceCost, deleteCost + insertCost);
        }
        sourceIndexByCharacter.put(source.charAt(0), 0);
        for (int i = 1; i < source.length(); i++) {
            int deleteDistance = table[i - 1][0] + deleteCost;
            int insertDistance = (i + 1) * deleteCost + insertCost;
            int matchDistance = i * deleteCost
                + (source.charAt(i) == target.charAt(0) ? 0 : replaceCost);
            table[i][0] = Math.min(Math.min(deleteDistance, insertDistance),
                    matchDistance);
        }
        for (int j = 1; j < target.length(); j++) {
            int deleteDistance = table[0][j - 1] + insertCost;
            int insertDistance = (j + 1) * insertCost + deleteCost;
            int matchDistance = j * insertCost
                + (source.charAt(0) == target.charAt(j) ? 0 : replaceCost);
            table[0][j] = Math.min(Math.min(deleteDistance, insertDistance),
                    matchDistance);
        }
        for (int i = 1; i < source.length(); i++) {
            int maxSourceLetterMatchIndex = source.charAt(i) == target
                .charAt(0) ? 0 : -1;
            for (int j = 1; j < target.length(); j++) {
                Integer candidateSwapIndex = sourceIndexByCharacter.get(target
                        .charAt(j));
                int jSwap = maxSourceLetterMatchIndex;
                int deleteDistance = table[i - 1][j] + deleteCost;
                int insertDistance = table[i][j - 1] + insertCost;
                int matchDistance = table[i - 1][j - 1];
                if (source.charAt(i) != target.charAt(j)) {
                    matchDistance += replaceCost;
                } else {
                    maxSourceLetterMatchIndex = j;
                }
                int swapDistance;
                if (candidateSwapIndex != null && jSwap != -1) {
                    int iSwap = candidateSwapIndex;
                    int preSwapCost;
                    if (iSwap == 0 && jSwap == 0) {
                        preSwapCost = 0;
                    } else {
                        preSwapCost = table[Math.max(0, iSwap - 1)][Math.max(0, jSwap - 1)];
                    }
                    swapDistance = preSwapCost + (i - iSwap - 1) * deleteCost
                        + (j - jSwap - 1) * insertCost + swapCost;
                } else {
                    swapDistance = Integer.MAX_VALUE;
                }
                table[i][j] = Math.min(Math.min(Math.min(deleteDistance, insertDistance), 
                    matchDistance), swapDistance);
            }
            sourceIndexByCharacter.put(source.charAt(i), i);
        }
        return new IntWritable(table[source.length() - 1][target.length() - 1]);
    }
}

