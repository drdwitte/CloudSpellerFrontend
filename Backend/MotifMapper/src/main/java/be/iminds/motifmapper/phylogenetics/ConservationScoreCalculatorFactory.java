package be.iminds.motifmapper.phylogenetics;


import be.iminds.motifmapper.input.GeneFamily;

public interface ConservationScoreCalculatorFactory {

	ConservationScoreCalculator createCalculator(GeneFamily gf);
}
