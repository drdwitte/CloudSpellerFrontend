package be.iminds.motifmapper.phylogenetics;


import be.iminds.motifmapper.input.GeneFamily;

public class BLSCalculatorFactory implements ConservationScoreCalculatorFactory {

	private BLS cutoffScore;
	
	public BLSCalculatorFactory(BLS cutoffScore) {
		this.cutoffScore=cutoffScore;
	}
	
	@Override
	public ConservationScoreCalculator createCalculator(GeneFamily gf){
		BLSCalculator calc=new BLSCalculator(gf);
		calc.setCutoff(cutoffScore);
		return calc;
	}
}
