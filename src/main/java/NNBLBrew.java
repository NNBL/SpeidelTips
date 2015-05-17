import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class NNBLBrew {
	private SimpleStringProperty brewer;
	private SimpleStringProperty brewname;
	private SimpleIntegerProperty grainweight;
	private SimpleDoubleProperty efficiency;
	private SimpleIntegerProperty originalGravity;
	private SimpleIntegerProperty finalGravity;
	private SimpleDoubleProperty mashTemp;
	private SimpleDoubleProperty attenuation;
	private SimpleStringProperty yeasttype;
	private SimpleStringProperty remarks;


	public NNBLBrew(String[] values) {
		this.brewer = new SimpleStringProperty(values[0]);
		this.brewname = new SimpleStringProperty(values[1]);
		this.grainweight = new SimpleIntegerProperty(Integer.parseInt(values[2].trim()));
		this.efficiency = new SimpleDoubleProperty(Double.parseDouble(values[3].trim()));

		if (values[4].equals("NaN")) {
			this.originalGravity = new SimpleIntegerProperty(-1);
		} else {
			this.originalGravity = new SimpleIntegerProperty(Integer.parseInt(values[4].trim()));
		}

		if (values[4].equals("NaN")) {
			this.finalGravity = new SimpleIntegerProperty(-1);

		} else {
			this.finalGravity = new SimpleIntegerProperty(Integer.parseInt(values[5].trim()));
		}

		this.mashTemp = new SimpleDoubleProperty(Double.parseDouble(values[6].trim()));
		this.attenuation = new SimpleDoubleProperty(Double.parseDouble(values[7].trim()));
		this.yeasttype =new SimpleStringProperty( values[8].trim());
		this.remarks = new SimpleStringProperty(values[9].trim());
	}

	public String getBrewer() {
		return brewer.get();
	}

	public void setBrewer(String brewer) {
		this.brewer.set(brewer);
	}

	public String getBrewname() {
		return brewname.get();
	}

	public void setBrewname(String brewname) {
		this.brewname.set(brewname);
	}

	public int getGrainweight() {
		return grainweight.get();
	}

	public void setGrainweight(int grainweight) {
		this.grainweight.set(grainweight);
	}

	public double getEfficiency() {
		return efficiency.get();
	}

	public void setEfficiency(double efficiency) {
		this.efficiency.set(efficiency);
	}

	public int getOriginalGravity() {
		return originalGravity.get();
	}

	public void setOriginalGravity(int originalGravity) {
		this.originalGravity.set(originalGravity);
	}

	public int getFinalGravity() {
		return finalGravity.get();
	}

	public void setFinalGravity(int finalGravity) {
		this.finalGravity.set(finalGravity);
	}

	public double getMashTemp() {
		return mashTemp.get();
	}

	public void setMashTemp(double mashTemp) {
		this.mashTemp.set(mashTemp);
	}

	public double getAttenuation() {
		return attenuation.get();
	}

	public void setAttenuation(double attenuation) {
		this.attenuation.set(attenuation);
	}

	public String getYeasttype() {
		return yeasttype.get();
	}

	public void setYeasttype(String yeasttype) {
		this.yeasttype.set(yeasttype);
	}

	public String getRemarks() {
		return remarks.get();
	}

	public void setRemarks(String remarks) {
		this.remarks.set(remarks);
	}


	@Override
	public String toString() {

		String s = brewname.get() + " by " + brewer.get() + System.lineSeparator();
		s += "Grain weight: " + grainweight.get() + "g" + System.lineSeparator();
		s += "Efficiency: " + efficiency.get() + "%" + System.lineSeparator();
		s += "OG: " + originalGravity.get() + System.lineSeparator();
		s += "FG: " + finalGravity.get() + System.lineSeparator();
		s += "Mash temp: " + mashTemp.get() + "C" + System.lineSeparator();
		s += "Attanuation: " + attenuation.get() + System.lineSeparator();
		s += "Yeast type: " + yeasttype.get() + System.lineSeparator();
		s += "Remarks: " + remarks.get() + System.lineSeparator();

		return s;
	}
}
