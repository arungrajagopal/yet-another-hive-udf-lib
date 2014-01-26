-- Functions to compute edit distance between two strings; 
create temporary function dldistance as 'com.infinitescaling.DamerauLevenshtein';
create temporary function ldistance as 'com.infinitescaling.Levenshtein';

