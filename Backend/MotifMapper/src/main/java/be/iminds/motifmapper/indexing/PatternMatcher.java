package be.iminds.motifmapper.indexing;


import be.iminds.motifmapper.motifmodels.IUPACMotif;

import java.util.List;

public interface PatternMatcher {

	public List<Suffix> matchExactPattern(IUPACMotif pattern);
}
