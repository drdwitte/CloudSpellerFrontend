package be.iminds.motifmapper.indexing;

public class SeqIDDecorationFactory implements NodeDecorationFactory {
	
	@Override
	public NodeDecoration createNodeDecoration(int numberOfSequences) {
		return new SequenceIDSet(numberOfSequences);
	}

}
