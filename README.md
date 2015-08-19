# CloudSpellerFrontend

Frontend application which allows the postprocessing of CloudSpeller Database (Monocot motifs)

Starting from for example C70, F20, BLS15 there are two tracks:

1) people want to analyze this data 
	
	a) further filter this dataset for example to BLS95
	
	b) PWM matching
	
	c) permutaton group matching


2) people want to see the mapped back motifs in a 'genome browser'
	
	* data model 1: every sequence is an array with frequences for iupac chars per position

		a) bar-chart like visualization showing the pile-ups: stacked bars for different characters?)
		
		b) identify peaks with IUPAC like structures (IUPAC PWMs)
		
		c) clustering
		
		d) show OCR regions
		
		e) compare with peaks in random regions (must be possible to derive p-values -> peaks with h>=1000 num in random data
			is a p-value)
		
		f) cluster IUPAC PWMs

	* data model 2: patternBLS matching in masked sequences (mask parts of sequence which are not in a peak)

		a) table: motifBLS: F,C => F_peaks,C_peaks
		=> query a motif for presence in clusters: motif -> in x clusters with cutoff 10 (pileup)
	
		b) peaks in OCR regions?

	data model 3: per motif family/genes in which they occur (derive exact positions later?)
	
		a) map a set of motifs to clusters (for example: result of kmeans clustering)
	
		b) motif position dependence with motif sequence (visualisatieles de sterre)
			what motifs are in this peak is then a very small GST and match all motifs in that gene

	
