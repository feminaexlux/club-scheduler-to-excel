package net.feminaexlux.converter.clubschedulertoexcel.model;

public enum AgendaItemType {

	SPEECH_1(8),
	SPEECH_2(9),
	SPEECH_3(5);

	private int index;

	private AgendaItemType(final int index) {
		this.index = index;
	}

	public static AgendaItemType getTypeByIndex(final int index) {
		for (AgendaItemType value : values()) {
			if (index == value.index) {
				return value;
			}
		}

		return null;
	}

}
