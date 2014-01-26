package com.infinitescaling;

import junit.framework.Assert;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.junit.Test;

public class StringDistanceTest {
  
  @Test
  public void testLUDF() {
    Levenshtein example = new Levenshtein();
    Assert.assertEquals(2, example.evaluate(new Text("book"), new Text("back")).get());
    Assert.assertEquals(3, example.evaluate(new Text("CA"), new Text("ABC")).get());
  }
  
  @Test
  public void testLUDFNullCheck() {
    Levenshtein example = new Levenshtein();
    Assert.assertNull(example.evaluate(null, null));
    Assert.assertNull(example.evaluate(null, new Text("word")));
    Assert.assertNull(example.evaluate(new Text("apple"), null));
  }

  @Test
  public void testDLUDF() {
    DamerauLevenshtein example = new DamerauLevenshtein();
    Assert.assertEquals(2, example.evaluate(new Text("book"), new Text("back")).get());
    Assert.assertEquals(2, example.evaluate(new Text("CA"), new Text("ABC")).get());
  }
  
  @Test
  public void testDLUDFNullCheck() {
    DamerauLevenshtein example = new DamerauLevenshtein();
    Assert.assertNull(example.evaluate(null, null));
    Assert.assertNull(example.evaluate(null, new Text("word")));
    Assert.assertNull(example.evaluate(new Text("apple"), null));
  }
}



