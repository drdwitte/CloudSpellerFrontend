package be.iminds.motifmapper.indexing;


public class FullMotifMatchWithPos extends FullMotifMatch {

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + motifStart;
		result = prime * result + motifStop;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof FullMotifMatchWithPos)) {
			return false;
		}
		FullMotifMatchWithPos other = (FullMotifMatchWithPos) obj;
		if (motifStart != other.motifStart) {
			return false;
		}
		if (motifStop != other.motifStop) {
			return false;
		}
		return true;
	}



	/**
	 * motif start first ID of motif, motifstop is lastID of motif
	 */
	
	private int motifStart;
	private int motifStop;
	

	/**
	 * Default constructor
	 */
	public FullMotifMatchWithPos(){}
	
	public FullMotifMatchWithPos(FullMotifMatch match){
		super(match);
	}
	
	public int getMotifStartPosition() {
		return motifStart;
	}
	
	public int getMotifStopPosition() {
		return motifStop;
	}


	public void setMotifPosition(int start, int stop)  {
		this.motifStart = start;
		this.motifStop = stop;
	}

	@Override
	public String toString(){
		return super.toString()+tab+motifStart+tab+motifStop;
	}




	

	
	
	
	
}
