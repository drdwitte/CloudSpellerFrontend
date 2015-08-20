package be.iminds.motifmapper.indexing;


import be.iminds.motifmapper.motifmodels.MotifFactory;

public interface IndexStructure extends PatternMatcher{
	
	public ISMonkey getExactISMonkey(MotifFactory motifFactory, int maxDegeneracy);
}
