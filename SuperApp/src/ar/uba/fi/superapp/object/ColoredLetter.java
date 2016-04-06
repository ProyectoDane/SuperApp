package ar.uba.fi.superapp.object;

import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

import ar.uba.fi.superapp.managers.ResourcesManager;

public class ColoredLetter extends Text {

	public ColoredLetter(Color pColor, char letter) {
		super(0, 0, ResourcesManager.get().font, "" + letter, ResourcesManager.get().vbom);
		setColor(pColor);
	}

	@Override
	public String toString() {
		return getText().toString();
	}

	public ColoredLetter getCopy() {
		return new ColoredLetter(getColor(), getText().charAt(0));
	}

	@Override
	public boolean equals(Object o) {
		if (!o.getClass().equals(this.getClass())) {
			return false;
		}
		ColoredLetter cl = (ColoredLetter) o;
		if (!getText().equals(cl.getText())) {
			return false;
		}
		if (!getColor().equals(cl.getColor())) {
			return false;
		}
		return true;

	}

}
