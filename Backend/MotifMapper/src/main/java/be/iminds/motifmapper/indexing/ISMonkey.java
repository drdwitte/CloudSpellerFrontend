package be.iminds.motifmapper.indexing;


import be.iminds.motifmapper.motifmodels.Motif;

import java.util.ArrayList;
import java.util.List;

public interface ISMonkey {

	public Motif getMotifTrail();
	public boolean hasMatches();
	public List<Suffix> grabSuffixes();
	public ArrayList<NodeDecoration> grabInternalNodeInfo();
	public ISMonkey createClone();
	public void jumpTo(Character c);
	public void backtrack();
	//public ISMonkey createDegenerateMonkey(Character degenerateExtension,
	//		List<ISMonkey> exactMonkeys);
	public NodeDecoration getJointNodeInfo();
}
