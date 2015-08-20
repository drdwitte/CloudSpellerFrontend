package be.iminds.motifmapper.phylogenetics;


import be.iminds.motifmapper.indexing.NodeDecoration;

public interface ConservationScoreCalculator {

	/**
	 * 
	 * @param nodeInfo
	 * @return (conservationScore < cutoffScore)?null:conservationScore;
	 */
	public ConservationScore calculateScore(NodeDecoration nodeInfo);
	public void setCutoff(ConservationScore cutoffScore);
	

}
