-- Functions to compute edit distance of two strings; 
create temporary function dldistance as 'com.tre.DamerauLevenshtein';
create temporary function ldistance as 'com.tre.Levenshtein';

