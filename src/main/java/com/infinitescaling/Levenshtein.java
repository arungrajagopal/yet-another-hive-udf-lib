/*
* Levenshtein Distance implementation from
* http://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance
*/

package com.infinitescaling;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;

@UDFType(deterministic = true)
@Description(
  name="ldistance",
  value="ldistance('book', 'cook') -> 1.",
  extended="SELECT ldistance(col1, col2) from foo;")
class Levenshtein extends UDF {

    /**
     * Compute the Levenshtein distance between the two strings.
     */
    
    public static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    public IntWritable evaluate(Text col1, Text col2) {
        if (col1 == null || col2 == null) {
            return null;
        }
        String str1 = col1.toString();
        String str2 = col2.toString();

        int[][] distance = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++)
            distance[i][0] = i;
        for (int j = 1; j <= str2.length(); j++)
            distance[0][j] = j;

        for (int i = 1; i <= str1.length(); i++)
            for (int j = 1; j <= str2.length(); j++)
                distance[i][j] = minimum(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1]+ ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));

        return new IntWritable(distance[str1.length()][str2.length()]);    
    }
}
    
