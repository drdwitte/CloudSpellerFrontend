package be.iminds.motifmapper.indexing;


import be.iminds.motifmapper.input.Sequence;

import java.util.ArrayList;
import java.util.List;

public interface IndexStructureFactory {

	NodeDecorationFactory getNodeDecorationFactory();
	IndexStructure createIndexStructure(ArrayList<Sequence> seqs);
	IndexStructure createIndexStructureForSuffixes(ArrayList<Sequence> seqs, List<Suffix> suffixes);
}
